package com.jhzf.mapper;

import com.jhzf.pojo.PaymentUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    //修改密码之前先查询用户旧密码是否输入正确
    List<PaymentUser> selectUserMsg(@Param("userId") String userId, @Param("oldPwd") String oldPwd);
    //修改密码
    int modifyPwd(@Param("userId") String userId, @Param("newPwd") String newPwd);
}
