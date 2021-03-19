package com.github.limingliang.service;

import com.github.limingliang.entity.Page;
import com.github.limingliang.entity.QueryRequest;
import com.github.limingliang.entity.Trade;

import java.util.List;

/**
 * 交易服务类
 */
public interface TradeService {

    /**
     * 更新交易表
     * @param trade
     * @return
     */
    public boolean save(Trade trade);

    /**
     * 批量更新交易表
     * @param trades
     * @return
     */
    public boolean batchSave(List<Trade> trades);

    /**
     * 删除交易记录
     * @param tradeId
     * @return
     */
    public boolean delete(String tradeId);

    /**
     * 删除交易记录表
     * @return
     */
    public boolean deleteTable();
    /**
     * 通过交易ID获得交易记录
     * @param tradeId
     * @return
     */
    public Trade getById(String tradeId);

    /**
     * 获得交易记录数量
     * @return
     */
    public Integer getSize();

    /**
     * 获得所有的交易记录
     * @return
     */
    public List<Object> getAll();

    /**
     *
     * @param request
     * @return
     */
    public Page<Trade> getByPage(QueryRequest request);
}
