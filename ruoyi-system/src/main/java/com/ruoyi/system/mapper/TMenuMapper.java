package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TFixPrice;
import com.ruoyi.system.domain.TMenu;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface TMenuMapper {

    public List<TMenu> selectTMenuList(TMenu tMenu);

    public int add(TMenu tMenu);

    public List<TMenu> selectTMenuListById(Long id);

    public int edit(TMenu menu);

    public int remove(TMenu tMenu);

    public int removeByIds(@Param("id")Long id);

}
