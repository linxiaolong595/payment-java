package com.jhzf.controller;

import com.jhzf.service.MessageCodeService;
import com.jhzf.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/messageCode")
public class MessageCodeController {
    @Autowired
    private MessageCodeService messageCodeService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @PostMapping("send/{phone}/interAspect")
    public void sendMsm(@PathVariable String phone){
        //生成随机数
        String code = RandomUtil.getFourBitRandom();
        Map map = new HashMap();
        map.put("code",code);
        boolean b = messageCodeService.send(map,phone);
        if(b){
            redisTemplate.opsForValue().set(phone,code,3, TimeUnit.MINUTES);
            System.out.println("验证码为：" + code);
        }else {
            System.out.println("验证码发送失败");
        }
    }
}
