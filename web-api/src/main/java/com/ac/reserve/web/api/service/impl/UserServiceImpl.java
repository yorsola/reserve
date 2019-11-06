package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.common.constant.CommonConstant;
import com.ac.reserve.common.util.GetRandomString;
import com.ac.reserve.common.util.TimeOperating;
import com.ac.reserve.web.api.mapper.QRCodeMapper;
import com.ac.reserve.web.api.po.QRCode;
import com.ac.reserve.web.api.po.User;
import com.ac.reserve.web.api.mapper.UserMapper;
import com.ac.reserve.web.api.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.CommandMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private QRCodeMapper qrCodeMapper;

    /**
     * 功能描述: <br>
     * 〈获取二维码内容〉
     *
     * @Author:fan shi ke
     * @Date: 2019/7/26 15:53
     */
    @Transactional
    public JSONObject getCodePic(String setIDCardNo){
        JSONObject jsonObject = new JSONObject();

        //1.生成二维码内容
        String key = CommonConstant.TEMP_TYPE.replace(CommonConstant.idCardNo, setIDCardNo);
        String content = GetRandomString.getRandomString(8) + System.currentTimeMillis();
        //2.保存二维码内容
        QRCode qrCode = addTempInfo(setIDCardNo,key,key+content);
        qrCodeMapper.insert(qrCode);
        jsonObject.put(CommonConstant.value, key+content);
        return jsonObject;
    }

    /**
     * 功能描述: <br>
     * 〈创建一个二维码信息〉
     *
     * @Author:fan shi ke
     * @Date: 2019/7/26 16:38
     */
    public QRCode addTempInfo(String IDCardNo, String key, String value) {
        QRCode qrCode = qrCodeMapper.findByTempKeyAndValid(key, CommonConstant.oneInt);
        if (Objects.isNull(qrCode)) {
            qrCode = new QRCode();
            qrCode.setIdCardNo(IDCardNo);
            qrCode.setTempKey(key);
            qrCode.setScanTime(TimeOperating.getNowTime());
            // TODO: 2019/11/6 不知道创建时间是什么类型  需要补充
//            qrCode.setCreated(TimeOperating.getNowTime());
        }
        qrCode.setTempValue(value);
        qrCode.setValid(CommonConstant.oneInt);
        // TODO: 2019/11/6 不知道创建时间是什么类型  需要补充
//        qrCode.updated(TimeOperating.getNowTimestamp());
        return qrCode;
    }

}
