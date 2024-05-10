package com.jhzf.mapper;

import com.jhzf.pojo.PaymentAudit;
import com.jhzf.pojo.PaymentStore;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.audit.AuditCommitVo;
import com.jhzf.vo.store.SelectStoreVo;
import com.jhzf.vo.store.StoreReviewVo;
import com.jhzf.vo.store.StoreVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 吴政顺
 * @date 2024/4/22 11:05
 */
@Mapper
public interface BackendStoreMapper {
    List<PaymentStore> selectStoreInfo(SelectStoreVo selectStoreVo); //按信息查询商家

    PaymentStore getInformationMapper(int storeId); //获取商家认证信息

    List<PaymentAudit> getReviewStoreMapper(StoreReviewVo vo);//查询审核表的店铺信息

    PaymentAudit getReviewStoreInfoMapper(int auditId); //获取审核店铺信息

    int getReviewAuditingMapper(AuditCommitVo vo); //提交店铺审核

    int insetStoreMapper(PaymentAudit payment); //插入审核店铺到店铺表

    int UpdateStore(@Param("storeId") int storeId, @Param("code") String code, @Param("storeNum") String storeNum); //插入二维码和商户号
}
