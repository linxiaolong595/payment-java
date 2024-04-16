package com.jhzf.service;

import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.StoreOrderVo;
import org.springframework.web.bind.annotation.RequestBody;

public interface StoreOrderService {
    ResponseDTO getStoreMonthOrder(StoreOrderVo storeOrderVo);
    ResponseDTO getStoreDailyOrder(StoreOrderVo storeOrderVo);
}
