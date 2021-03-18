package com.github.limingliang.service.impl;

import com.github.limingliang.entity.Page;
import com.github.limingliang.entity.QueryRequest;
import com.github.limingliang.entity.Trade;
import com.github.limingliang.service.RedisService;
import com.github.limingliang.service.TradeService;
import com.github.limingliang.util.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private RedisService redisService;

    @Override
    public boolean save(Trade trade) {
        redisService.hset(CacheConstants.TRADE_CACHE_PREFIX,trade.getTradeId()+"",trade);
        Trade trade1 = (Trade)redisService.hget(CacheConstants.TRADE_CACHE_PREFIX,trade.getTradeId()+"");
        return true;
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
    public List<Object> getAll() {
        return redisService.hvalues(CacheConstants.TRADE_CACHE_PREFIX);
    }

    @Override
    public Page<Trade> getByPage(QueryRequest request) {
        return null;
    }
}
