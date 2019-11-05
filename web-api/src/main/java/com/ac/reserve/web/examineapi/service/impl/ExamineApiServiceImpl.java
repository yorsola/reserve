package com.ac.reserve.web.examineapi.service.impl;

import com.ac.reserve.web.api.dto.ReserveBillRequestDTO;
import com.ac.reserve.web.examineapi.dto.ApplyExaminePostRequestDTO;
import com.ac.reserve.web.examineapi.service.ExamineApiService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExamineApiServiceImpl implements ExamineApiService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${examine.url.applyExamineUrl}")
    private String applyExamineUrl;
    @Value("${examine.url.checkExamineUrl}")
    private String checkExamineUrl;
    @Value("${examine.account.user}")
    private String user;
    @Value("${examine.account.password}")
    private String password;

    @Override
    public JSONObject applyExamine(ReserveBillRequestDTO reserveBillRequestDTO){

        ApplyExaminePostRequestDTO applyExaminePostRequestDTO = new ApplyExaminePostRequestDTO();
        applyExaminePostRequestDTO.setZname(reserveBillRequestDTO.getPossessorName());
        applyExaminePostRequestDTO.setZnumber(reserveBillRequestDTO.getPossessorNumber());
        applyExaminePostRequestDTO.setDydw(reserveBillRequestDTO.getCompanyName());
        applyExaminePostRequestDTO.setZtype(reserveBillRequestDTO.getDocumentType());
        applyExaminePostRequestDTO.setZtypename(getIdCardTypeName(reserveBillRequestDTO.getDocumentType()));

        Map map = new HashMap();
        map.put("data", applyExaminePostRequestDTO);
        map.put("user", user);
        map.put("password", password);
        String response = restTemplate.postForObject(applyExamineUrl, map, String.class);
        JSONObject jsonObject = JSON.parseObject(response);
        return jsonObject;

    }

    @Override
    public JSONObject checkExamineResult(String examineId){
        Map map = new HashMap();
        Map o = new HashMap<>();
        o.put("bsId", examineId);
        map.put("user", user);
        map.put("password", password);
        map.put("data", o);
        String forObject = restTemplate.postForObject(checkExamineUrl, map, String.class);
        JSONObject jsonObject = JSONObject.parseObject(forObject);
        return jsonObject;
    }


    /**
     * 根据证件类型编码获取证件类型名称
     * @param idCardTypeCode
     * @return
     */
    private String  getIdCardTypeName(String idCardTypeCode){
        //111=二代居民身份证，411=护照，990=其他，991=港澳居民往来内地通行证，992=香港永久性居民身份证，993=台胞证',
        switch (idCardTypeCode) {
            case "111" :
                return "二代居民身份证";
            case "411" :
                return "护照";
            case "990" :
                return "其他";
            case "991" :
                return "港澳居民往来内地通行证";
            case "992" :
                return "香港永久性居民身份证";
            case "993" :
                return "台胞证";
            default:
                return null;
        }
    }

}