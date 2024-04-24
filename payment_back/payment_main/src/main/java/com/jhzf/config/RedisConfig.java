package com.jhzf.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory builder){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(builder);
        //创建一fastjson针对redis所作的序列化对象
        FastJsonRedisSerializer<String> serializer = new FastJsonRedisSerializer<>(String.class);
        //格式转换：key/value ==>String
        redisTemplate.setKeySerializer(new StringRedisSerializer());//针对key进行序列化
        redisTemplate.setValueSerializer(serializer);//针对value进行序列化
        //格式转换: hash, set, zset, List类型的
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        //初始化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
