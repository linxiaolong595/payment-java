package com.jhzf.controller;

import com.jhzf.service.impl.StoreInfoServiceImpl;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.SelectStoreVo;
import com.jhzf.vo.store.StoreReviewVo;
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
    //按条件查询店铺
    @PostMapping("/search")
    public ResponseDTO selectStore(@RequestBody SelectStoreVo selectStoreVo){
        return storeInfoService.getStoreInfo(selectStoreVo);
    }

    //查询商家认证信息
    @GetMapping("/information")
    public ResponseDTO information(@RequestParam int storeId){
        return storeInfoService.getInformation(storeId);
    }


    //查询审核表的店铺信息
    @PostMapping("/PendingInformation")
    public ResponseDTO PendingInformation(@RequestBody StoreReviewVo vo){
        return storeInfoService.getReviewStore(vo);
    }
}
