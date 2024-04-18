package com.jhzf.controller;

import com.jhzf.service.ReportFormService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.reportForm.OrdersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseDTO selectStoreReportForm(String[] data,int storeId){
        return reportFormService.selectStoreReportForm(data,storeId);
    }
    @GetMapping("/storeName")
    public ResponseDTO selectStoreName() {
        return reportFormService.selectStoreName();
    }
    @GetMapping("/money")
    public ResponseDTO selectStoreMoney(String[] data,int storeId) {
        return reportFormService.selectStoreMoney(data,storeId);
    }
    @PostMapping("/order")
    public ResponseDTO selectStoreOrder(@RequestBody OrdersVo ordersVo) {
        System.out.println(ordersVo);
        return reportFormService.selectStoreOrder(ordersVo);
    }
}