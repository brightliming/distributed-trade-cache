package com.github.limingliang.controller;

import com.alibaba.fastjson.JSON;
import com.github.limingliang.entity.QueryRequest;
import com.github.limingliang.entity.Trade;
import com.github.limingliang.service.TradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 缓存controller类
 */
@RestController
@Api(tags = "交易表缓存模块")
public class CacheController {

    private static final Logger logger = LoggerFactory.getLogger(CacheController.class.getName());

    @Autowired
    private TradeService tradeService;

    /**
     * 更新交易表
     * @param trade
     * @return
     */
    @PutMapping("/addTrade")
    @ApiOperation(value = "更新交易表")
    public Object addTrade(@RequestBody @Validated Trade trade, BindingResult bindingResult) {
        logger.info("更新trade:{}",JSON.toJSONString(trade));
        tradeService.save(trade);
        logger.info("更新成功");
        return true;
    }

    /**
     * 获得全部交易表记录
     * @return
     */
    @GetMapping("/getAllTrades")
    @ApiOperation(value = "获取全部交易记录")
    public List<Object> getAllTrades(){
        return tradeService.getAll();
    }

    /**
     * 批量更新交易表
     * @param trades
     * @return
     */
    @PutMapping("/addTrades")
    @ApiOperation(value = "批量更新交易记录")
    public Object addTrades(@RequestBody  @Validated List<Trade> trades,BindingResult bindingResult){
        logger.info("批量更新trade:{}",JSON.toJSONString(trades));
        tradeService.batchSave(trades);
        logger.info("批量更新成功");
        return true;
    }

    /**
     * 删除交易表记录
     * @param tradeId
     * @return
     */
    @DeleteMapping("/deleteTrade/{tradeId}")
    @ApiOperation(value = "删除交易记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", required = true, name = "tradeId", value = "交易记录Id")
    })
    public Object deleteTrade(@PathVariable("tradeId")  @NotBlank(message = "{required}")String tradeId){
        logger.info("删除trade,tradeId:{}",tradeId);
        tradeService.delete(tradeId);
        logger.info("删除成功");
        return true;
    }

    /**
     * 查询交易表信息
     * @param tradeId
     * @return
     */
    @GetMapping("/queryTradeById/{tradeId}")
    @ApiOperation(value = "查询交易记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", required = true, name = "tradeId", value = "交易记录Id")
    })
    public Object queryTradeById(@PathVariable("tradeId")  @NotBlank(message = "{required}")String tradeId){
        logger.info("查询交易表信息,tradeId:{}",tradeId);
        return tradeService.getById(tradeId);
    }

    /**
     * 获取交易表大小
     * @return
     */
    @GetMapping("/getTradeSize")
    @ApiOperation(value = "获取交易总量")
    public Integer getTradeSize(){
        return tradeService.getSize();
    }

    /**
     * 分页获取交易记录
     * @param request
     * @return
     */
    @PostMapping("/getTradeByPage")
    @ApiOperation(value = "分页获取交易记录")
    public Object getTradeByPage(@RequestBody @Validated  QueryRequest request,BindingResult bindingResult){
        return tradeService.getByPage(request);
    }

}
