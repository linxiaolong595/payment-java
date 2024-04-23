package com.jhzf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jhzf.service.StoreInfoService;
import com.jhzf.util.PageResult;
import com.jhzf.util.PageUtils;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.StoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import com.jhzf.mapper.BackendStoreMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 吴政顺
 * @date 2024/4/22 10:41
 */
@Service
public class StoreInfoServiceImpl implements StoreInfoService {
    @Autowired
    private  BackendStoreMapper backendStoreMapper;
    //按信息查询商家
    @Override
    public ResponseDTO getStoreInfo(int pageNum, int pageSize,StoreVo storeVo) {
        PageHelper.startPage(pageNum,pageSize);
        List<StoreVo> storeVos = backendStoreMapper.selectStoreInfo();
        if (storeVos != null && !storeVos.isEmpty()){
            PageInfo pageInfo = new PageInfo(storeVos);
            PageResult pageResult = PageUtils.getPageResult(pageInfo);
            return ResponseDTO.success(200,"success",pageResult);
        }else {
            return ResponseDTO.error(-1,"查询失败,未找到商家信息");
        }
    }
}
