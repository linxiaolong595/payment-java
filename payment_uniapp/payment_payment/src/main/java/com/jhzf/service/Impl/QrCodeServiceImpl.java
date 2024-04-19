package com.jhzf.service.Impl;

import com.alibaba.fastjson.JSON;
import com.jhzf.service.QrCodeService;
import com.jhzf.util.QRCodeUtil;
import com.jhzf.vo.user.QrCodeVo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    private static final String RootPath="D:\\HBuilderX";
    private static final String FileFormat=".png";
    private static final ThreadLocal<SimpleDateFormat> LOCALDATEFORMAT=ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmss"));

    @Override
    public String createStoreCode(QrCodeVo qrCodeVo) {

        //组装url信息
        String urlMsg = JSON.toJSONString(qrCodeVo);
        byte[] utf8Bytes = urlMsg.getBytes(StandardCharsets.UTF_8);
        String urlSafeBase64String = Base64.getUrlEncoder().withoutPadding().encodeToString(utf8Bytes);
        System.out.println("Base64 编码后的数据:" + urlSafeBase64String);



        //校验商户信息
        String url = "http://192.168.1.49:8110/qrCode/pay-entry/" + urlSafeBase64String;
        System.out.println(url);

        final String fileName=LOCALDATEFORMAT.get().format(new Date());
        QRCodeUtil.createCodeToFile(url,new File(RootPath),fileName+FileFormat);
        return null;
    }
}
