package com.github.limingliang.service.impl;

import com.github.limingliang.entity.Page;
import com.github.limingliang.entity.QueryRequest;
import com.github.limingliang.entity.Trade;
import com.github.limingliang.service.TradeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {
    @Override
    public boolean save(Trade trade) {
        return false;
    }

    @Override
    public boolean batchSave(List<Trade> trade) {
        return false;
    }

    @Override
    public Trade getById(Long tradeId) {
        return null;
    }

    @Override
    public Long getSize() {
        return null;
    }

    @Override
    public List<Trade> getAll() {
        return null;
    }

    @Override
    public Page<Trade> getByPage(QueryRequest request) {
        return null;
    }
}
