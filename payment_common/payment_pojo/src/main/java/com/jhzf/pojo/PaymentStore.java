package com.jhzf.pojo;

import lombok.Data;

@Data
public class PaymentStore {

  private long storeId;
  private String storeName;
  private String storeHeadImage;
  private String sotreCreatetime;
  private String storeNumber;
  private String storeIdentifyImage;
  private String storeIdentifyCardFront;
  private String storeIdentifyCardBack;
  private double storeRake;
  private long storePrimaryAccountId;
  private long storeSubsidiaryAccountId;
  private long storeIsStatus;
  private long storeIsDelete;
  private String storeIdNum;
  private double storeUsableMoney;
  private double storeAuditMoney;
  private String userAccount; // 用户表中的手机号字段
  private String userName;
}
