package com.github.limingliang.service.impl;

import com.github.limingliang.entity.Page;
import com.github.limingliang.entity.QueryRequest;
import com.github.limingliang.entity.Trade;
import com.github.limingliang.service.RedisService;
import com.github.limingliang.service.TradeService;
import com.github.limingliang.utils.CacheConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private RedisService redisService;

    @Override
    public boolean save(Trade trade) {
        redisService.setPage(CacheConstants.TRADE_CACHE_PREFIX,trade.getTradeId()+"",(double)trade.getTradeId(),trade);
        return true;
    }

    @Override
    public boolean batchSave(List<Trade> trades) {
        trades.stream().forEach(trade -> {
            redisService.setPage(CacheConstants.TRADE_CACHE_PREFIX,trade.getTradeId()+"",(double)trade.getTradeId(),trade);
        });
        return false;
    }

    @Override
    public boolean delete(String tradeId) {
        return redisService.delPage(CacheConstants.TRADE_CACHE_PREFIX,tradeId);
    }

    @Override
    public boolean deleteTable() {
        return redisService.delPageTable(CacheConstants.TRADE_CACHE_PREFIX);
    }

    @Override
    public Trade getById(String tradeId) {
        return (Trade) redisService.hget(CacheConstants.TRADE_CACHE_PREFIX,tradeId);
    }

    @Override
    public Integer getSize() {
        return redisService.getSize(CacheConstants.TRADE_CACHE_PREFIX);
    }

    @Override
    public List<Object> getAll() {
        Set<Object> keys =  redisService.getPageAll(CacheConstants.TRADE_CACHE_PREFIX);
        List<Object> result = redisService.hMultiGet(CacheConstants.TRADE_CACHE_PREFIX,keys);
        return result;
    }

    @Override
    public Page<Trade> getByPage(QueryRequest request) {

        Set<Object> keys =  redisService.getPage(CacheConstants.TRADE_CACHE_PREFIX,request.getPageNum(),request.getPageSize());
        List<Object> result = redisService.hMultiGet(CacheConstants.TRADE_CACHE_PREFIX,keys);
        List<Trade> tradeRecords = result.stream().map(o -> (Trade)o).collect(Collectors.toList());
        Page<Trade> page = new Page<>();
        page.setCurrent(request.getPageNum());
        page.setSize(request.getPageSize());
        page.setTotal(redisService.getSize(CacheConstants.TRADE_CACHE_PREFIX));
        page.setRecords(tradeRecords);
        return page;
    }
}
