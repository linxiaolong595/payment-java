package com.jhzf.mapper;

import com.jhzf.pojo.PaymentOrder;
import com.jhzf.pojo.PaymentStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreOrderMapper {
    // 获取当月没退款的钱
    String getStoreProfitMonthOrders(@Param("storeId")int storeId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    String getStoreRebackMonthOrder(@Param("storeId")int storeId, @Param("startTime") String startTime, @Param("endTime") String endTime);
    String profitOrderCount(@Param("storeId")int storeId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    String rebackOrderCount(@Param("storeId")int storeId, @Param("startTime") String startTime, @Param("endTime") String endTime);
    // 获取当天全部订单
//    List<PaymentStore> getStoreDailyOrder();
}
