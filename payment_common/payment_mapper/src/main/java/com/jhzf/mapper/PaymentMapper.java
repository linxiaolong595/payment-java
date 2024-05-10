package com.jhzf.mapper;

import com.jhzf.vo.payment.WXPayOrderVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    //微信支付成功之后存储
    int insertOrder(WXPayOrderVo vo);
}
