package com.jhzf.mapper;

import com.jhzf.pojo.PaymentAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    //管理员登录
    List<PaymentAdmin> adminLogin (@Param("acc") String acc,@Param("pwd") String pwd);
}