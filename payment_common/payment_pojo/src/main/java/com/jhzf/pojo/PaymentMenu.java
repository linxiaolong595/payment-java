package com.jhzf.pojo;

import lombok.Data;

@Data
public class PaymentMenu {

  private long menuId;
  private String menuName;
  private long menuParent;
}
