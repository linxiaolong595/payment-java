package com.jhzf.vo.store;

import lombok.Data;

@Data
public class StoreOrderVo {
    private int storeId;
    private String startTime;
    private String endTime;
    private String orderNumber;
}
