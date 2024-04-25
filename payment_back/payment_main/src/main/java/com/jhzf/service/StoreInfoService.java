package com.jhzf.service;

import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.SelectStoreVo;
import com.jhzf.vo.store.StoreVo;

/**
 * @author 吴政顺
 * @date 2024/4/22 10:44
 */
public interface StoreInfoService {
    ResponseDTO getStoreInfo(SelectStoreVo selectStoreVo); //查询商家信息
}
