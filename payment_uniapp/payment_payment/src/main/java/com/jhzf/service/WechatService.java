package com.jhzf.service;

import com.jhzf.vo.payment.WXPayOrderVo;

public interface WechatService {
    void insertOrder(WXPayOrderVo vo);
}
