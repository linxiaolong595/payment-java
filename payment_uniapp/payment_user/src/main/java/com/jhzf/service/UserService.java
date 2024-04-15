package com.jhzf.service;

import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.user.ModifyPwdVo;

public interface UserService {
    ResponseDTO modifyPwd(ModifyPwdVo vo);
}
