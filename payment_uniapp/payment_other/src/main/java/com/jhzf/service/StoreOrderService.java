package com.jhzf.service;

import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.StoreOrderVo;

public interface StoreOrderService {
    ResponseDTO getStoreMonthOrder(StoreOrderVo storeOrderVo);
    ResponseDTO getStoreDailyOrder(StoreOrderVo storeOrderVo);
}
