package com.ruoyi.system.service;

import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.TFixPrice;

import java.util.List;

public interface ITFixPriceService {

    public List<TFixPrice> list(TFixPrice tFixPrice);

    public List<TFixPrice> selectTFixPriceListById(Long id);

    public int add(TFixPrice tFixPrice);



}
