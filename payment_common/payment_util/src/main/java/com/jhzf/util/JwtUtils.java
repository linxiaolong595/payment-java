package com.jhzf.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    //密钥
    private static String signKey = "test123test123test123test123testdfdsfdsfds1234567890";
    //设置过期时间
    private static int expire = 1000 * 10; //24小时
//    private static int expire = 1000 * 10; //10秒

    /**
     * 生成JWT令牌
     * @param claims JWT第二部分负载 payload 中存储的内容
     * @return
     */
    public static String generateJwt(Map<String, Object> claims){
        // 使用Jwts.builder()创建一个JWT Builder对象
        // addClaims方法将claims添加到JWT令牌中
        // signWith方法指定使用HS256算法和signKey密钥对JWT进行签名
        // setExpiration方法设置JWT令牌的过期时间为当前时间加上expire的毫秒数
        // compact方法将JWT Builder对象转换为字符串形式的JWT令牌
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }
    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt){
        // 使用Jwts.parser()创建一个JWT Parser对象
        // setSigningKey方法指定使用signKey密钥对JWT令牌进行解析
        // parseClaimsJws方法对JWT令牌进行解析，得到包含在Claims中的JWT负载
        // getBody方法获取JWT负载（Claims）对象
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

    public static int getExpire() {
        return expire;
    }
}