package com.jhzf.service;

import com.jhzf.vo.user.QrCodeVo;

public interface QrCodeService {
    String createStoreCode(QrCodeVo qrCodeVo);

}
