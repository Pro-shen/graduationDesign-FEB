package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TFixPrice;

import java.util.List;

public interface TFixPriceMapper {

    public List<TFixPrice> selectTFixPriceList(TFixPrice tFixPrice);

    public List<TFixPrice> selectTFixPriceListByPlateColor(TFixPrice tFixPrice);

    public int insertTFixPrice(TFixPrice tFixPrice);

    public List<TFixPrice> selectTFixPriceListById(Long id);

    public int edit(TFixPrice tFixPrice);

    public int remove(TFixPrice tFixPrice);

}
