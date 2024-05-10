package com.jhzf.controller;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.jhzf.config.MtWeChatConstant;
import com.jhzf.service.WechatService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.payment.CreateOrderVo;
import com.jhzf.vo.payment.WXPayOrderVo;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.cipher.RSASigner;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;
import com.wechat.pay.java.service.partnerpayments.model.TransactionPayer;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@CrossOrigin
@RequestMapping("/wechat")
@Slf4j
public class WxPayController {
    @Autowired
    WechatService wechatService;

    //授权回调,获取openid
    @GetMapping("/wxOauthCodeReturn")
    public String wxOAuth2CodeReturn(String code, String state, Model model){
        // 解码 Base64 编码的数据
        byte[] decodedBytes = Base64.getUrlDecoder().decode(state);
        // 将字节数组转换为字符串（使用 UTF-8 字符集）
        String jsonString = new String(decodedBytes, StandardCharsets.UTF_8);
        // 将 JSON 字符串转换为 JSON 对象
        com.alibaba.fastjson2.JSONObject jsonObject = JSON.parseObject(jsonString);
        int storeId = jsonObject.getIntValue("storeId");
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code" +
                "=%s&grant_type=authorization_code",MtWeChatConstant.APP_ID,MtWeChatConstant.APP_SECRET,code);
        //申请openid
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        //申请openid接口响应的内容，其中包括了openid
        String response = exchange.getBody();
//        System.out.println(response);
        String openid= JSONObject.parseObject(response).getString("openid");
//        System.out.println(openid);
        jsonObject.put("openid",openid);
        jsonObject.put("storeId",storeId);
        // 将解析后的参数添加到模型中
        model.addAttribute("storeMsg", jsonObject);
        //携带openid跳转至统一下单地址
        return "pay";
    }


    //微信下单接口
    @PostMapping("/createOrder")
    @ResponseBody
    public ResponseDTO createOrder(@RequestBody CreateOrderVo vo) throws Exception {
        // 使用自动更新平台证书的RSA配置
        // 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错
        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(MtWeChatConstant.MERCHANT_ID)
                .privateKeyFromPath(MtWeChatConstant.MERCHANT_PRIVATE_KEY_PATH)
                .merchantSerialNumber(MtWeChatConstant.MERCHANT_SERIAL_NUMBER)
                .apiV3Key(MtWeChatConstant.API_V3_KEY)
                .build();
        //获取预支付订单号
        // 构建service
        JsapiService service = new JsapiService.Builder().config(config).build();
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        //设置金额, 以分为单位 金额根据实际情况自行填写
        amount.setTotal(vo.getAmount());
        amount.setCurrency("CNY");
        request.setAmount(amount);
        //绑定微信支 公众号的appid 根据实际情况自行获取填写
        request.setAppid(MtWeChatConstant.APP_ID);
        // 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一,根据自己需求自己定义
        request.setMchid(MtWeChatConstant.MERCHANT_ID);
        request.setDescription("产品描述");
        //支付成功后回调的URL
        request.setNotifyUrl(MtWeChatConstant.PAY_NOTIFY_URL);
        //ZHE
        request.setOutTradeNo(UUID.randomUUID().toString().replace("-",""));
        //自定义信息, 在支付订单回调可以取到(自行替换,如不需要可注释掉)
        Map meetIfno = new HashMap();
        meetIfno.put("storeId", vo.getStoreId());
        request.setAttach(JSON.toJSONString(meetIfno));
        Payer payer = new Payer();
        //支付人员的的openid
        payer.setOpenid(vo.getOpenid());
        request.setPayer(payer);
        //调用支付接口
        PrepayResponse response = service.prepay(request);
        //响应体中有预支付订单号
        response.getPrepayId();
        //以上为获取预支付订单号步骤
        /**
         * 下面为使用加密获取密钥,也需要返回给前端
         */
        long timeStamp = System.currentTimeMillis() / 1000;
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        //构造签名参数(appid更换为实际支付人员的appid)
        // 构造签名所需的消息
        String message =
                MtWeChatConstant.APP_ID + "\n" + timeStamp + "\n" + nonceStr + "\n" + "prepay_id=" + response.getPrepayId() + "\n";
//        System.out.println("下单的id："+response.getPrepayId());
        // 使用 RSASigner 创建签名器
        RSASigner rsaSigner = new RSASigner(MtWeChatConstant.MERCHANT_SERIAL_NUMBER, MtWeChatConstant.MERCHANT_PRIVATE_KEY);
        // 生成签名
        String sign = rsaSigner.sign(message).getSign();
        // 输出签名，供前端调用支付
//        System.out.println("签名："+sign);
        JSONObject payParams = new JSONObject();
        payParams.put("appId",MtWeChatConstant.APP_ID);
        payParams.put("timeStamp",timeStamp);
        payParams.put("nonceStr",nonceStr);
        payParams.put("package",response.getPrepayId());
        payParams.put("paySign",sign);
        return ResponseDTO.success(200,"下单成功",payParams);
    }


    // 处理订单回调事件
    @PostMapping("/notify")
    @ResponseBody
    public String savePayOrder(
            @RequestHeader(value = "Wechatpay-Signature") String sign,
            @RequestHeader(value = "Wechatpay-Serial") String serial,
            @RequestHeader(value = "Wechatpay-Nonce") String nonce,
            @RequestHeader(value = "Wechatpay-Timestamp") String timestamp,
            @RequestHeader(value = "Wechatpay-Signature-Type") String signType,
            @RequestBody String info
    ) {

        // 构造 RequestParam
        com.wechat.pay.java.core.notification.RequestParam requestParam = new com.wechat.pay.java.core.notification.RequestParam.Builder()
                .serialNumber(serial)
                .nonce(nonce)
                .signature(sign)
                .timestamp(timestamp)
                .body(info)
                .build();

        // 如果已经初始化了 RSAAutoCertificateConfig，可直接使用
        NotificationConfig config = new RSAAutoCertificateConfig.Builder()
                .merchantId(MtWeChatConstant.MERCHANT_ID)
                .privateKeyFromPath(MtWeChatConstant.MERCHANT_PRIVATE_KEY_PATH)
                .merchantSerialNumber(MtWeChatConstant.MERCHANT_SERIAL_NUMBER)
                .apiV3Key(MtWeChatConstant.API_V3_KEY)
                .build();

        // 初始化 NotificationParser
        NotificationParser parser = new NotificationParser(config);

        // 以支付通知回调为例，验签、解密并转换成 Transaction
        Transaction payInfo = null;
        try {
            //回调所有的支付信息
            payInfo = parser.parse(requestParam, Transaction.class);
        } catch (Exception e) {
            return "FAIL";
        }
        //获取支付人员的信息
        TransactionPayer payerInfo = payInfo.getPayer();
        //获取自定义信息
        com.alibaba.fastjson2.JSONObject meetInfo = JSON.parseObject(payInfo.getAttach());
        //具体信息自行获取即可
        JSONObject jsonObject = new JSONObject(meetInfo);
        String storeId = jsonObject.getString("storeId");
        System.out.println("商户的id是：" + storeId);
        System.out.println("支付的金额是：" + payInfo.getAmount().getTotal() * 0.01);
        System.out.println("流水号是：" + payInfo.getTransactionId());
        System.out.println("支付的时间是：" + payInfo.getSuccessTime());
        WXPayOrderVo wxPayOrderVo = new WXPayOrderVo();
        wxPayOrderVo.setOrderMoney(payInfo.getAmount().getTotal() * 0.01);
        wxPayOrderVo.setOrderCreateTime(payInfo.getSuccessTime());
        wxPayOrderVo.setOrderStoreId(storeId);
        wxPayOrderVo.setOrderCallbackNumber(payInfo.getTransactionId());
        wechatService.insertOrder(wxPayOrderVo);
        return "SUCCESS";
    }


    //支付成功之后可以在这里回调一个广告的界面
    @GetMapping("/advertisement")
    public String advertisement(){
        return "advertisement";
    }
}
