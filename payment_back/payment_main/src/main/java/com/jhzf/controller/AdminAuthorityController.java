package com.jhzf.controller;

import com.jhzf.service.impl.AdminAuthorityServiceImpl;
import com.jhzf.service.impl.AdminServiceImpl;
import com.jhzf.util.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 吴政顺
 * @date 2024/4/25 10:32
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminAuthorityController {
    @Autowired
    private AdminAuthorityServiceImpl adminAuthorityService;

    @PostMapping("/authority")
    public ResponseDTO getAuthority(int adminId){
       return adminAuthorityService.getAuthority(adminId);
    }
}
