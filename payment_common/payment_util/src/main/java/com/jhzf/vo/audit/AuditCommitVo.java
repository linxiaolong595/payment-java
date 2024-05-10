package com.jhzf.vo.audit;

import lombok.Data;

/**
 * @author 吴政顺
 * @date 2024/4/29 16:02
 */
@Data
public class AuditCommitVo {
    private int auditId;
    private int auditStatus;
    private String auditSuggestion;
}
