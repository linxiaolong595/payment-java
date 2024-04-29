package com.jhzf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jhzf.mapper.WithdrawalMapper;
import com.jhzf.pojo.PaymentPayouts;
import com.jhzf.service.WithdrawalService;
import com.jhzf.util.PageResult;
import com.jhzf.util.PageUtils;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.order.WithdrawalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 吴政顺
 * @date 2024/4/26 17:09
 */
@Service
public class WithdrawalServiceImpl implements WithdrawalService {
    @Autowired
    private WithdrawalMapper withdrawalMapper;

    //查询提现审核订单
    @Override
    public ResponseDTO WithdrawalOlder(WithdrawalVo vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<PaymentPayouts> res = withdrawalMapper.WithdrawalOlder(vo);
        System.out.println("res"+res);
        if (res != null && !res.isEmpty()){
            PageInfo<PaymentPayouts> pageInfo = new PageInfo<>(res);
            PageResult pageResult = PageUtils.getPageResult(pageInfo);
            return ResponseDTO.success(200,"success",pageResult);
        }else {
            return ResponseDTO.error(201,"查询失败,未找到提现审核订单");
        }
    }
    //提现订单详情
    @Override
    public ResponseDTO WithdrawalDetails(int payoutsId) {
       PaymentPayouts res = withdrawalMapper.WithdrawalDetails(payoutsId);
        if (res != null){
            return ResponseDTO.success(200,"success",res);
        }else {
            return ResponseDTO.error(201,"查询失败,未找到提现订单详情");
        }
    }
}
