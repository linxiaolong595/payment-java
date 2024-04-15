package com.jhzf.controller;

import com.jhzf.config.GetAccessToken;
import com.jhzf.utils.Base64Util;
import com.jhzf.utils.HttpUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
@RestController
@CrossOrigin
public class OcrController {
    @RequestMapping("/ocr/idCard")
    public String OcrIdCard(@RequestParam("file") MultipartFile file){
        long startTime = System.currentTimeMillis();
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件类型
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if(!"jpg".equals(fileType) && !"jpeg".equals(fileType) &&!"png".equals(fileType) &&!"bmp".equals(fileType)){
            return "文件类型不符合要求!";
        }

        // 请求url 身份证
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            byte[] imgData = file.getBytes();
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "id_card_side=" + "front" + "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间(一个月)， 客户端可自行缓存，过期后重新获取。
            String accessToken = GetAccessToken.getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            long endTime = System.currentTimeMillis();
            System.out.println("本次处理耗时：" + (endTime - startTime) + " 毫秒");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "500";
    }

    @RequestMapping("/ocr/businessLicense")
    public String OcrBusinessLicense(@RequestParam("file") MultipartFile file){
        long startTime = System.currentTimeMillis();
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件类型
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if(!"jpg".equals(fileType) && !"jpeg".equals(fileType) &&!"png".equals(fileType) &&!"bmp".equals(fileType)){
            return "文件类型不符合要求!";
        }

        // 请求url 营业执照
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/business_license";
        try {
            byte[] imgData = file.getBytes();
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "id_card_side=" + "front" + "&image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间(一个月)， 客户端可自行缓存，过期后重新获取。
            String accessToken = GetAccessToken.getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            long endTime = System.currentTimeMillis();
            System.out.println("本次处理耗时：" + (endTime - startTime) + " 毫秒");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "500";
    }
}
