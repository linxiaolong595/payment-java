package com.jhzf.service.Impl;

import com.jhzf.mapper.PaymentMapper;
import com.jhzf.service.WechatService;
import com.jhzf.vo.payment.WXPayOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


@Service
public class WechatServiceImpl implements WechatService {
    @Autowired
    PaymentMapper paymentMapper;
    @Override
    //将支付的信息存到数据库中
    public void insertOrder(WXPayOrderVo vo) {
        // 获取当前日期时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateTime = dateFormat.format(new Date());
        // 使用 UUID 生成唯一标识符作为订单编号
        String randomNumber  = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        // 组合订单编号
        String orderNumber = "ORD" +"-" + dateTime + "-" + randomNumber;
        vo.setOrderNumber(orderNumber);
        vo.setOrderPayWay("微信");
        paymentMapper.insertOrder(vo);
    }
}
