package com.jhzf.filter;

import com.alibaba.fastjson.JSON;
import com.jhzf.util.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthFilter implements GlobalFilter {
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1、获取request，response ==>HttpServletRequest
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //2、判断白名单
        String[] whiteList = {"/user/login","user/reg","/code"};
        //请求的url
        String url = request.getURI().getPath();
        for (String item : whiteList) {
            if (item.equals(url)) {
                //放行
                System.out.println("路由白名单，放行");
                return chain.filter(exchange);
            }
        }

        //3、获取请求头里面的token
        List<String> strings = request.getHeaders().get("X-Token");
        //4、从redis里面取出数据，取得到说明有效，娶不到说明过期或者不存在
        if (strings != null && strings.size() > 0){
            String token = strings.get(0);
            System.out.println(token);
            if (redisTemplate.hasKey(token)){
                System.out.println("存在token，放行");
                //放行
                return chain.filter(exchange);
            }else {
                //token不存在，响应给客户端
                response.setStatusCode(HttpStatus.UNAUTHORIZED);//设置响应状态码为401
                response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                ResponseDTO dto = ResponseDTO.error(401,"token不存在或过期了");
                byte[] bytes = JSON.toJSONBytes(dto);
                DataBuffer wrap = response.bufferFactory().wrap(bytes);
                return response.writeWith(Mono.just(wrap));
            }
        }else {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);//设置响应状态码为401
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            ResponseDTO dto = ResponseDTO.error(401,"token不存在或过期了");
            byte[] bytes = JSON.toJSONBytes(dto);
            DataBuffer wrap = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(wrap));
        }

    }
}
