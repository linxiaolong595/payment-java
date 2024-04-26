package com.jhzf.mapper;

import com.jhzf.pojo.PaymentStore;
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
    int regist(@Param("account")String account, @Param("pwd") String pwd, @Param("nickName")String nickName);

    // 查询账号(手机号)是否已存在
    int selectUser(@Param("account")String account);

    // 账号密码登录
    PaymentUser accountLogin(@Param("account")String account,@Param("pwd")String pwd);

    // 查询每个店可提现金额总量
    List<PaymentStore> selectStoreInfo(@Param("userId")int userId);
    // 查询暂未划分到店铺的金额
    Double getCashOutMoney(@Param("userId")int userId, @Param("startTime")String startTime, @Param("endTime")String endTime,@Param("storeId") int storeId);
    // 将查过的订单置1
    int updateOrderSelectStatus(@Param("startTime")String startTime, @Param("endTime")String endTime,@Param("storeId") int storeId);
    int updateOrderAuditStatus(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("storeId")int storeId);
    // 更新提现金额到对应店铺
    int updateCashMoney(@Param("storeId")long storeId,@Param("cashOutMoney")double cashOutMoney);
    // 更新审核金额到对应店铺
    int updateAuditMoney(@Param("storeId")long storeId,@Param("auditMoney")double auditMoney);
    // 查询审核金额
    Double getAuditingMoney(@Param("userId")int userId,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("storeId")int storeId);
    List<Integer> getCashOutStoreId(@Param("userId")int userId);
    PaymentUser getStoreCastOutMoney(@Param("storeId")int storeId,@Param("userId")int userId);
    int doCashOut(@Param("payoutMoney")double payoutMoney,@Param("storeId")int storeId,@Param("payoutCard")String payoutCard);
    PaymentStore cashOutStoreInfo(@Param("storeId")int storeId);
    int updateStoreCashOutMoney(@Param("storeId")int storeId,@Param("cashOutMoney")double cashOutMoney);


    // 用户实名认证
    int authentication(@Param("userId")String userId);
}
