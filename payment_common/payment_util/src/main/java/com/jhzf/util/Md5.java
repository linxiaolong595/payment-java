package com.jhzf.util;

import java.security.MessageDigest;

public class Md5 {
    public static String getString(String s) {
        String encryptedString = null;
        try {
            // 创建 MessageDigest 对象并指定算法为 MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 将密码字符串转换为字节数组
            byte[] passwordBytes = s.getBytes();

            // 计算 MD5 哈希值
            byte[] hashBytes = md.digest(passwordBytes);

            // 将字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            encryptedString = sb.toString();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }
}
