package com.jhzf.vo.audit;

import lombok.Data;


@Data
public class AuditVo {
    long auditId;
    String userId;
    String auditStoreName;
    String auditStoreHeadImage;
    String auditStoreNumber;
    String auditStoreIdentifyImage;
    String auditStoreIdentifyCardFront;
    String auditStoreIdentifyCardBack;
}
