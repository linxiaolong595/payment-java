package com.jhzf.vo.order;

import lombok.Data;

/**
 * @author 吴政顺
 * @date 2024/4/26 17:13
 */
@Data
public class WithdrawalVo {
    private String payoutsNumber;
    private String userName;
    private String payoutsTime;
    private String payoutsStatus;
    private int pageNum;
    private int pageSize;
}
