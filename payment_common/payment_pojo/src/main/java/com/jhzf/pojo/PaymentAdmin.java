package com.jhzf.pojo;

import lombok.Data;

@Data
public class PaymentAdmin {

  private long adminId;
  private String adminAccount;
  private String adminPwd;
  private String adminName;
  private String adminStatus;
  private String adminIsDelete;
  private long adminRoleId;
  private java.sql.Timestamp adminCreateTime;
}
