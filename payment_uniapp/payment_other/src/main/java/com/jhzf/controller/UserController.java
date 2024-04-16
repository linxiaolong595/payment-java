package com.jhzf.controller;


import com.jhzf.service.UserService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.user.LoginVo;
import com.jhzf.vo.user.ModifyPwdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/modifyPwd")
    public ResponseDTO modifyPwd(@RequestBody ModifyPwdVo vo) {
        return userService.modifyPwd(vo);
    }

    @PostMapping("/register")
    public ResponseDTO register(@RequestBody LoginVo loginVo){
        return userService.regist(loginVo);
    }

    @PostMapping("/accountLogin")
    public ResponseDTO accountLogin(@RequestBody LoginVo loginVo){
        return userService.accountLogin(loginVo.getAccount(), loginVo.getPwd());
    }
    @PostMapping("/messageCodeLogin")
    public ResponseDTO messageCodeLogin(@RequestBody LoginVo loginVo){
        return userService.messageCodeLogin(loginVo.getAccount(),loginVo.getCode());
    }
}
