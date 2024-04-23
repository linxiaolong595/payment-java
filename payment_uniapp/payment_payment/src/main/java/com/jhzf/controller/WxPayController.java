package com.jhzf.controller;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@CrossOrigin
@RequestMapping("/test")
public class WxPayController {
        String appId = "wx96600d2c9189b7bb";
        String mchId = "";
        String appSecret = "";
        String key = "";
        //申请微信授权码的地址
        String wxOAuth2RequestUrl = "";
        //授权回调的地址
        String wxOAuth2CodeReturnUrl = "http://yujenz.natappfree.cc/wx-oauth-code-return";
        String state = "";

    //获取授权码
    @GetMapping("/getWXOAuth2Code")
    public String getWXOAuth2Code(ServerWebExchange exchange){
        // 对 redirect_uri 进行编码
        String url = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect", appId, wxOAuth2CodeReturnUrl);
        return "redirect:" + url;
    }
    //授权回调,获取openid
    @GetMapping("/wx-oauth-code-return")
    public String wxOAuth2CodeReturn(String code,String state){
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code" +
                "=%s&grant_type=authorization_code",appId,appSecret,code);
        //申请openid
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        //申请openid接口响应的内容，其中包括了openid
        String response = exchange.getBody();
        System.out.println(response);
        String openid= JSONObject.parseObject(response).getString("openid");
        //携带openid跳转至统一下单地址
        return "http://qadaky.natappfree.cc/wxjspay?openid=" + openid;
    }

    //微信下单接口
    public void CreateOrder() throws Exception{
        // 创建一个HttpPost请求对象，指定请求的URL
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi");
        // 添加请求头，指定接受的数据类型为JSON
        httpPost.addHeader("Accept", "application/json");
        // 添加请求头，指定发送的数据类型为JSON，并设置字符集为UTF-8
        httpPost.addHeader("Content-type","application/json; charset=utf-8");

        // 创建一个字节数组输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // 创建一个ObjectMapper对象，用于将Java对象转换为JSON格式
        ObjectMapper objectMapper = new ObjectMapper();

        // 创建一个ObjectNode对象，用于构建请求体的JSON数据
        ObjectNode rootNode = objectMapper.createObjectNode();
        // 向JSON数据中添加商户号、appId、商品描述、通知URL和商户订单号等信息
        rootNode.put("mchid","1900009191")
                .put("appid", "wxd678efh567hg6787")
                .put("description", "Image形象店-深圳腾大-QQ公仔")
                .put("notify_url", "https://www.weixin.qq.com/wxpay/pay.php")
                .put("out_trade_no", "1217752501201407033233368018");
        // 在JSON数据中添加支付金额信息
        rootNode.putObject("amount")
                .put("total", 1);
        // 在JSON数据中添加支付者的openid信息
        rootNode.putObject("payer")
                .put("openid", "oUpF8uMuAJO_M2pxb1Q9zNjWeS6o");

        // 将JSON数据写入字节数组输出流中
        objectMapper.writeValue(bos, rootNode);

        // 将JSON数据设置为请求体的实体，指定字符集为UTF-8
        httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
        // 执行HttpPost请求，获取响应
        CloseableHttpResponse response = HttpClientUtil.executeHttpPost(httpPost);
        // 从响应中获取响应体的内容并转换为字符串
        String bodyAsString = EntityUtils.toString(response.getEntity());
        // 打印响应体的内容
        System.out.println(bodyAsString);
    }
}
