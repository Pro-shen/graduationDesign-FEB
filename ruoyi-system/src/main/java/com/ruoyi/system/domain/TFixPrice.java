package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.List;

public class TFixPrice extends BaseEntity {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 盘子颜色
     */
    @Excel(name = "盘子颜色", readConverterExp = "0=红色,1=蓝色,2=绿色,3=黄色,4=粉色,5=橙色,6=紫色,7=白色")
    private Integer plateColor;

    /**
     * 盘子价格
     */
    @Excel(name = "盘子价格")
    private Double platePrice;

    /**
     * is_state
     */
    private Integer isState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
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

    /**
     * 价格菜单表
     */
    private List<TMenu> tMenus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(Integer plateColor) {
        this.plateColor = plateColor;
    }

    public Double getPlatePrice() {
        return platePrice;
    }

    public void setPlatePrice(Double platePrice) {
        this.platePrice = platePrice;
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

    public List<TMenu> gettMenus() {
        return tMenus;
    }

    public void settMenus(List<TMenu> tMenus) {
        this.tMenus = tMenus;
    }
}
