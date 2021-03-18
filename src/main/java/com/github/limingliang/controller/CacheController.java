package com.github.limingliang.controller;

import com.alibaba.fastjson.JSON;
import com.github.limingliang.entity.Trade;
import com.github.limingliang.service.RedisService;
import com.github.limingliang.service.TradeService;
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
    private TradeService tradeService;

    @RequestMapping("/addTrade")
    public Boolean put(@RequestBody Trade trade) {
        logger.info("更新trade:{}",JSON.toJSONString(trade));
        tradeService.save(trade);
        logger.info("更新成功");
        return true;
    }

    @RequestMapping("/getAllTrades")
    public List<Object> getAllTrades(){
        return tradeService.getAll();
    }

}
