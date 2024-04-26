package com.jhzf.controller;

import com.jhzf.service.impl.AdminAuthorityServiceImpl;
import com.jhzf.service.impl.AdminServiceImpl;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.admin.AdminAuthorityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    //查询角色权限
    @GetMapping("/authority")
    public ResponseDTO getAuthority(@RequestParam("adminId")int adminId){
       return adminAuthorityService.getAuthority(adminId);
    }
    //修改角色权限
    @PostMapping("/updateAuthority")
    public ResponseDTO updateAuthority(@RequestBody AdminAuthorityVo vo){
        return adminAuthorityService.updateAuthority(vo);
    }
}
