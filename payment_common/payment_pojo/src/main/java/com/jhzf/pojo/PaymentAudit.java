package com.jhzf.pojo;

import lombok.Data;

@Data
public class PaymentAudit {

  private long auditId;
  private long auditUserId;
  private String auditStoreName;
  private String auditStoreHeadImage;
  private String auditStoreNumber;
  private String auditStoreIdentifyImage;
  private String auditStoreIdentifyCardFront;
  private String auditStoreIdentifyCardBack;
  private String auditStoreCreateTime;
  private long auditStatus;
  private String auditSuggestion;
  private long auditIsDelete;
}
