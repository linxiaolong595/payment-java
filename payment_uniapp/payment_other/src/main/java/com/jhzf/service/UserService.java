package com.jhzf.service;

import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.user.LoginVo;
import com.jhzf.vo.user.ModifyPwdVo;
import com.jhzf.vo.user.PayOutVo;

public interface UserService {
    ResponseDTO modifyPwd(ModifyPwdVo vo);
    
    ResponseDTO register(LoginVo loginVo);

    // 账号密码登录
    ResponseDTO accountLogin(String account, String pwd);

    // 短信验证码登录
    ResponseDTO messageCodeLogin(String account, String code);
    // 查询账户下可提现 待审查金额
    ResponseDTO getCashOutMoney(int userId);
    // 查询可提现店铺
    ResponseDTO getCashOutStore(int userId);
    ResponseDTO getStoreCashMoney(int storeId,int userId);
    ResponseDTO doCashOut(PayOutVo payOutVo);
    ResponseDTO resetPwd(ResetPwdVo resetPwdVo);
}
