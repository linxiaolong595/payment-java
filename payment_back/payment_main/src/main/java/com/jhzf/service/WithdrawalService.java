package com.jhzf.service;

import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.order.AuditingVo;
import com.jhzf.vo.order.WithdrawalVo;

/**
 * @author 吴政顺
 * @date 2024/4/26 17:10
 */
public interface WithdrawalService {
    ResponseDTO WithdrawalOlder(WithdrawalVo vo);//查询提现审核订单
    ResponseDTO withdrawalAuditing(AuditingVo vo);//提现订单审核
    ResponseDTO WithdrawalDetails(int payoutsId);//提现订单详情
}
