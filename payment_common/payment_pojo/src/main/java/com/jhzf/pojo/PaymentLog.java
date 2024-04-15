package com.jhzf.pojo;

import lombok.Data;

@Data
public class PaymentLog {

  private long logId;
  private String logName;
  private String logUrl;
  private String logMsg;
  private long logAdminId;
  private String logIp;
  private String logIsDelete;
  private java.sql.Timestamp logCreateTime;
}
