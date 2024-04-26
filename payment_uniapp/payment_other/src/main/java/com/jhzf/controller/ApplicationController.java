package com.jhzf.controller;


import com.jhzf.service.ApplicationService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.user.ModifyPwdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @GetMapping("/selectAllApplication")
    public ResponseDTO selectAllApplication(String userId) {
        return applicationService.selectAllApplication(userId);
    }

    @GetMapping("/cancellationApplication")
    public ResponseDTO cancellationApplication(String auditId) {
        return applicationService.cancellationApplication(auditId);
    }
}
