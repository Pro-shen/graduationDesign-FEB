package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.system.mapper.TMenuMapper;
import com.ruoyi.system.service.ITMenuService;
import com.ruoyi.system.domain.TMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ITMenuServiceImpl implements ITMenuService {

    @Autowired
    private TMenuMapper tMenuMapper;

    @Override
    @DataScope(deptAlias = "tm")
    public List<TMenu> selectTMenuList(TMenu tMenu) {
        return tMenuMapper.selectTMenuList(tMenu);
    }
}
