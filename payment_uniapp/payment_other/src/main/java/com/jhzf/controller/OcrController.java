package com.jhzf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhzf.config.GetAccessToken;
import com.jhzf.util.Base64Util;
import com.jhzf.util.ExtractingIDCardInformation;
import com.jhzf.util.HttpUtil;
import com.jhzf.util.ResponseDTO;
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
    public ResponseDTO OcrIdCard(@RequestParam("file") MultipartFile file){
        String result = null;
        long startTime = System.currentTimeMillis();
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件类型
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if(!"jpg".equals(fileType) && !"jpeg".equals(fileType) &&!"png".equals(fileType) &&!"bmp".equals(fileType)){
            return ResponseDTO.error(202,"文件类型不符合要求!");
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
            result = HttpUtil.post(url, accessToken, param);
            long endTime = System.currentTimeMillis();
            System.out.println("本次处理耗时：" + (endTime - startTime) + " 毫秒");
            System.out.println(result);
            //用 Fastjson 解析 JSON 字符串为 JSONObject 对象
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.getString("image_status").equals("normal")){
                if (jsonObject.containsKey("idcard_number_type")){
                    int res = Integer.parseInt(jsonObject.getString("idcard_number_type"));
                    if (res != 1){
                        return ResponseDTO.error(201,"请检查您上传的图片类型是否正确!");
                    }
                        return ResponseDTO.success(200,"正面识别成功",ExtractingIDCardInformation.ExtractingInformation(result));
                }
            }
            if (jsonObject.getString("image_status").equals("reversed_side")){
                return ResponseDTO.error(200,"反面识别成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseDTO.error(203,"上传的图片不是身份证!");
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