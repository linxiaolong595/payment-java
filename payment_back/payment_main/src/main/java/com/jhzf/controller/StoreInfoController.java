package com.jhzf.controller;

import com.jhzf.service.impl.StoreInfoServiceImpl;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.SelectStoreVo;
import com.jhzf.vo.store.StoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 吴政顺
 * @date 2024/4/22 10:29
 */
@RestController
@CrossOrigin
@RequestMapping("/store")
public class StoreInfoController {
    @Autowired
    private StoreInfoServiceImpl storeInfoService;

    @PostMapping("/search")
    public ResponseDTO selectStore(@RequestBody SelectStoreVo selectStoreVo){
        return storeInfoService.getStoreInfo(selectStoreVo);
    }
    @GetMapping("/test")
    public ResponseDTO test(){
        return ResponseDTO.success();
    }
}
