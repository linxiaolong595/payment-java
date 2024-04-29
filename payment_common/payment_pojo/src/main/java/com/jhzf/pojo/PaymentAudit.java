package com.jhzf.pojo;

import lombok.Data;

@Data
public class PaymentAudit {

  private long auditId;
  private String auditType;
  private long auditStoreId;
  private String auditStoreName;
  private String auditStoreHeadImage;
  private String auditStoreNumber;
  private String auditStoreIdentifyImage;
  private String auditStoreIdentifyCardFront;
  private String auditStoreIdentifyCardBack;
  private String auditStoreCreatetime;
  private String userAccount; // 用户表中的手机号字段
  private String userName;// 用户表中的用户名字段
}
