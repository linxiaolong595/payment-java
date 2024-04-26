package com.jhzf.service.impl;

import com.jhzf.mapper.AdminMapper;
import com.jhzf.pojo.PaymentMenu;
import com.jhzf.service.AdminAuthorityService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.admin.AdminAuthorityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴政顺
 * @date 2024/4/25 10:32
 */
@Service
public class AdminAuthorityServiceImpl implements AdminAuthorityService {
    @Autowired
    private AdminMapper adminMapper;
    //查询角色权限
    @Override
    public ResponseDTO getAuthority(int adminId) {
        List<PaymentMenu> Existing = adminMapper.adminAuthority(adminId);
        List<PaymentMenu> NotPresent = adminMapper.adminNoAuthority(adminId);
        Map<String,Object> res = new HashMap<>();
        res.put("Existing",Existing);
        res.put("NotPresent",NotPresent);
        if (!res.isEmpty()){
            return ResponseDTO.success(200,"获取管理员权限成功", res);
        }else {
            return ResponseDTO.success(201,"获取管理员权限失败", res);
        }
    }
    //修改角色权限
    @Override
    @Transactional //事务注解
    public ResponseDTO updateAuthority(AdminAuthorityVo vo) {
        int res = adminMapper.deleteAuthority(vo.getAdminId());
        if (res > 0 ){
            int adminId = vo.getAdminId();
        try {
            for (int menuId : vo.getMenuId()) {
                int res1 = adminMapper.updateAuthority(adminId, menuId);
                if (res1 <= 0) {
                    throw new RuntimeException("权限更新失败");
                }
            }
            return ResponseDTO.success(200, "权限更新成功");
                    } catch (Exception e) {
            // 捕获异常，事务会自动回滚
            return ResponseDTO.error(201, "权限更新失败");
        }
        }else {
            return ResponseDTO.error(201,"权限更新失败");
        }
    }
}
