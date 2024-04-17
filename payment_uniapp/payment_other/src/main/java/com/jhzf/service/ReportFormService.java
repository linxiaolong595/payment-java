package com.jhzf.service;

import com.jhzf.util.ResponseDTO;

/**
 * Description:
 *
 * @Author： 林晓龙
 * @DATE: 2024/4/16 11:03
 */
public interface ReportFormService {
    ResponseDTO selectStoreReportForm(String[] data,int storeId);
    ResponseDTO selectStoreName();
    ResponseDTO selectStoreMoney(String[] data,int storeId);
}
