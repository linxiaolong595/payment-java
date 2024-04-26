package com.jhzf.controller;

import com.jhzf.service.AuditService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.audit.AuditVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @PostMapping("/addMerchant")
    public ResponseDTO addMerchant(@RequestBody AuditVo auditVo) {
        ResponseDTO responseDTO = auditService.addMerchant(auditVo);
        return responseDTO;
    }
}
