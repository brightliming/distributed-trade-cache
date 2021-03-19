package com.github.limingliang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Trade implements Serializable {
    @NotNull(message = "{required}")
    private Long tradeId;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date tradeDate;

    private String client;

    private String product;
    @NumberFormat
    private BigDecimal amount;
    @NotBlank(message = "{required}")
    private String updateBy;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @Override
    public String toString() {
        return "Trade{" +
                "tradeId=" + tradeId +
                ", tradeDate=" + tradeDate +
                ", client='" + client + '\'' +
                ", product='" + product + '\'' +
                ", amount=" + amount +
                ", updateBy='" + updateBy + '\'' +
                ", updateDime=" + updateTime +
                '}';
    }



    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
