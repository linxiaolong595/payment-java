package com.jhzf.service;

import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.admin.adminLoginVo;

public interface AdminService {
    ResponseDTO adminLogin(adminLoginVo vo);
    ResponseDTO getAdminInfo(String token);
}
