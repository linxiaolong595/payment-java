package com.jhzf.mapper;

import com.jhzf.vo.audit.AuditVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditMapper {
    int addAudit(AuditVo auditVo);
}
