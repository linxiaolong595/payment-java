package com.jhzf.vo.store;

import lombok.Data;

/**
 * @author 吴政顺
 * @date 2024/4/26 0:12
 */
@Data
public class StoreReviewVo {
    private String auditStoreName;
    private String auditStatus;
    private String startDate;
    private String endDate;
    private String userAccount;
    private int pageNum;
    private int pageSize;
}
