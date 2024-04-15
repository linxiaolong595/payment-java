package com.jhzf.mapper;

import com.jhzf.pojo.PaymentOrder;
import com.jhzf.pojo.PaymentStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreOrderMapper {
    // 获取当月的订单
    List<PaymentOrder> getStoreMonthOrder(@Param("storeId")int storeId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    // 获取当天全部订单
    List<PaymentStore> getStoreDailyOrder();
}
