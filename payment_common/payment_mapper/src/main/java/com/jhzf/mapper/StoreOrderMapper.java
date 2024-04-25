package com.jhzf.mapper;

import com.jhzf.pojo.PaymentOrder;
import com.jhzf.pojo.PaymentStore;
import com.jhzf.vo.store.StoreOrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreOrderMapper {
    // 获取当月没退款的钱
    String getStoreProfitMonthOrders(@Param("storeId")int storeId, @Param("startTime") String startTime, @Param("endTime") String endTime);
    // 退款的钱
    String getStoreRebackMonthOrder(@Param("storeId")int storeId, @Param("startTime") String startTime, @Param("endTime") String endTime);
    // 没退款的订单
    String profitOrderCount(@Param("storeId")int storeId, @Param("startTime") String startTime, @Param("endTime") String endTime);
    // 获取退款的订单
    String rebackOrderCount(@Param("storeId")int storeId, @Param("startTime") String startTime, @Param("endTime") String endTime);
    // 获取当天全部订单
    List<PaymentOrder> getStoreDailyOrder(@Param("storeId")int storeId,@Param("startTime") String startTime,@Param("endTime")String endTime);
    // 获取订单详情
    List<PaymentOrder> getOrderDetail(@Param("orderNumber")String orderNumber);

    //后台订单组合查询
    List<PaymentOrder> getStoreOrderBack(StoreOrderVo storeOrderVo);
}
