package com.jhzf.controller;

import com.jhzf.service.ReportFormService;
import com.jhzf.util.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseDTO selectStoreReportForm(String storeId){
        return reportFormService.selectStoreReportForm(storeId);
    }
}
