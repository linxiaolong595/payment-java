package com.jhzf.controller;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jhzf.config.MtWeChatConstant;
import com.jhzf.service.QrCodeService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.user.QrCodeVo;
import com.sun.deploy.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
@RequestMapping("/qrCode")
public class QrCodeController{
    @Autowired
    private QrCodeService qrCodeService;

    //生成二维码
    @ResponseBody
    @PostMapping("/createCode")
    public ResponseDTO createCode(@RequestBody QrCodeVo vo){
        System.out.println(vo);
        qrCodeService.createStoreCode(vo);
        return null;
    }

    //识别二维码
    @GetMapping("/pay-entry/{ticket}")
    public String payEntry(@PathVariable("ticket")String ticket, Model model,@RequestHeader(value = "user-agent", required = false) String userAgent) throws UnsupportedEncodingException {
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
        if (userAgent != null && userAgent.contains("AlipayClient")) {
            System.out.println("使用的是支付宝扫描");
            return "pay";
        }
        if (userAgent != null && userAgent.contains("MicroMessenger")) {
            System.out.println("使用的是微信扫描");
            String encodedRedirectUri = URLEncoder.encode(MtWeChatConstant.PAY_OAUTH_CODE_URL, StandardCharsets.UTF_8.toString());
            String url = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s" +
                    "&response_type=code&scope=snsapi_base&state=%s#wechat_redirect", MtWeChatConstant.APP_ID,
                    encodedRedirectUri,ticket);
            return "redirect:" + url;
        }
        return "payError";
    }
}