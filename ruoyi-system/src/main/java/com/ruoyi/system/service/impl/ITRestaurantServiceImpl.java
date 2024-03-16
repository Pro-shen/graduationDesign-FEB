package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.TPayroll;
import com.ruoyi.system.domain.TRestaurant;
import com.ruoyi.system.mapper.TRestaurantMapper;
import com.ruoyi.system.service.ITRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ITRestaurantServiceImpl implements ITRestaurantService {

    @Autowired
    private TRestaurantMapper tRestaurantMapper;

    @Override
    public List<TRestaurant> selectTRestaurant(TRestaurant tRestaurant) {
        return tRestaurantMapper.selectTRestaurant(tRestaurant);
    }

    @Override
    public int add(TRestaurant tRestaurant) {
        return tRestaurantMapper.add(tRestaurant);
    }

    @Override
    public int edit(TRestaurant tRestaurant) {
        return tRestaurantMapper.edit(tRestaurant);
    }

    @Override
    public List<TRestaurant> selectTRestaurantListById(Long id) {
        return tRestaurantMapper.selectTRestaurantListById(id);
    }

    @Override
    public TRestaurant remove(TRestaurant tRestaurant) {
        List<Long> tRestaurantList = new ArrayList<>();
        TRestaurant tRestaurant1 = new TRestaurant();
        if (tRestaurant.getId() != null) {
            int res = tRestaurantMapper.remove(tRestaurant);
            if (res == 0) {
                tRestaurantList.add(tRestaurant.getId());
                tRestaurant1.setIds(tRestaurantList);
                return tRestaurant1;
            } else {
                tRestaurant1.setIds(tRestaurantList);
                return tRestaurant1;
            }
        } else if (!tRestaurant.getIds().isEmpty()) {
            int resById;
            for (int i = 0; i < tRestaurant.getIds().size(); i++) {
                resById = tRestaurantMapper.removeByIds(tRestaurant.getIds().get(i));
                if (resById == 0) {
                    tRestaurantList.add(tRestaurant.getIds().get(i));
                }
            }
            tRestaurant1.setIds(tRestaurantList);
            return tRestaurant1;
        } else {
            tRestaurantList.add(-1L);
            tRestaurant1.setIds(tRestaurantList);
            return tRestaurant1;
        }
    }
}
