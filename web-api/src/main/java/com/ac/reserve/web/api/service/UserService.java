package com.ac.reserve.web.api.service;

import com.ac.reserve.web.api.po.User;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;


public interface UserService extends IService<User> {


    /**
     * 功能描述: <br>
     * 〈获取二维码图片内容〉
     *
     * @Author:fan shi ke
     * @Date: 2019/7/27 10:46
     */
    JSONObject getCodePic(String setIDCardNo);

}
