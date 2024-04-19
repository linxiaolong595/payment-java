package com.jhzf.controller;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jhzf.service.QrCodeService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.user.QrCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;


@Controller
@RequestMapping("/qrCode")
public class QrCodeController{
    @Autowired
    private QrCodeService qrCodeService;

    //生成验证码
    @ResponseBody
    @PostMapping("/createCode")
    public ResponseDTO createCode(@RequestBody QrCodeVo vo){
        System.out.println(vo);
        qrCodeService.createStoreCode(vo);
        return null;
    }


    //识别二维码
    @GetMapping("/pay-entry/{ticket}")
    public String payEntry(@PathVariable("ticket")String ticket, Model model, ServerWebExchange exchange) {
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
        // 将解析后的参数添加到模型中
        model.addAttribute("storeMsg", jsonObject);
        try {
            // 获取请求头
            HttpHeaders headers = exchange.getRequest().getHeaders();
            // 获取特定头部信息
            List<String> contentTypeList = headers.get("user-agent");
            if (contentTypeList != null && !contentTypeList.isEmpty()) {
                String userAgent = contentTypeList.get(0);
                if (userAgent != null && userAgent.contains("AlipayClient")) {
                    System.out.println("使用的是支付宝扫描");
                    return "pay";
                }
                if (userAgent != null && userAgent.contains("MicroMessenger")) {
                    System.out.println("使用的是微信扫描");
                    return "pay";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "payError";
    }
}