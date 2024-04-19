package com.jhzf.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhzf.service.QrCodeService;
import com.jhzf.util.QRCodeUtil;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.user.QrCodeVo;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@RestController
@RequestMapping("/qrCode")
public class QrCodeController{
    @Autowired
    private QrCodeService qrCodeService;

    //生成验证码
    @PostMapping("/createCode")
    public ResponseDTO createCode(@RequestBody QrCodeVo vo){
        System.out.println(vo);
        qrCodeService.createStoreCode(vo);
        return null;
    }
    //识别二维码
    @GetMapping("/pay-entry/{ticket}")
    public ResponseDTO payEntry(@PathVariable("ticket")String ticket){
        System.out.println(ticket);
        // 解码 Base64 编码的数据
        byte[] decodedBytes = Base64.getUrlDecoder().decode(ticket);
        // 将字节数组转换为字符串（使用 UTF-8 字符集）
        String jsonString = new String(decodedBytes, StandardCharsets.UTF_8);
        // 将 JSON 字符串转换为 JSON 对象
        JSONObject jsonObject = JSON.parseObject(jsonString);
        // 打印 JSON 对象
        System.out.println("解码后的 JSON 对象:");
        System.out.println(jsonObject);
        return ResponseDTO.success(200,"这是个页面",jsonObject);
    }
}