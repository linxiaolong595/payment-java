package com.jhzf.controller;

import com.jhzf.service.OrderService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.StoreOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 蔡嘉豪
 * @date 2024/4/24 15:39
 */


@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/search")
    public ResponseDTO selectStore(@RequestBody StoreOrderVo storeOrderVo){

        return orderService.getStoreOrderBack(storeOrderVo);
    }
}
