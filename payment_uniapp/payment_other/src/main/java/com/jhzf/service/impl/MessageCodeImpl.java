package com.jhzf.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.google.gson.Gson;
import com.jhzf.service.MessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageCodeImpl implements MessageCode {

    @Override
    public boolean send(Map map, String phone) {
        if(StringUtils.isEmpty(phone)) return false;
        
        Config config = new Config().setAccessKeyId("LTAI5tSKFx2pidsDNNUrcpcj").setAccessKeySecret("Y3hWbDjDDsfHE8cnGRvfjw19jX9svu");
        // 访问的域名（这个不用变都是这个）
        config.endpoint = "dysmsapi.aliyuncs.com";
        Client client = null;
        try {
            client = new Client(config);
            SendSmsRequest request = new SendSmsRequest();

            request.setSignName("聚合支付");//签名名称
            request.setTemplateCode("SMS_465334737");//模版Code
            request.setPhoneNumbers(phone);//电话号码
            //这里的参数是json格式的字符串
            request.setTemplateParam(JSONObject.toJSONString(map));
            SendSmsResponse response = client.sendSms(request);
            System.out.println("发送成功："+new Gson().toJson(response));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
