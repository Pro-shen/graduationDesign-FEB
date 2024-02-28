package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.List;

public class TSalestable extends BaseEntity {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 菜品名称
     */
    @Excel(name = "菜品名称")
    private String dishName;

    /**
     * 份数
     */
    @Excel(name = "菜品销量")
    private Integer number;

    /**
     * 时间
     */
    private Date dateTime;

    /**
     * 菜品id
     */
    private Long dishId;

    /**
     * 菜品盈利
     */
    @Excel(name = "菜品利润")
    private Double dishProfit;

    /**
     * 菜品总利润
     */
    @Excel(name = "菜品总利润")
    private Double dishProfitTotal;

    /**
     * 价格id
     */
    private Long priceId;

    /**
     * is_state
     */
    private Integer isState;

    /**
     * 创建时间
     */
    @Excel(name = "销售时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 更新人id
     */
    private Long updateId;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * ids
     */
    private List<Long> ids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Double getDishProfit() {
        return dishProfit;
    }

    public void setDishProfit(Double dishProfit) {
        this.dishProfit = dishProfit;
    }

    public Double getDishProfitTotal() {
        return dishProfitTotal;
    }

    public void setDishProfitTotal(Double dishProfitTotal) {
        this.dishProfitTotal = dishProfitTotal;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Integer getIsState() {
        return isState;
    }

    public void setIsState(Integer isState) {
        this.isState = isState;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
