package com.jhzf.mapper;

import com.jhzf.pojo.PaymentOrder;
import com.jhzf.vo.homepage.DataVo;
import com.jhzf.vo.homepage.GetLineVo;
import com.jhzf.vo.homepage.GetStepLineVo;
import com.jhzf.vo.homepage.PaywayVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 *
 * @Author： 林晓龙
 * @DATE: 2024/4/30 9:34
 */
@Mapper
public interface HomepageMapper {

    // 获取店铺数量
    int storeNum();

    // 今日或昨天营业额
    List<PaymentOrder> selectAllStoreMoney(@Param("data") String data);
    // 7/30天折线图
    List<GetLineVo> getLine(@Param("data") DataVo data);

    //支付方式饼图
    List<PaywayVo> getReferer(@Param("data") DataVo data);

    // 交易统计折线图方法
    List<GetStepLineVo> getStepLine(@Param("data") DataVo data);
}
