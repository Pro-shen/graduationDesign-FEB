package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.system.domain.TFixPrice;
import com.ruoyi.system.domain.TMenu;
import com.ruoyi.system.domain.TSalestable;
import com.ruoyi.system.mapper.TFixPriceMapper;
import com.ruoyi.system.mapper.TMenuMapper;
import com.ruoyi.system.mapper.TSalestableMapper;
import com.ruoyi.system.service.ITSalestableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ITSalestableServiceImpl implements ITSalestableService {

    @Autowired
    private TSalestableMapper tSalestableMapper;

    @Autowired
    private TMenuMapper tMenuMapper;

    @Autowired
    private TFixPriceMapper tFixPriceMapper;

    @Override
    @DataScope(deptAlias = "ts")
    public List<TSalestable> list(TSalestable tSalestable) {
        return tSalestableMapper.list(tSalestable);
    }

    @Override
    @DataScope(deptAlias = "ts")
    public List<TSalestable> selectTSalestableListById(Long id) {
        return tSalestableMapper.selectListById(id);
    }

    @Override
    @DataScope(deptAlias = "ts")
    public int addList(List<TSalestable> tSalestableList) {
        int fres = 0, res;
        for (int i = 0; i < tSalestableList.size(); i++) {
            List<TMenu> tMenus = tMenuMapper.selectTMenuListById(tSalestableList.get(i).getDishId());
            if (tMenus != null) {
                tSalestableList.get(i).setDishName(tMenus.get(0).getDishName());
                TFixPrice tFixPrice = new TFixPrice();
                tFixPrice.setPlateColor(tMenus.get(0).getPlateColor());
                List<TFixPrice> tFixPrices = tFixPriceMapper.selectTFixPriceListByPlateColor(tFixPrice);
                if (tFixPrices != null) {
                    tSalestableList.get(i).setPriceId(tFixPrices.get(0).getId());
                    List<TSalestable> tSalestables = tSalestableMapper.selectListByDishNameAndDate(tSalestableList.get(i));
                    if (tSalestables.size() == 0) {
                        res = tSalestableMapper.add(tSalestableList.get(i));
                    } else {
                        tSalestableList.get(i).setId(tSalestables.get(0).getId());
                        tSalestableList.get(i).setNumber(tSalestableList.get(i).getNumber() + tSalestables.get(0).getNumber());
                        res = tSalestableMapper.update(tSalestableList.get(i));
                    }
                    if (res == 0) {
                        fres++;
                    }
                } else {
                    fres = -1;
                }
            } else {
                fres = -1;
            }
        }
        return fres;
    }

    @Override
    @DataScope(deptAlias = "ts")
    public int add(TSalestable tSalestable) {
        List<TSalestable> tSalestables = tSalestableMapper.selectListByDishNameAndDate(tSalestable);
        if (tSalestables.size() > 0) {
            return -1;
        } else {
            return tSalestableMapper.addHaveCreateTime(tSalestable);
        }
    }

    @Override
    public int edit(TSalestable tSalestable) {
        List<TSalestable> tSalestables = tSalestableMapper.selectListByDishNameAndDate(tSalestable);
        if(tSalestables.size() > 0 && !tSalestables.get(0).getId().equals(tSalestable.getId())){
            return -1;
        }else {
            return tSalestableMapper.edit(tSalestable);
        }
    }

    @Override
    public TSalestable remove(TSalestable tSalestable) {
        List<Long> resByIdsList = new ArrayList<>();
        TSalestable tSalestable1 = new TSalestable();
        if(tSalestable.getId() != null){
            int res = tSalestableMapper.remove(tSalestable);
            if (res == 0) {
                resByIdsList.add(tSalestable.getId());
                tSalestable1.setIds(resByIdsList);
                return tSalestable1;
            } else {
                tSalestable1.setIds(resByIdsList);
                return tSalestable1;
            }
        }else if(!tSalestable.getIds().isEmpty()){
            int resById;
            for (int i = 0; i < tSalestable.getIds().size(); i++) {
                resById = tSalestableMapper.removeByIds(tSalestable.getIds().get(i));
                if (resById == 0) {
                    resByIdsList.add(tSalestable.getIds().get(i));
                }
            }
            tSalestable1.setIds(resByIdsList);
            return tSalestable1;
        }else{
            resByIdsList.add(-1L);
            tSalestable1.setIds(resByIdsList);
            return tSalestable1;
        }
    }
}
