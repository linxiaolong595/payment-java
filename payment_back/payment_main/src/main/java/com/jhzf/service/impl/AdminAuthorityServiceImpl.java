package com.jhzf.service.impl;

import com.jhzf.mapper.AdminMapper;
import com.jhzf.pojo.PaymentMenu;
import com.jhzf.service.AdminAuthorityService;
import com.jhzf.util.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 吴政顺
 * @date 2024/4/25 10:32
 */
@Service
public class AdminAuthorityServiceImpl implements AdminAuthorityService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public ResponseDTO getAuthority(int adminId) {
        List<PaymentMenu> res = adminMapper.adminAuthority(adminId);
        if (res != null){
            return ResponseDTO.success(200,"获取管理员权限成功", res);
        }else {
            return ResponseDTO.success(201,"获取管理员权限失败", res);
        }
    }
}
