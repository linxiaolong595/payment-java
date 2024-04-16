package com.jhzf.controller;


import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.user.ModifyPwdVo;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/application")
public class ApplicationController {
    @PostMapping("/selectAllApplication")
    public ResponseDTO modifyPwd(@RequestBody ModifyPwdVo vo) {
        return null;
    }
}
