package com.jhzf.service;

import com.alibaba.fastjson2.JSONObject;
import com.jhzf.vo.user.QrCodeVo;

import java.io.UnsupportedEncodingException;

public interface QrCodeService {
    String createStoreCode(QrCodeVo qrCodeVo);
}
