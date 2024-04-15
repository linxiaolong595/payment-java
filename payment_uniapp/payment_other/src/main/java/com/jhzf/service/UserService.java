package com.jhzf.service;

import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.user.ModifyPwdVo;

public interface UserService {
    ResponseDTO modifyPwd(ModifyPwdVo vo);
    
    ResponseDTO regist(LoginVo loginVo);

    // 账号密码登录
    ResponseDTO accountLogin(String account, String pwd);

    // 短信验证码登录
    ResponseDTO messageCodeLogin(String account, String code);
}
