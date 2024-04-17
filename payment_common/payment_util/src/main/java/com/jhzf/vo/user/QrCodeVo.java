package com.jhzf.vo.user;


import lombok.Data;
import java.io.Serializable;

@Data
public class QrCodeVo implements Serializable {
    //商铺的id
    private long storeId;
    //商品的标题
    private String subject;
    //订单描述
    private String body;
}
