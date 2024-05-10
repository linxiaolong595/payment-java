package com.jhzf.controller;

import com.jhzf.service.HomepageService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.homepage.DataVo;
import com.jhzf.vo.homepage.PaywayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 *
 * @Author： 林晓龙
 * @DATE: 2024/4/30 9:34
 */
@RestController
@CrossOrigin
@RequestMapping("/homepage")
public class HomepageController {
    @Autowired
    private HomepageService homepageService;
    @PostMapping("/storeNum")
    public ResponseDTO storeNum() {
        return homepageService.storeNum();
    }
    @GetMapping("/allMoney")
    public ResponseDTO selectAllStoreMoney(String data){
        return homepageService.selectAllStoreMoney(data);
    }
    @PostMapping("/getLine")
    public ResponseDTO getLine(@RequestBody DataVo data){
        return homepageService.getLine( data);
    }
    @PostMapping("/getReferer")
    public ResponseDTO getReferer(@RequestBody DataVo data){
        return homepageService.getReferer( data);
    }
    @PostMapping("/getStepLine")
    public ResponseDTO getStepLine(@RequestBody DataVo data){
        return homepageService.getStepLine( data);
    }
}
