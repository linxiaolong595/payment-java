package com.jhzf.controller;
import com.jhzf.pojo.PaymentAdmin;
import com.jhzf.service.AdminService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.admin.adminLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @PostMapping("/login")
    public ResponseDTO selectStore(@RequestBody adminLoginVo vo){
        return adminService.adminLogin(vo);
    }
    @GetMapping("/test")
    public ResponseDTO test(@RequestParam("X-Token") String token){
        return adminService.getAdminInfo(token);
    }
}
