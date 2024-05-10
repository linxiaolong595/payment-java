package com.jhzf.service.impl;

import com.jhzf.mapper.HomepageMapper;
import com.jhzf.pojo.PaymentOrder;
import com.jhzf.service.HomepageService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.homepage.DataVo;
import com.jhzf.vo.homepage.GetLineVo;
import com.jhzf.vo.homepage.GetStepLineVo;
import com.jhzf.vo.homepage.PaywayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author： 林晓龙
 * @DATE: 2024/4/30 9:33
 */
@Service
public class HomepageServiceImpl implements HomepageService {

    @Autowired
    private HomepageMapper homepageMapper;
    @Override
    public ResponseDTO storeNum() {
        int i = homepageMapper.storeNum();
        return ResponseDTO.success(i);
    }

    @Override
    public ResponseDTO selectAllStoreMoney(String data) {
        List<PaymentOrder> orders = homepageMapper.selectAllStoreMoney(data);
        int count=0;
        double sum=0;
        double refundSum=0;
        int refundCount=0;

        for (PaymentOrder order : orders) {
            if (order.getOrderReback()==0){
                count++;
                sum += order.getOrderMoney();
            } else {
                refundCount++;
                refundSum += order.getOrderMoney();
            }
        }
        Map<String, Object> money = new HashMap<>();
        money.put("count",count);
        money.put("sum",sum);
        money.put("refundSum",refundSum);
        money.put("refundCount",refundCount);

        return ResponseDTO.success(200,"success",money);
    }

    @Override
    public ResponseDTO getLine(DataVo data) {
        List<GetLineVo> line = homepageMapper.getLine(data);
        ArrayList<String[]> list = new ArrayList<>();
        for (GetLineVo vo : line) {
            String data1 = vo.getData();
            String money = String.valueOf(vo.getMoney());
            String[] s = new  String[]{data1, money};
            list.add(s);
        }
        return ResponseDTO.success(200,"success",list);
    }

    @Override
    public ResponseDTO getReferer(DataVo data) {
        List<PaywayVo> referer = homepageMapper.getReferer(data);
        return ResponseDTO.success(200,"success",referer);
    }

    @Override
    public ResponseDTO getStepLine(DataVo data) {
        List<GetStepLineVo> stepLine = homepageMapper.getStepLine(data);
        return ResponseDTO.success(200,"success",stepLine);
    }
}
