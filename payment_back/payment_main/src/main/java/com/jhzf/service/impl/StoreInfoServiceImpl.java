package com.jhzf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jhzf.pojo.PaymentAudit;
import com.jhzf.pojo.PaymentStore;
import com.jhzf.service.StoreInfoService;
import com.jhzf.util.PageResult;
import com.jhzf.util.PageUtils;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.SelectStoreVo;
import com.jhzf.vo.store.StoreReviewVo;
import com.jhzf.vo.store.StoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import com.jhzf.mapper.BackendStoreMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public ResponseDTO getStoreInfo(SelectStoreVo selectStoreVo) {
        PageHelper.startPage(selectStoreVo.getPageNum(),selectStoreVo.getPageSize());
        List<PaymentStore> storeVos = backendStoreMapper.selectStoreInfo(selectStoreVo);
        System.out.println("storeVos"+storeVos);
        if (storeVos != null && !storeVos.isEmpty()){
            PageInfo<PaymentStore> pageInfo = new PageInfo<>(storeVos);
            PageResult pageResult = PageUtils.getPageResult(pageInfo);
            System.out.println("pageResult"+pageResult);
            return ResponseDTO.success(200,"success",pageResult);
        }else {
            return ResponseDTO.error(201,"查询失败,未找到商家信息");
        }
    }

    //获取商家认证信息
    @Override
    public ResponseDTO getInformation(int storeId) {
        PaymentStore p = backendStoreMapper.getInformationMapper(storeId);
        if (p != null){
            return ResponseDTO.success(200,"查询认证信息成功",p);
        }else {
            return ResponseDTO.error(201,"查询认证信息失败");
        }
    }

    //查询审核表的店铺信息
    @Override
    public ResponseDTO getReviewStore(StoreReviewVo vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<PaymentAudit> res = backendStoreMapper.getReviewStoreMapper(vo);
        if (res != null && !res.isEmpty()){
            PageInfo<PaymentAudit> pageInfo = new PageInfo<>(res);
            PageResult pageResult = PageUtils.getPageResult(pageInfo);
            return ResponseDTO.success(200,"success",pageResult);
        }else {
            return ResponseDTO.error(201,"查询失败,未找到待审核商家信息");
        }
    }
    //查询审核店铺信息
    @Override
    public ResponseDTO getReviewStoreInfo(int auditId) {
        List<PaymentAudit> paymentAudit = backendStoreMapper.getReviewStoreInfoMapper(auditId);
        if (paymentAudit != null){
            return ResponseDTO.success(200,"success",paymentAudit);
        }else {
            return ResponseDTO.success(201,"error");
        }
    }
}
