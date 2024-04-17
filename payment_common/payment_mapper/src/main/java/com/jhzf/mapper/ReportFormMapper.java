package com.jhzf.mapper;

import com.jhzf.pojo.PaymentOrder;
import com.jhzf.pojo.PaymentStore;
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
    List<ReportFormVo> selectStoreReportForm(@Param("data") String[] data,@Param("storeId")int storeId);
    List<PaymentStore> selectStoreName();
    List<PaymentOrder> selectStoreMoney(@Param("data") String[] data,@Param("storeId")int storeId);
}
