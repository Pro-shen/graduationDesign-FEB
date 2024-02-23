package com.ruoyi.system.service;

import com.ruoyi.system.domain.TFixPrice;
import com.ruoyi.system.domain.TMenu;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface ITMenuService {

    public List<TMenu> selectTMenuList(TMenu tMenu);

    public int add(TMenu tMenu);

    public List<TMenu> selectTMenuListById(Long id);

    public int edit(TMenu tMenu);

    public TMenu remove(TMenu tMenu);

    public List<TFixPrice> listTree(List<TFixPrice> tFixPrices);



}
