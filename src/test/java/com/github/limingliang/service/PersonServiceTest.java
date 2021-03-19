package com.github.limingliang.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.limingliang.entity.Page;
import com.github.limingliang.entity.QueryRequest;
import com.github.limingliang.entity.Trade;
import com.github.limingliang.utils.DateUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private TradeService tradeService;

    @Before
    public void setUp() throws Exception{
        try(FileReader f = new FileReader(Thread.currentThread().getContextClassLoader().getResource("datasource.json").getPath())){
            ObjectMapper mapper=new ObjectMapper();
            JsonNode node = mapper.readTree(f);
            List<Trade> tradeList = JSON.parseArray(node.toString(),Trade.class);
            tradeService.batchSave(tradeList);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @After
    public void destory(){
        //清理测试数据
        tradeService.deleteTable();
    }
    @Test
    public void testSave(){
        Trade trade = new Trade();
        trade.setTradeId(11l);
        trade.setClient("ios");
        trade.setAmount(new BigDecimal(1.2));
        trade.setProduct("123");
        trade.setTradeDate(DateUtil.formatDate("2021-03-19", DateUtil.YYYY_MM_DD));
        trade.setUpdateTime(DateUtil.formatDate("2021-03-19 16:48:01", DateUtil.YYYY_MM_DD_HH_MM_SS));
        trade.setUpdateBy("devid");

        boolean result = tradeService.save(trade);
        Assert.assertTrue(result);
    }

    @Test
    public void testDelete(){
        boolean result = tradeService.delete("2");
        Trade trade = tradeService.getById("2");
        Assert.assertTrue(result);
        Assert.assertNull(trade);
    }

    @Test
    public void testGetById(){
        Trade trade = tradeService.getById("2");
        Assert.assertEquals(2l,trade.getTradeId().longValue());
    }

    @Test
    public void testGetSize(){
        int size = tradeService.getSize();
        Assert.assertEquals(10,size);
    }

    @Test
    public void testGetAll(){
        List<Object> result = tradeService.getAll();
        Assert.assertEquals(10,result.size());
        Object trade = result.get(0);
        Assert.assertTrue(trade instanceof Trade);
    }

    @Test
    public void testGetByPage(){
        QueryRequest request = new QueryRequest();
        request.setPageNum(1);
        request.setPageSize(5);
        Page page = tradeService.getByPage(request);
        Assert.assertEquals(page.getSize(),page.getRecords().size());
    }

}
