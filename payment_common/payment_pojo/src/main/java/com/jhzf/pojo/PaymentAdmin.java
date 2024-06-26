package com.jhzf.pojo;

import lombok.Data;

@Data
public class PaymentAdmin {

  private long adminId;
  private String adminAccount;
  private String adminPwd;
  private String adminName;
  private int adminStatus;
  private int adminIsDelete;
  private long adminRoleId;
  private String adminCreateTime;
  private PaymentRole paymentRole;
}
