package com.jhzf.mapper;

import com.jhzf.pojo.PaymentOrder;
import com.jhzf.pojo.PaymentStore;
import com.jhzf.vo.reportForm.OrdersVo;
import com.jhzf.vo.reportForm.ReportFormVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 *
 * @Author： 林晓龙
 * @DATE: 2024/4/16 10:04
 */
@Mapper
public interface ReportFormMapper {
    List<ReportFormVo> selectStoreReportForm(@Param("data") String[] data,
                                             @Param("storeId")int storeId,@Param("userId")int userId);
    List<PaymentStore> selectStoreName(@Param("userId")int userId);
    List<PaymentOrder> selectStoreMoney(@Param("data") String[] data,@Param("storeId")int storeId,@Param("userId")int userId);

    //查找店铺所有订单并统计
    List<PaymentOrder> selectStoreOrder(@Param("ordersVo") OrdersVo ordersVo);

    //查找店铺所有订单
    List<PaymentOrder> selectOrder(@Param("ordersVo") OrdersVo ordersVo);
}
