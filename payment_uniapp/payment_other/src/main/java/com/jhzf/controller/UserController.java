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

    //修改密码
    @PostMapping("/modifyPwd")
    public ResponseDTO modifyPwd(@RequestBody ModifyPwdVo vo) {
        return userService.modifyPwd(vo);
    }
    //用户注销账户
    @GetMapping("/unsubscribe")
    public ResponseDTO unsubscribe(String userId) {
        //可提现金额和待提现金额都必须为0
        //店铺不能有异常订单
        System.out.println(userId);
        return null;
    }


    @PostMapping("/register")
    public ResponseDTO register(@RequestBody LoginVo loginVo){
        return userService.register(loginVo);
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
