package com.jhzf.pojo;

import lombok.Data;

@Data
public class PaymentOrder {

  private long orderId;
  private String orderNumber;
  private String orderCallbackNumber;
  private double orderMoney;
  private long orderStoreId;
  private String orderCreatetime;
  private String orderStatus;
  private String orderReback;
  private String orderPayway;
}
