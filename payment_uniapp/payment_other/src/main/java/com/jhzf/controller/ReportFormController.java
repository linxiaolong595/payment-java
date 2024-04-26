package com.jhzf.controller;

import com.jhzf.pojo.PaymentOrder;
import com.jhzf.service.ReportFormService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.reportForm.OrdersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 *
 * @Author： 林晓龙
 * @DATE: 2024/4/16 11:08
 */
@CrossOrigin
@RestController
@RequestMapping("/form")
public class ReportFormController {
    @Autowired
    private ReportFormService reportFormService;
    @GetMapping("/report")
    public ResponseDTO selectStoreReportForm(String[] data,int storeId,int userId){
        return reportFormService.selectStoreReportForm(data,storeId,userId);
    }
    @GetMapping("/storeName")
    public ResponseDTO selectStoreName(int userId) {
        return reportFormService.selectStoreName(userId);
    }
    @GetMapping("/money")
    public ResponseDTO selectStoreMoney(String[] data,int storeId,int userId) {
        return reportFormService.selectStoreMoney(data,storeId,userId);
    }
    @PostMapping("/order")
    public ResponseDTO selectStoreOrder(@RequestBody OrdersVo ordersVo) {
        return reportFormService.selectStoreOrder(ordersVo);
    }
    @PostMapping("/allOrder")
    public ResponseDTO selectOrder(@RequestBody OrdersVo ordersVo) {
        return reportFormService.selectOrder(ordersVo);
    }
}
