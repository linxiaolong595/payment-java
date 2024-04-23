package com.jhzf.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;



public class ExtractingBusinessLicenseInformation {
    public static JSONObject ExtractingInformation(String result,String fileNewName){
        // 使用com.alibaba.fastjson.JSON将字符串转换为JSONObject
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject wordsResult = jsonObject.getJSONObject("words_result");
        String businessScopeWords = wordsResult.getJSONObject("经营范围").getString("words");
        String businessName = wordsResult.getJSONObject("单位名称").getString("words");
        String legalPerson = wordsResult.getJSONObject("法人").getString("words");
        String socialCreditCode = wordsResult.getJSONObject("社会信用代码").getString("words");
        String address = wordsResult.getJSONObject("地址").getString("words");

        JSONObject information = new JSONObject();
        information.put("businessScopeWords",businessScopeWords);
        information.put("legalPerson",legalPerson);
        information.put("socialCreditCode",socialCreditCode);
        information.put("address",address);
        information.put("businessName",businessName);
        information.put("businessUrl",fileNewName);
        return information;
    }
}
