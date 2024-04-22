package com.jhzf.mapper;

import com.jhzf.pojo.PaymentStore;
import com.jhzf.vo.store.StoreVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 吴政顺
 * @date 2024/4/22 11:05
 */
@Mapper
public interface BackendStoreMapper {
    List<StoreVo> selectStoreInfo();
}
