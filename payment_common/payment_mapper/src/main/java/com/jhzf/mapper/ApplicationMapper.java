package com.jhzf.mapper;

import com.jhzf.pojo.PaymentAudit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ApplicationMapper {
    int cancellationApplication(@Param("auditId") String auditId);
    List<PaymentAudit> selectAllApplication(@Param("userId") String userId);
}
