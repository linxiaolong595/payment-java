package com.jhzf.util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

//提取出身份证的名字和身份证号码
public class ExtractingIDCardInformation {
    public static JSONObject ExtractingInformation(String result,String fileNewName){
        JSONObject jsonObject = JSON.parseObject(result);
        String words_result = jsonObject.getString("words_result");
        JSONObject words_result_json = JSON.parseObject(words_result);
        //从请求中提取出名字
        String name_box = words_result_json.getString("姓名");
        JSONObject name_box_json = JSON.parseObject(name_box);
        String name = name_box_json.getString("words");
        //从请求中提取出身份证号码
        String idNum_box = words_result_json.getString("公民身份号码");
        JSONObject idNum_box_json = JSON.parseObject(idNum_box);
        String idNum = idNum_box_json.getString("words");
        JSONObject information = new JSONObject();
        information.put("idName",name);
        information.put("idNum",idNum);
        information.put("idCardFrontUrl",fileNewName);
        return information;
    }
}
