package com.jhzf.service.impl;

import com.jhzf.mapper.AuditMapper;
import com.jhzf.service.AuditService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.audit.AuditVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {
    @Autowired
    private AuditMapper auditMapper;
    @Override
    public ResponseDTO addMerchant(AuditVo auditVo) {
        int i = auditMapper.addAudit(auditVo);
        if(i>0){
            return ResponseDTO.success(200,"申请入驻已发送",i);
        }else {
            return ResponseDTO.error();
        }

    }
}
