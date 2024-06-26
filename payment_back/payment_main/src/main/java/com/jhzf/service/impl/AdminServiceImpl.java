package com.jhzf.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.jhzf.mapper.AdminMapper;
import com.jhzf.pojo.PaymentAdmin;
import com.jhzf.service.AdminService;
import com.jhzf.util.JwtUtils;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.admin.adminLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public ResponseDTO adminLogin(adminLoginVo vo) {
        ResponseDTO dto = null;
        List<PaymentAdmin> res = adminMapper.adminLogin(vo.getUsername(), vo.getPassword());
        if (res.size() > 0){
//            System.out.println(res.get(0));
            HashMap<String,Object> map = new HashMap<>();
            map.put("adminId",res.get(0).getAdminId());
            String token = JwtUtils.generateJwt(map);
            redisTemplate.opsForValue().set(token,res,1, TimeUnit.DAYS);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token",token);
            dto = new ResponseDTO(200,"登录成功",token);
        }else {
            dto = new ResponseDTO(201,"账号或密码错误",null);
        }
        return dto;
    }

    @Override
    public ResponseDTO getAdminInfo(String token) {
        System.out.println("token"+token);
        //从redis中拿出管理员的信息
        Object adminInfo = redisTemplate.opsForValue().get(token);
        PaymentAdmin adminInfo2 = JSONObject.parseObject((String) adminInfo, PaymentAdmin.class);
        System.out.println("==========="+adminInfo2);
        System.out.println("===========----"+adminInfo);
        return ResponseDTO.success(200,"获取成功",adminInfo2);
    }
}
