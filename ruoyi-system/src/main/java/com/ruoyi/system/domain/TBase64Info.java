package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

public class TBase64Info extends BaseEntity {

    private String imgName;

    private String base64;

    private List<TObjectList> objectList;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public List<TObjectList> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<TObjectList> objectList) {
        this.objectList = objectList;
    }
}

