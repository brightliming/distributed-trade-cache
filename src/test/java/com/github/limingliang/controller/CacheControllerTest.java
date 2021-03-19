package com.github.limingliang.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.limingliang.entity.Trade;
import com.github.limingliang.service.TradeService;
import com.github.limingliang.utils.DateUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CacheControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private TradeService tradeService;
    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //准备mock数据源
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
    public void testAddTrade() throws Exception {
        Trade trade = new Trade();
        trade.setTradeId(11l);
        trade.setClient("ios");
        trade.setAmount(new BigDecimal(1.2));
        trade.setProduct("123");
        trade.setTradeDate(DateUtil.formatDate("2021-03-19", DateUtil.YYYY_MM_DD));
        trade.setUpdateTime(DateUtil.formatDate("2021-03-19 16:48:01", DateUtil.YYYY_MM_DD_HH_MM_SS));
        trade.setUpdateBy("devid");

        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.put("/addTrade")
                .content(JSON.toJSONString(trade))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int status=mvcResult.getResponse().getStatus();
        String result =mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(200,status);
        Assert.assertTrue(Boolean.parseBoolean(result));
    }

}