package com.jhzf.vo.user;

import lombok.Data;

@Data
public class LoginVo {
    private String account;
    private String pwd;
    private String code;
    private String nickName;
}
