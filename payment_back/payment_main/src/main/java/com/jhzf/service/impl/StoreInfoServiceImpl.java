package com.jhzf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jhzf.pojo.PaymentAudit;
import com.jhzf.pojo.PaymentStore;
import com.jhzf.service.StoreInfoService;
import com.jhzf.util.PageResult;
import com.jhzf.util.PageUtils;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.audit.AuditCommitVo;
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
    //提交店铺审核通过
    @Override
    public ResponseDTO getReviewAuditing(AuditCommitVo vo) {
        int res = backendStoreMapper.getReviewAuditingMapper(vo);
        if (res > 0){
            //获取审核表信息
            PaymentAudit payment = backendStoreMapper.getReviewStoreInfoMapper(vo.getAuditId());
            if (payment != null){
                //将审核表信息插入商店表
                int res1 = backendStoreMapper.insetStoreMapper(payment);
                if (res1 > 0){
                    int storeId = payment.getStoreId();
                    //插入商户号和二维码
                    int res2 = backendStoreMapper.UpdateStore(storeId,"123","333");
                    if (res2 > 0){
                        ResponseDTO.success(200,"更新二维码和商户号成功");
                    }else {
                        ResponseDTO.error(201,"更新二维码和商户号失败");
                    }
                }else {
                    return ResponseDTO.success(201,"插入失败");
                }
            }
            return ResponseDTO.success(200,"success");
        }else {
            return ResponseDTO.success(201,"更新失败");
        }
    }
    //提交店铺审核没通过
    @Override
    public ResponseDTO getNoReviewAuditing(AuditCommitVo vo) {
        int res = backendStoreMapper.getReviewAuditingMapper(vo);
        if (res > 0){
            return ResponseDTO.success(200,"铺审审核状态更新成功");
        }else {
            return ResponseDTO.success(201,"铺审审核状态更新失败");
        }
    }

    //查询审核店铺信息
    @Override
    public ResponseDTO getReviewStoreInfo(int auditId) {
        PaymentAudit res = backendStoreMapper.getReviewStoreInfoMapper(auditId);
        if (res != null){
            return ResponseDTO.success(200,"success",res);
        }else {
            return ResponseDTO.success(201,"error");
        }
    }
}
