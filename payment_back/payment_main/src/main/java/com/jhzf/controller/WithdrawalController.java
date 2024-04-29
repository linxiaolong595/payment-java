package com.jhzf.controller;

import com.jhzf.util.ResponseDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 吴政顺
 * @date 2024/4/26 16:54
 */
@RestController
@CrossOrigin
@RequestMapping("/withdrawal")
public class WithdrawalController {
    //查询提现审核订单
    @PostMapping("/order")
    private ResponseDTO withdrawalOrder(){
        return null;
    }
}
