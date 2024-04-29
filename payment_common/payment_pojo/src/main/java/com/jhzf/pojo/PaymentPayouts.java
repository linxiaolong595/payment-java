package com.jhzf.pojo;

import lombok.Data;

@Data
public class PaymentPayouts {
  private long payoutsId;
  private long payoutsMoney;
  private String payoutsTime;
  private long payoutsStoreId;
  private String payoutsCard;
  private String payoutsStatus;
  private String payoutsRemarks;
  private String payoutsNumber;
  private long payoutUserId;
  private String userName;
  private String userAccount;
  private double storeRake;
}
