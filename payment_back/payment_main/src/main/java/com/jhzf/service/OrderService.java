package com.jhzf.service;

import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.StoreOrderVo;

/**
 * @author 蔡嘉豪
 * @date 2024/4/24 15:40
 */

public interface OrderService {
    //后台订单组合查询
    ResponseDTO getStoreOrderBack(StoreOrderVo storeOrderVo);
}
