package com.jhzf.controller;

import com.jhzf.service.StoreService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.StoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@CrossOrigin
public class StoreController {
    @Autowired
    private StoreService storeService;
    @PostMapping("/getUserStore")
    public ResponseDTO getUserStore(@RequestBody StoreVo storeVo){
        return storeService.getStore(storeVo.getUserId());
    }
}
