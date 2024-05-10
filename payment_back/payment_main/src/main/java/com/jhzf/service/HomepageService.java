package com.jhzf.service;

import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.homepage.DataVo;
import com.jhzf.vo.homepage.PaywayVo;

import java.util.List;

/**
 * Description:
 *
 * @Author： 林晓龙
 * @DATE: 2024/4/30 9:33
 */
public interface HomepageService {
    // 获取店铺数量
    ResponseDTO storeNum();
    ResponseDTO selectAllStoreMoney(String data);
    ResponseDTO getLine(DataVo data);
    ResponseDTO getReferer(DataVo data);
    ResponseDTO getStepLine(DataVo data);
}
