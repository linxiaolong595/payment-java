package com.jhzf.vo.user;

import lombok.Data;

@Data
public class ModifyPwdVo {
    //用户id
    private String userId;
    //旧密码
    private String oldPwd;
    //新密码
    private String newPwd;
}
