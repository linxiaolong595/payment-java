package com.jhzf.pojo;

import lombok.Data;

@Data
public class PaymentStore {

  private long storeId;
  private String storeName;
  private String storeHeadImage;
  private java.sql.Timestamp sotreCreatetime;
  private String storeNumber;
  private String storeIdentifyImage;
  private String storeIdentifyCardFront;
  private String storeIdentifyCardBack;
  private double storeRake;
  private long storePrimaryAccountId;
  private long storeSubsidiaryAccountId;
  private long storeIsStatus;
  private long storeIsDelete;
}
