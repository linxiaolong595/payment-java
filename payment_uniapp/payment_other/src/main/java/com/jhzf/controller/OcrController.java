package com.jhzf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhzf.config.GetAccessToken;
import com.jhzf.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

@RestController
@CrossOrigin
public class OcrController {
    @Value("${upload-dir}")
    private String uploadDir;

    @Value("${server.address}")
    private String address;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/ocr/idcard")
    public ResponseDTO OcrIdCard(@RequestParam("multipartFile") MultipartFile file) throws IOException {
        File file1 = new File(uploadDir);
        if(file1.exists()){
            file1.mkdirs();
        }
        String result = null;
        long startTime = System.currentTimeMillis();
        //获取文件名
        String fileName = file.getOriginalFilename();

        // 获取文件名
        String originalFileName = file.getOriginalFilename();
        //切后缀
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        // 生成新的文件名，加上UUID作为后缀，确保唯一性
        String newFileName = UUID.randomUUID().toString() + extension;
        // 指定文件保存的路径
        String filePath = uploadDir + newFileName;


        //获取文件类型
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if(!"jpg".equals(fileType) && !"jpeg".equals(fileType) &&!"png".equals(fileType) &&!"bmp".equals(fileType)){
            return ResponseDTO.error(203,"文件类型不符合要求!");
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

            // 将文件保存到指定路径
            file.transferTo(new File(filePath));
            String backUrl="http://"+address+":"+port+"/"+ newFileName;

            if (jsonObject.getString("image_status").equals("normal")){
                if (jsonObject.containsKey("idcard_number_type")){
                    int res = Integer.parseInt(jsonObject.getString("idcard_number_type"));
                    if (res != 1){
                        return ResponseDTO.error(202,"请检查您上传的图片类型是否正确!");
                    }
                        return ResponseDTO.success(200,"信息面识别成功",ExtractingIDCardInformation.ExtractingInformation(result,backUrl));
                }
            }
            if (jsonObject.getString("image_status").equals("reversed_side")){
                JSONObject information = new JSONObject();
                information.put("idCardBackUrl",backUrl);
                return ResponseDTO.success(201,"国徽面识别成功",information);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseDTO.error(204,"上传的图片不是身份证!");
    }

    @RequestMapping("/ocr/businessLicense")
    public ResponseDTO OcrBusinessLicense(@RequestParam("multipartFile") MultipartFile file){
        long startTime = System.currentTimeMillis();
        //获取文件名
        String fileName = file.getOriginalFilename();

        // 获取文件名
        String originalFileName = file.getOriginalFilename();
        //切后缀
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        // 生成新的文件名，加上UUID作为后缀，确保唯一性
        String newFileName = UUID.randomUUID().toString() + extension;
        // 指定文件保存的路径
        String filePath = uploadDir + newFileName;

        //获取文件类型
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if(!"jpg".equals(fileType) && !"jpeg".equals(fileType) &&!"png".equals(fileType) &&!"bmp".equals(fileType)){
            return ResponseDTO.error(203,"文件类型不符合要求!");
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
            //用 Fastjson 解析 JSON 字符串为 JSONObject 对象
            JSONObject jsonObject = JSON.parseObject(result);
            // 将文件保存到指定路径
            file.transferTo(new File(filePath));
            String backUrl="http://"+address+":"+port+"/"+ newFileName;
            long endTime = System.currentTimeMillis();
            System.out.println("本次处理耗时：" + (endTime - startTime) + " 毫秒");
            return ResponseDTO.success(200,"营业执照识别成功", ExtractingBusinessLicenseInformation.ExtractingInformation(result,backUrl));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseDTO.error(204,"上传的图片不是营业执照!");
    }

    @RequestMapping("/ocr/uploadMerchant")
    public ResponseDTO uploadMerchant(@RequestParam("multipartFile") MultipartFile file){
        if (file.isEmpty()) {
            return ResponseDTO.error();
        }
        //不存在则创建新文件夹
        File file1 = new File(uploadDir);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件类型
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if(!"jpg".equals(fileType) && !"jpeg".equals(fileType) &&!"png".equals(fileType) &&!"bmp".equals(fileType)){
            return ResponseDTO.error(203,"文件类型不符合要求!");
        }

        try {
            // 获取文件名
            String originalFileName = file.getOriginalFilename();
            //切后缀
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            // 生成新的文件名，加上UUID作为后缀，确保唯一性
            String newFileName = UUID.randomUUID().toString() + extension;
            // 指定文件保存的路径
            String filePath = uploadDir + newFileName;
            // 将文件保存到指定路径
            file.transferTo(new File(filePath));
            System.out.println(newFileName);
            String backUrl="http://"+address+":"+port+"/"+ newFileName;
            JSONObject information = new JSONObject();
            information.put("storeHeadImageUrl",backUrl);
            return ResponseDTO.success(200,"商铺照片上传成功",information);
        } catch (IOException  e) {
            e.printStackTrace();
            return ResponseDTO.error();
        }

    }
}