package com.jhzf.vo.store;

import lombok.Data;

/**
 * @author 吴政顺
 * @date 2024/4/23 15:05
 */
@Data
public class SelectStoreVo {
    private String storeIdNum;
    private String storeName;
    private String storeIsStatus;
    private String startDate;
    private String endDate;
    private String userAccount;
    private int pageNum;
    private int pageSize;
}
