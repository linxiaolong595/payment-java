package com.jhzf.vo.store;

import lombok.Data;

@Data
public class StoreOrderVo {
    private int storeId;
    private String storeName;
    private String startTime;
    private String endTime;
    private String orderNumber;
    private String orderCreatetime;
    private String orderStatus;
    private int pageNum;
    private int pageSize;
}
