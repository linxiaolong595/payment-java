package com.jhzf.service.Impl;

import com.alibaba.fastjson.JSON;
import com.jhzf.service.QrCodeService;
import com.jhzf.util.QRCodeUtil;
import com.jhzf.vo.user.QrCodeVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service
public class QrCodeServiceImpl implements QrCodeService {
    @Value("${upload-dir}")
    private String uploadDir;
    @Value("${server.address}")
    private String address;
    @Value("${server.port}")
    private String port;

    private static final String FileFormat=".png";

    @Override
    public String createStoreCode(QrCodeVo qrCodeVo) {
        //组装url信息
        String urlMsg = JSON.toJSONString(qrCodeVo);
        byte[] utf8Bytes = urlMsg.getBytes(StandardCharsets.UTF_8);
        String urlSafeBase64String = Base64.getUrlEncoder().withoutPadding().encodeToString(utf8Bytes);
        //校验商户信息
        String url = "http://payproject.mynatapp.cc/qrCode/pay-entry/" + urlSafeBase64String;
        String fileName = UUID.randomUUID() + FileFormat;
        QRCodeUtil.createCodeToFile(url,new File(uploadDir), fileName);
        //返回文件访问路径
        String backUrl = "http://" + address + ":" + port + "/" + fileName;
        System.out.println(backUrl);
        return backUrl;
    }
}
