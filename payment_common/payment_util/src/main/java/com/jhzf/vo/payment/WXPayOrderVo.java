package com.jhzf.vo.payment;

import lombok.Data;

@Data
public class WXPayOrderVo {
    private String orderNumber;
    private String orderCallbackNumber;
    private double orderMoney;
    private String orderStoreId;
    private String orderCreateTime;
    private String orderPayWay;
}
