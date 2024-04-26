package com.jhzf.service;

import com.jhzf.util.ResponseDTO;

public interface ApplicationService {
    ResponseDTO selectAllApplication(String userId);
    ResponseDTO cancellationApplication(String auditId);
}
