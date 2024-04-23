package com.jhzf.vo.reportForm;

import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @Author： 林晓龙
 * @DATE: 2024/4/18 10:06
 */
@Data
public class OrdersVo {
    private int userId;
    private int storeId;
    private List<String> data;
}
