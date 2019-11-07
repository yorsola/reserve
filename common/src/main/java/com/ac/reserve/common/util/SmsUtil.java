package com.ac.reserve.common.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 阿里大于
 * Create by ZYY on 2019/10/22 11:50
 */
@Slf4j
@Component
public class SmsUtil {

    //产品名称:云通信短信API产品,开发者无需替换
    public static final String product = "SendSms";
    //产品域名,开发者无需替换
    public  static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    public static final String accessKeyId = "LTAI4FvGv4QK48FJuZFDANqW";
    public static final String accessKeySecret = "kLQy1j65PHwxpZZFHDPPBMUt02pvEl";

    /**
     * 发送短信
     * @param TemplateCode 短信模板（SMS_147195339）
     * @param SignName  短信签名（【珠海航展】）
     * @param phone 手机号码
     * @param code  验证码（可选）
     */
    public static void sendSms(String TemplateCode, String SignName, String phone, String code){
        DefaultProfile profile = DefaultProfile.getProfile("cn-shenzhen", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        CommonRequest request = new CommonRequest();

        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion("2017-05-25");
        request.setAction(product);
        //必填:待发送手机号
        request.putQueryParameter("PhoneNumbers", phone);
        //必填:短信签名-可在短信控制台中找到
        request.putQueryParameter("SignName", SignName);
        //必填:短信模板-可在短信控制台中找到
        request.putQueryParameter("TemplateCode", TemplateCode);
//        request.putQueryParameter("TemplateCode", "SMS_147195339");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为

        if(!StringUtils.isEmpty(code)){
            request.putQueryParameter("TemplateParam",code);
        }
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject jsonObject = JSONObject.parseObject(response.getData());
            if("OK".equals(jsonObject.get("Code"))){
                log.warn("短信发送成功：" + code);
            }else {
                log.error("短信发送失败：" + jsonObject);
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("company", "zyy公司");
        jsonObject.put("password", "123");
        System.out.println(jsonObject.toString());
        sendSms("SMS_176522071", "智慧航展平台", "18520458878", jsonObject.toString());
    }
}
