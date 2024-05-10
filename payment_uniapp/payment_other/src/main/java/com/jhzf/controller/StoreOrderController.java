package com.jhzf.controller;

import com.jhzf.service.StoreOrderService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.StoreOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/storeOrder")
public class StoreOrderController {
    @Autowired
    private StoreOrderService storeOrderService;
    @PostMapping("/getStoreMonthOrder")
    public ResponseDTO getStoreMonthOrder(@RequestBody StoreOrderVo storeOrderVo){
        return storeOrderService.getStoreMonthOrder(storeOrderVo);
    }
    @PostMapping("/getStoreDailyOrder")
    public ResponseDTO getStoreDailyOrder(@RequestBody StoreOrderVo storeOrderVo){
        return storeOrderService.getStoreDailyOrder(storeOrderVo);
    }
    @PostMapping("/getOrderDetail")
    public ResponseDTO getOrderDetail(@RequestBody StoreOrderVo storeOrderVo){
        return storeOrderService.getOrderDetail(storeOrderVo);
    }
    @GetMapping("/getAbnormalOrder")
    public ResponseDTO getAbnormalOrder(int userId){
        return storeOrderService.getAbnormalOrder(userId);
    }
}
