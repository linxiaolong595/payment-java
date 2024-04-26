package com.jhzf.service.impl;

import com.jhzf.mapper.ApplicationMapper;
import com.jhzf.mapper.StoreMapper;
import com.jhzf.pojo.PaymentAudit;
import com.jhzf.service.ApplicationService;
import com.jhzf.util.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    ApplicationMapper applicationMapper;
    @Override
    public ResponseDTO selectAllApplication(String userId) {
        ResponseDTO dto = null;
        List<PaymentAudit> paymentAudits = applicationMapper.selectAllApplication(userId);
        if (paymentAudits.size() > 0){
            dto = new ResponseDTO(200,"获取所有申请进度成功",paymentAudits);
        }else {
            dto = ResponseDTO.error(201,"获取失败");
        }
        return dto;
    }

    @Override
    public ResponseDTO cancellationApplication(String auditId) {
        ResponseDTO dto = null;
        int res = applicationMapper.cancellationApplication(auditId);
        if (res > 0){
            dto = new ResponseDTO(200,"取消成功",null);
        }else {
            dto = new ResponseDTO(201,"取消失败",null);
        }
        return dto;
    }
}
