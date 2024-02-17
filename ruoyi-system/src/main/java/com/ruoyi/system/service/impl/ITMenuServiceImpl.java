package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.system.mapper.TMenuMapper;
import com.ruoyi.system.service.ITMenuService;
import com.ruoyi.system.domain.TMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    @DataScope(deptAlias = "tm")
    public int add(TMenu tMenu) {
        return tMenuMapper.add(tMenu);
    }

    @Override
    @DataScope(deptAlias = "tm")
    public List<TMenu> selectTMenuListById(Long id) {
        return tMenuMapper.selectTMenuListById(id);
    }

    @Override
    public int edit(TMenu tMenu) {
        return tMenuMapper.edit(tMenu);
    }

    @Override
    public TMenu remove(TMenu tMenu) {
        List<Long> resByIdsList = new ArrayList<>();
        TMenu tMenu1 = new TMenu();
        if (tMenu.getId() != null) {
            int res = tMenuMapper.remove(tMenu);
            if(res == 0){
                resByIdsList.add(tMenu.getId());
                tMenu1.setIds(resByIdsList);
                return tMenu1;
            }else {
                tMenu1.setIds(resByIdsList);
                return tMenu1;
            }
        } else if (tMenu.getIds() != null) {
            int resById;
            for(int i = 0;i<tMenu.getIds().size();i++){
                resById = tMenuMapper.removeByIds(tMenu.getIds().get(i));
                if(resById == 0){
                    resByIdsList.add(tMenu.getIds().get(i));
                }
            }
            tMenu1.setIds(resByIdsList);
            return tMenu1;
        }else{
            resByIdsList.add(-1L);
            tMenu1.setIds(resByIdsList);
            return tMenu1;
        }
    }
}
