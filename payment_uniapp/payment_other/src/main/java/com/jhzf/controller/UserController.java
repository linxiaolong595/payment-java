package com.jhzf.controller;


import com.jhzf.service.UserService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.user.LoginVo;
import com.jhzf.vo.user.ModifyPwdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/modifyPwd")
    public ResponseDTO modifyPwd(@RequestBody ModifyPwdVo vo) {
        return userService.modifyPwd(vo);
    }

    @RequestMapping("/regist")
    public ResponseDTO regist(@RequestBody LoginVo loginVo){
        return userService.regist(loginVo);
    }

    @RequestMapping("/accountLogin")
    public ResponseDTO accountLogin(@RequestBody LoginVo loginVo){
        return userService.accountLogin(loginVo.getAccount(), loginVo.getPwd());
    }
    @RequestMapping("/messageCodeLogin")
    public ResponseDTO messageCodeLogin(@RequestBody LoginVo loginVo){
        return userService.messageCodeLogin(loginVo.getAccount(),loginVo.getCode());
    }
}
