package com.jhzf.service.impl;

import com.jhzf.mapper.StoreOrderMapper;
import com.jhzf.pojo.PaymentOrder;
import com.jhzf.service.StoreOrderService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.StoreOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class StoreOrderServiceImpl implements StoreOrderService {
    @Autowired
    private StoreOrderMapper storeOrderMapper;

    @Override
    public ResponseDTO getStoreMonthOrder(StoreOrderVo storeOrderVo) {
        LocalDate currenDate = LocalDate.now();
        int year = currenDate.getYear();
        int month = currenDate.getMonthValue();
        int day = currenDate.getDayOfMonth();
        String startTime = null;
        String endTime = null;
        String profitMoney = null;
        String rebackMoney = null;
        String profitOrderCount = null;
        String rebackOrderCount = null;
        List<String> profitMoneyList = new ArrayList<>();
        List<String> rebackMoneyList = new ArrayList<>();
        List<String> profitOrderList = new ArrayList<>();
        List<String> rebackOrderList = new ArrayList<>();
        HashMap<String,List<String>> data = new HashMap<>();
        for(int i = 1;i <= day;i++){
            startTime = year + "-" + month + "-" + i + " " + "00:00:00";
            endTime = year + "-" + month + "-" + i + " " + "23:59:59";
            profitMoney = storeOrderMapper.getStoreProfitMonthOrders(storeOrderVo.getStoreId(),startTime,endTime);
            rebackMoney = storeOrderMapper.getStoreRebackMonthOrder(storeOrderVo.getStoreId(),startTime,endTime);
            profitOrderCount = storeOrderMapper.profitOrderCount(storeOrderVo.getStoreId(),startTime,endTime);
            rebackOrderCount = storeOrderMapper.rebackOrderCount(storeOrderVo.getStoreId(),startTime,endTime);
            profitMoneyList.add(i-1,profitMoney);
            rebackMoneyList.add(i-1,rebackMoney);
            profitOrderList.add(i-1,profitOrderCount);
            rebackOrderList.add(i-1,rebackOrderCount);
        }
        data.put("profitMoney",profitMoneyList);
        data.put("rebackMoney",rebackMoneyList);
        data.put("profitOrderCount",profitOrderList);
        data.put("rebackOrderCount",rebackOrderList);
        return ResponseDTO.success(200,"查询成功",data);
    }

    @Override
    public ResponseDTO getStoreDailyOrder(StoreOrderVo storeOrderVo){
        return null;
    }
}
