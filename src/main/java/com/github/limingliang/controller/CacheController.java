package com.github.limingliang.controller;

import com.alibaba.fastjson.JSON;
import com.github.limingliang.entity.Trade;
import com.github.limingliang.service.RedisService;
import com.github.limingliang.util.CacheConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 缓存controller类
 */
@RestController
@Validated
public class CacheController {

    private static final Logger logger = LoggerFactory.getLogger(CacheController.class.getName());

    @Autowired
    private RedisService redisService;

    @RequestMapping("/addTrade")
    public Trade put(@RequestBody Trade trade) {
        redisService.hset(CacheConstants.TRADE_CACHE_PREFIX,trade.getTradeId()+"",trade);
        Trade trade1 = (Trade)redisService.hget(CacheConstants.TRADE_CACHE_PREFIX,trade.getTradeId()+"");
        logger.info("反序列化后:"+JSON.toJSONString(trade1));
        return trade1;
    }

    @RequestMapping("/getAllTrades")
    public List<Object> getAllTrades(){
        return redisService.hvalues(CacheConstants.TRADE_CACHE_PREFIX);
    }

}
