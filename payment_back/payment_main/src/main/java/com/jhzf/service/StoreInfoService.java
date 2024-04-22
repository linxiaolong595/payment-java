package com.jhzf.service;

import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.StoreVo;

/**
 * @author 吴政顺
 * @date 2024/4/22 10:44
 */
public interface StoreInfoService {
    ResponseDTO getStoreInfo(int pageNum, int pageSize,StoreVo storeVo); //查询商家信息

}
