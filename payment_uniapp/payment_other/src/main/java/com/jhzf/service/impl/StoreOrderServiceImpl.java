package com.jhzf.service.impl;

import com.jhzf.mapper.StoreOrderMapper;
import com.jhzf.pojo.PaymentOrder;
import com.jhzf.service.StoreOrderService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.StoreOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreOrderServiceImpl implements StoreOrderService {
    @Autowired
    private StoreOrderMapper storeOrderMapper;

    @Override
    public ResponseDTO getStoreMonthOrder(StoreOrderVo storeOrderVo) {
        List<PaymentOrder> orderList = storeOrderMapper.getStoreMonthOrder(storeOrderVo.getStoreId(),storeOrderVo.getStartTime(),storeOrderVo.getEndTime());
        if(orderList.size() != 0){
            return ResponseDTO.success(200,"获取成功",orderList);
        }else{
            return ResponseDTO.success(200,"暂无订单",null);
        }
    }

    @Override
    public ResponseDTO getStoreDailyOrder(StoreOrderVo storeOrderVo){
        return null;
    }
}
