package com.jhzf.mapper;

import com.jhzf.pojo.PaymentPayouts;
import com.jhzf.vo.order.AuditingVo;
import com.jhzf.vo.order.WithdrawalVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 吴政顺
 * @date 2024/4/28 11:17
 */
@Mapper
public interface WithdrawalMapper {
    List<PaymentPayouts> WithdrawalOlder(WithdrawalVo vo);  //查询提现待审核订单
    int withdrawalAuditing(AuditingVo vo); //提现订单审核
    PaymentPayouts WithdrawalDetails(int payoutsId);  //提现订单详情
}
