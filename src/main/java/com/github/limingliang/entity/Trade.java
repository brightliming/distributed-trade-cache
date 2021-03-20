package com.github.limingliang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "com.github.limingliang.entity.Trade",description = "接收更新交易记录数据")
public class Trade implements Serializable {
    @NotNull(message = "{required}")
    @ApiModelProperty(value = "交易记录id",dataType = "Long",required = true)
    private Long tradeId;
    @JsonFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "交易时间",example = "2021-01-01",required = true)
    private Date tradeDate;
    @ApiModelProperty(value = "客户端",dataType = "String")
    private String client;
    @ApiModelProperty(value = "产品",dataType = "String")
    private String product;
    @NumberFormat
    @ApiModelProperty(value = "交易金额",required = true)
    private BigDecimal amount;
    @NotBlank(message = "{required}")
    @ApiModelProperty(value = "更新人",dataType = "String",required = true)
    private String updateBy;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间",example = "2021-01-01 15:16:03",required = true)
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
