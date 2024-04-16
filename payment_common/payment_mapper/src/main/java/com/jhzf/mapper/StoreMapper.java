package com.jhzf.mapper;

import com.jhzf.pojo.PaymentStore;
import com.jhzf.pojo.PaymentUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreMapper {

    List<PaymentUser> getUserStore(@Param("userId") int userId);
}
