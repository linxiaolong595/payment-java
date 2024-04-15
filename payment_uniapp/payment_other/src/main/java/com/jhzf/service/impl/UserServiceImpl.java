package com.jhzf.service.impl;

import com.jhzf.mapper.UserMapper;
import com.jhzf.pojo.PaymentUser;
import com.jhzf.service.UserService;
import com.jhzf.util.Md5;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.user.LoginVo;
import com.jhzf.vo.user.ModifyPwdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public ResponseDTO regist(LoginVo loginVo) {
        int res = userMapper.selectUser(loginVo.getAccount());
        if(res == 1){
            return ResponseDTO.error(0,"注册的手机号已存在");
        }else{
            String messageCode = redisTemplate.opsForValue().get(loginVo.getAccount());
            if(loginVo.getCode().equals(messageCode)){
                System.out.println(loginVo.getAccount() + " " + loginVo.getPwd() + " " + loginVo.getNickName());
                userMapper.regist(loginVo.getAccount(), Md5.getString(loginVo.getPwd()),loginVo.getNickName());
                return ResponseDTO.success(200,"注册成功",null);
            }else{
                return ResponseDTO.error(0,"验证码错误，请重新输入");
            }
        }
    }

    @Override
    public ResponseDTO accountLogin(String account, String pwd) {
        int res = userMapper.accountLogin(account, Md5.getString(pwd));
        if(res == 1){
            return ResponseDTO.success(200,"登录成功",null);
        }else{
            return ResponseDTO.error(0,"登录失败，账号或密码错误");
        }
    }

    @Override
    public ResponseDTO messageCodeLogin(String account, String code) {
        int count = userMapper.selectUser(account);
        if(count == 1){
            String messageCode = redisTemplate.opsForValue().get(account);
            if(code.equals(messageCode)){
                return ResponseDTO.success(200,"登录成功",null);
            }else{
                return ResponseDTO.error(0,"验证码错误");
            }
        }else{
            return ResponseDTO.error(0,"账号不存在,请先去注册");
        }
    }

    @Override
    public ResponseDTO modifyPwd(ModifyPwdVo vo) {
        ResponseDTO dto = null;
        List<PaymentUser> userMsg = userMapper.selectUserMsg(vo.getUserId(), vo.getOldPwd());
        if (userMsg.size() > 0){
            int i = userMapper.modifyPwd(vo.getUserId(), vo.getNewPwd());
            if (i > 0){
                dto = new ResponseDTO(1001,"修改成功",null);
            }else {
                dto = new ResponseDTO(1002,"修改失败",null);
            }
        }else {
            dto = new ResponseDTO(1003,"旧密码输入错误，修改失败",null);
        }
        return dto;
    }
}
