package com.jhzf.vo.payment;

import lombok.Data;

@Data
public class CreateOrderVo {
    private String openid;
    private int amount;
    private String storeId;
}
