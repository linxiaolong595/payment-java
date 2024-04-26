package com.jhzf.mapper;

import com.jhzf.pojo.PaymentAdmin;
import com.jhzf.pojo.PaymentMenu;
import com.jhzf.vo.admin.AdminAuthorityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    //管理员登录
    List<PaymentAdmin> adminLogin (@Param("acc") String acc,@Param("pwd") String pwd);

    //管理员已有权限
    List<PaymentMenu> adminAuthority (int adminId);

    //管理员未有权限
    List<PaymentMenu> adminNoAuthority (int adminId);

    //删除管理员权限
    int deleteAuthority(@Param("adminId") int adminId);
    //管理员权限更新
    int updateAuthority(@Param("adminId") int adminId,@Param("menuId")int menuId);

}
