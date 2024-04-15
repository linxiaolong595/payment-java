package com.jhzf.controller;

import com.jhzf.service.StoreOrderService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.StoreOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/storeOrder")
public class StoreOrderController {
    @Autowired
    private StoreOrderService storeOrderService;
    @RequestMapping("/getStoreMonthOrder")
    public ResponseDTO getStoreMonthOrder(@RequestBody StoreOrderVo storeOrderVo){
        return storeOrderService.getStoreMonthOrder(storeOrderVo);
    }
    @RequestMapping("/getStoreDailyOrder")
    public ResponseDTO getStoreDailyOrder(@RequestBody StoreOrderVo storeOrderVo){
        return storeOrderService.getStoreDailyOrder(storeOrderVo);
    }
}
