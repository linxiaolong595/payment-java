package com.jhzf.service;

import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.admin.AdminAuthorityVo;

/**
 * @author 吴政顺
 * @date 2024/4/25 10:33
 */
public interface AdminAuthorityService {
    ResponseDTO getAuthority(int adminId);
    ResponseDTO updateAuthority(AdminAuthorityVo vo);
}
