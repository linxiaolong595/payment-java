package com.jhzf.pojo;

import lombok.Data;

@Data
public class PaymentUser {

  private long userId;
  private String userAccount;
  private String userPwd;
  private String userName;
  private String userIdNumber;
  private String userNickname;
  private String userCreatetime;
  private long userIsDelete;
  private long userIsAuthentication;
  private long userAccountType;
  private PaymentStore paymentStore;
}
