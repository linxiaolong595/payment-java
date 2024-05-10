package com.jhzf.config;

import com.wechat.pay.java.core.util.IOUtil;
import com.wechat.pay.java.core.util.PemUtil;

import java.security.PrivateKey;

public class MtWeChatConstant {
    //商户号  (自己商户平台的商户号)
    public static final String MERCHANT_ID = "1611845480";
    public static final String APP_ID = "wx96600d2c9189b7bb";
    public static final String APP_SECRET = "f4cf4ea6c6020bd2a3ab57324b04f643";
    //商户API私钥路径
    public static final String MERCHANT_PRIVATE_KEY_PATH = "payment_uniapp/payment_payment/src/main/resources" +
            "/wxPayConfig" +
            "/apiclient_key" +
            ".pem";
    //商户证书序列号(自己配置的证书序列号)
    public static final String MERCHANT_SERIAL_NUMBER = "7D7FC6A773657BFFF1685A6D875D8F324C2828C2";
    //商户APIV3密钥(自己配置的APIV3)
    public static final String API_V3_KEY = "xiamenyiyiwangluokejiyouxiangong";
    //支付完成后的回调的地址(如果不要回调信息,可以不配置)
    public static final String PAY_NOTIFY_URL = "https://payproject.mynatapp.cc/wechat/notify";
    //CODE的回调网址
    public static final String PAY_OAUTH_CODE_URL = "http://payproject.mynatapp.cc/wechat/wxOauthCodeReturn";
    //退款完成后的回调的地址(如果不要回调信息,可以不配置)
    public static final String REFUND_NOTIFY_URL = "https://xxx.xxxxx.com/services/mt/api/refund/notifi";
    public static final PrivateKey MERCHANT_PRIVATE_KEY;
    public static final String MERCHANT_PRIVATE_KEY_STRING;

    static {
        try {
            MERCHANT_PRIVATE_KEY_STRING = IOUtil.loadStringFromPath(MERCHANT_PRIVATE_KEY_PATH);
            MERCHANT_PRIVATE_KEY = PemUtil.loadPrivateKeyFromString(MERCHANT_PRIVATE_KEY_STRING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
