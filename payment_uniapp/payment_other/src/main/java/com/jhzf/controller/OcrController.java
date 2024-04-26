package com.jhzf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhzf.config.GetAccessToken;
import com.jhzf.service.UserService;
import com.jhzf.util.*;
import com.jhzf.vo.user.CertificationVo;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
public class OcrController {
    @Autowired
    private UserService userService;

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
    //实名认证的接口
    @RequestMapping("/ocr/certification")
    public ResponseDTO authentication(@RequestBody CertificationVo vo) {
        ResponseDTO dto = null;
        String msg = main(vo.getUserName(), vo.getUserIdCard());
//        String msg = "{\"code\":\"10000\",\"message\":\"成功\",\"data\":{\"result\":\"2\"},\"seqNo\":\"6L5V6122240412011038866\"}";
        // 使用 Fastjson 解析 JSON 字符串为 JSONObject 对象
        JSONObject jsonObject = JSON.parseObject(msg);
        // 获取各个字段的值
        String code = jsonObject.getString("code");
        if (code.contains("10000")){
            // 获取 data 字段的值，并将其解析为一个新的 JSONObject 对象
            JSONObject dataObject = jsonObject.getJSONObject("data");
            int result = Integer.parseInt(dataObject.getString("result"));
            if (result == 1){
                System.out.println("验证一致");
                dto = userService.authentication(vo.getUserId());
            }
        }else {
            System.out.println("验证失败");
            dto = ResponseDTO.error(201,"验证失败");
        }
        return dto;
    }


    public String main(String name,String idcard) {
        String host = "http://checkone.market.alicloudapi.com";
        String path = "/communication/personal/10101";
        String method = "POST";
        String appcode = "c52aa73306984a65a0825a301e3ca60c";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("name", name);
        bodys.put("idcard", idcard);
        HttpResponse response = null;
        String msg = null;
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */

            response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//            System.out.println(response.toString());
            //获取response的body
            msg = EntityUtils.toString(response.getEntity());
            System.out.println(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }
}