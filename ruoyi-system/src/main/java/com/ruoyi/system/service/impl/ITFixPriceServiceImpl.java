package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.TFixPrice;
import com.ruoyi.system.mapper.TFixPriceMapper;
import com.ruoyi.system.service.ITFixPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(tFixPrices.size() == 0){
            return tFixPriceMapper.insertTFixPrice(tFixPrice);
        }else{
            return 2;
        }
    }


}
