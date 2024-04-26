package com.jhzf.vo.user;

import lombok.Data;

@Data
public class PayOutVo {
    private String payOutCard;
    private double payOutMoney;
    private int storeId;
    private double choucheng;
}
