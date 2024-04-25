package com.jhzf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jhzf.mapper.StoreOrderMapper;
import com.jhzf.pojo.PaymentOrder;
import com.jhzf.service.OrderService;
import com.jhzf.util.PageResult;
import com.jhzf.util.PageUtils;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.store.StoreOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author 蔡嘉豪
 * @date 2024/4/24 15:42
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private StoreOrderMapper storeOrderMapper;
    @Override
    public ResponseDTO getStoreOrderBack(StoreOrderVo storeOrderVo) {
        if(storeOrderVo.getOrderCreatetime().equals("1")){
            LocalDate todayDate = LocalDate.now();
            LocalDateTime s = todayDate.atStartOfDay();
            LocalDate tomorrow = todayDate.plusDays(1);
            LocalDateTime e = tomorrow.atStartOfDay();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String end = e.format(formatter);
            String start = s.format(formatter);
            storeOrderVo.setStartTime(start);
            storeOrderVo.setEndTime(end);
        }if(storeOrderVo.getOrderCreatetime().equals("2")){
            LocalDate yesterday = LocalDate.now().minusDays(1);
            LocalDateTime startOfYesterday = yesterday.atStartOfDay();
            LocalDate todayDate = LocalDate.now();
            LocalDateTime startOfToday = todayDate.atStartOfDay();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String start = startOfYesterday.format(formatter);
            String end = startOfToday.format(formatter);

            storeOrderVo.setStartTime(start);
            storeOrderVo.setEndTime(end);
        }if(storeOrderVo.getOrderCreatetime().equals("3")){
            LocalDate todayDate = LocalDate.now();
            LocalDate sevenDaysAgo = todayDate.minusDays(6);
            LocalDateTime startOfSevenDaysAgo = sevenDaysAgo.atStartOfDay();
            LocalDate tomorrow = todayDate.plusDays(1);
            LocalDateTime startOfTomorrow = tomorrow.atStartOfDay();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String start = startOfSevenDaysAgo.format(formatter);
            String end = startOfTomorrow.format(formatter);

            storeOrderVo.setStartTime(start);
            storeOrderVo.setEndTime(end);

        }if(storeOrderVo.getOrderCreatetime().equals("4")){
            LocalDate todayDate = LocalDate.now();
            LocalDate thirtyDaysAgo = todayDate.minusDays(29);
            LocalDateTime startOfThirtyDaysAgo = thirtyDaysAgo.atStartOfDay();
            LocalDate tomorrow = todayDate.plusDays(1);
            LocalDateTime startOfTomorrow = tomorrow.atStartOfDay();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String start = startOfThirtyDaysAgo.format(formatter);
            String end = startOfTomorrow.format(formatter);

            storeOrderVo.setStartTime(start);
            storeOrderVo.setEndTime(end);

        }
        System.out.println("vo的信息"+storeOrderVo);
        PageHelper.startPage(storeOrderVo.getPageNum(),7);
        List<PaymentOrder> storeOrderBack = storeOrderMapper.getStoreOrderBack(storeOrderVo);
        PageInfo pageInfo = new PageInfo(storeOrderBack);
        PageResult pageResult = PageUtils.getPageResult(pageInfo);
        if(pageResult.getList().size()>0){
            return ResponseDTO.success(200,"查询成功",pageResult);
        }else {
            return ResponseDTO.error(201,"查询失败,无该条订单信息");
        }

    }
}
