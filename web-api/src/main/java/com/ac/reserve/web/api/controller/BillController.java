package com.ac.reserve.web.api.controller;

import com.ac.reserve.common.constant.CommonConstant;
import com.ac.reserve.common.constant.DataSourceConstant;
import com.ac.reserve.common.exception.ServiceException;
import com.ac.reserve.common.response.BaseResponse;
import com.ac.reserve.common.util.JSONUtil;
import com.ac.reserve.common.util.SmsUtil;
import com.ac.reserve.common.utils.ResponseBuilder;
import com.ac.reserve.web.api.dto.BillPdfInfoDTO;
import com.ac.reserve.web.api.dto.BillRequestDTO;
import com.ac.reserve.web.api.mapper.BillMapper;
import com.ac.reserve.web.api.po.Bill;
import com.ac.reserve.web.api.service.BillService;
import com.ac.reserve.web.api.service.ExamineApiService;
import com.ac.reserve.web.api.service.PdfService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bill")
@CrossOrigin("*")
public class BillController {

    // 申请成功code
    private static final String APPLY_EXAMINE_SUCCESS = "1";
    // 短信签名（【珠海航展】）
    private static final String SIGN_NAME = "智慧航展平台";

    @Autowired
    private BillService billService;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private ExamineApiService examineApiService;
    @Autowired
    private PdfService pdfService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsUtil smsUtil;

    @ApiOperation(value = "获取票据信息")
    @GetMapping("/{openid}")
    public BaseResponse getBillInfo(@PathVariable(value = "openid",required = true)String openid) {
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("openid",openid);
        billQueryWrapper.eq("valid", DataSourceConstant.DATA_SOURCE_VALID);
        List<Bill> list = billService.list(billQueryWrapper);
        return ResponseBuilder.buildSuccess(list);
    }

    @ApiOperation(value = "立即预约")
    @PostMapping("/reserve")
    public BaseResponse reserve(@RequestBody @Valid List<BillRequestDTO> billRequestDTOS){
        Bill bill = null;
        String bsId = null;
        List<Bill> list = new ArrayList<>();
        for (BillRequestDTO requestDTO : billRequestDTOS) {
            bill = new Bill();
            BeanUtils.copyProperties(requestDTO, bill);
            JSONObject jsonObject = examineApiService.applyExamine(requestDTO);
            if (jsonObject == null) {
                bill.setState(DataSourceConstant.APPROVAL_FAILED);
                // 发送预约失败短信
                SmsUtil.sendSms(CommonConstant.TEMPLATE_APPOINTMENT_TD, SIGN_NAME, bill.getPossessorPhone(), null);
            }
            // 申请成功
            if (APPLY_EXAMINE_SUCCESS.equals(jsonObject.getString("code"))) {
                bill.setState(DataSourceConstant.APPROVAL_IN_HAND);
                JSONObject data = jsonObject.getJSONObject("data");
                bsId = data.getString("bsId");
                bill.setExamineId(bsId);
                // 发送提交预约短信
                JSONObject smsJson = new JSONObject();
                smsJson.put("date", "2019.12.20 20:00");
                smsJson.put("address", "粤海东路至联安路");
                smsJson.put("order", bsId);
                SmsUtil.sendSms(CommonConstant.TEMPLATE_APPOINTMENT_SUBMIT, SIGN_NAME, bill.getPossessorPhone(), smsJson.toJSONString());
            }
            // 申请失败
            else {
                bill.setState(DataSourceConstant.APPROVAL_FAILED);
                // 发送预约失败短信
                SmsUtil.sendSms(CommonConstant.TEMPLATE_APPOINTMENT_TD, SIGN_NAME, bill.getPossessorPhone(), null);
            }
            list.add(bill);
        }
        billService.saveBatch(list);
        return ResponseBuilder.buildSuccess("预约成功.");
    }


    @GetMapping("/downLoad")
    public ResponseEntity downLoad(@RequestParam(value = "code") String code) throws UnsupportedEncodingException {
        BillPdfInfoDTO billPdfInfoDTO = billMapper.selectBillPdfInfo(code);
        if (billPdfInfoDTO == null) {
            throw new ServiceException("票据不存在.");
        } else if (billPdfInfoDTO.getState() != DataSourceConstant.DATA_SOURCE_VALID){
            throw new ServiceException("该票据还没通过审核.");
        }

        Map data = new HashMap();
        data.put("possessor_name", billPdfInfoDTO.getPossessorName());
        data.put("possessor_phone", billPdfInfoDTO.getPossessorPhone());
        data.put("type", billPdfInfoDTO.getType() == 0 ? "成人电子票" : "儿童电子票");
        data.put("round", billPdfInfoDTO.getRoundIfo());

        byte[] body = pdfService.getPdfByte(billPdfInfoDTO.getCode(), billPdfInfoDTO.getPossessorNumber(), data);
        String fileName = "downLoadPdf.pdf";
        fileName = new String(fileName.getBytes("gbk"), "iso8859-1");// 防止中文乱码
        HttpHeaders headers = new HttpHeaders();// 设置响应头
        headers.add("Content-Disposition", "attachment;filename=" + fileName);
        HttpStatus statusCode = HttpStatus.OK;// 设置响应吗
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
        return response;
    }
}
