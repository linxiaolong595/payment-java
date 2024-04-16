package com.jhzf.service.impl;

import com.jhzf.mapper.StoreMapper;
import com.jhzf.service.StoreService;
import com.jhzf.util.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreMapper storeMapper;
    @Override
    public ResponseDTO getStore(int userId) {
        return null;
    }
}
