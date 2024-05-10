package com.jhzf.controller;

import com.beust.jcommander.Parameters;
import com.jhzf.service.impl.WithdrawalServiceImpl;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.order.AuditingVo;
import com.jhzf.vo.order.WithdrawalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 吴政顺
 * @date 2024/4/26 16:54
 */
@RestController
@CrossOrigin
@RequestMapping("/withdrawal")
public class WithdrawalController {
    @Autowired
    private WithdrawalServiceImpl withdrawalService;

    //查询提现审核订单
    @PostMapping("/order")
    public ResponseDTO withdrawalOrder(@RequestBody WithdrawalVo vo){
        System.out.println("vo11111"+vo);
        return withdrawalService.WithdrawalOlder(vo);
    }

    //提现订单审核
    @PostMapping("/auditing")
    public ResponseDTO withdrawalAuditing(@RequestBody AuditingVo vo){
        return withdrawalService.withdrawalAuditing(vo);
    }

    //提现订单详情
    @GetMapping("/details")
    public ResponseDTO withdrawalDetails(@RequestParam int payoutsId){
        return withdrawalService.WithdrawalDetails(payoutsId);
    }
}
