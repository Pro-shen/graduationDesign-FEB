package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

public class TMenu extends BaseEntity {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 价格id
     */
    private Long priceId;

    /**
     * 菜品名称
     */
    private String dishName;

    /**
     * 菜品存量
     */
    private Integer dishStock;

    /**
     * 菜品销量
     */
    private Integer dishUsed;

    /**
     * 菜品盈利
     */
    private double dishProfit;

    /**
     * is_state
     */
    private Integer isState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人id
     */
    private Long updateId;

    /**
     * 租户id
     */
    private Long tenantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Integer getDishStock() {
        return dishStock;
    }

    public void setDishStock(Integer dishStock) {
        this.dishStock = dishStock;
    }

    public Integer getDishUsed() {
        return dishUsed;
    }

    public void setDishUsed(Integer dishUsed) {
        this.dishUsed = dishUsed;
    }

    public double getDishProfit() {
        return dishProfit;
    }

    public void setDishProfit(double dishProfit) {
        this.dishProfit = dishProfit;
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
}

