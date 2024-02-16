package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.system.domain.TFixPrice;
import com.ruoyi.system.mapper.TFixPriceMapper;
import com.ruoyi.system.service.ITFixPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ITFixPriceServiceImpl implements ITFixPriceService {

    @Autowired
    private TFixPriceMapper tFixPriceMapper;

    @Override
    @DataScope(deptAlias = "ftp")
    public List<TFixPrice> list(TFixPrice tFixPrice) {
        return tFixPriceMapper.selectTFixPriceList(tFixPrice);
    }

    @Override
    @DataScope(deptAlias = "ftp")
    public List<TFixPrice> selectTFixPriceListById(Long id) {
        return tFixPriceMapper.selectTFixPriceListById(id);
    }

    @Override
    @DataScope(deptAlias = "ftp")
    public int add(TFixPrice tFixPrice) {
        List<TFixPrice> tFixPrices = tFixPriceMapper.selectTFixPriceListByPlateColor(tFixPrice);
        if(tFixPrices.isEmpty()){
            return tFixPriceMapper.insertTFixPrice(tFixPrice);
        }else{
            return 2;
        }
    }

    @Override
    public int edit(TFixPrice tFixPrice) {
        List<TFixPrice> tFixPrices = tFixPriceMapper.selectTFixPriceListByPlateColor(tFixPrice);
        if(tFixPrices.isEmpty() || Objects.equals(tFixPrices.get(0).getId(), tFixPrice.getId())){
            return tFixPriceMapper.edit(tFixPrice);
        }else{
            return 2;
        }
    }

    @Override
    public TFixPrice remove(TFixPrice tFixPrice) {
        List<Integer> resByIdsList = new ArrayList<>();
        TFixPrice tFixPrice1 = new TFixPrice();
        if(tFixPrice.getId()!=null){
            int res = tFixPriceMapper.remove(tFixPrice);
            if(res == 0){
                resByIdsList.add(tFixPrice.getId());
                tFixPrice1.setIds(resByIdsList);
                return tFixPrice1;
            }else{
                tFixPrice1.setIds(resByIdsList);
                return tFixPrice1;
            }
        }else if(tFixPrice.getIds()!=null){
            int resById;
            for(int i = 0;i<tFixPrice.getIds().size();i++){
                resById = tFixPriceMapper.removeByIds(tFixPrice.getIds().get(i));
                if(resById == 0){
                    resByIdsList.add(tFixPrice.getIds().get(i));
                }
            }
            tFixPrice1.setIds(resByIdsList);
            return tFixPrice1;
        }else{
            resByIdsList.add(-1);
            return tFixPrice1;
        }
    }
}
