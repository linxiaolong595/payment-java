package com.jhzf.service.impl;

import com.jhzf.mapper.UserMapper;
import com.jhzf.pojo.PaymentUser;
import com.jhzf.service.UserService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.user.ModifyPwdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

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
