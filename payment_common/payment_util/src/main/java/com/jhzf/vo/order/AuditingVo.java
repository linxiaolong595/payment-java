package com.jhzf.vo.order;

import lombok.Data;

/**
 * @author 吴政顺
 * @date 2024/4/29 15:27
 */
@Data
public class AuditingVo {
    private String payoutsId;
    private String payoutsStatus;
    private String payoutsRemarks;
}
