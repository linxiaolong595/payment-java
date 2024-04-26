package com.jhzf.service;

import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.audit.AuditVo;

public interface AuditService {
    ResponseDTO addMerchant(AuditVo auditVo);
}
