package com.jhzf.pojo;

import lombok.Data;

@Data
public class PaymentPayouts {

  private long payoutsId;
  private long payoutsMoney;
  private java.sql.Timestamp payoutsTime;
  private long payoutsStoreId;
  private String payoutsCard;
  private String payoutsStatus;
}
