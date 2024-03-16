package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TPayroll;
import com.ruoyi.system.domain.TRestaurant;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface TRestaurantMapper {

    public List<TRestaurant> selectTRestaurant(TRestaurant tRestaurant);

    public TRestaurant selectTRestaurantByTRestaurantId(Long id);

    public int add(TRestaurant tRestaurant);

    public int edit(TRestaurant tRestaurant);

    public List<TRestaurant> selectTRestaurantListById(Long id);

    public int remove(TRestaurant tRestaurant);

    public int removeByIds(@Param("id")Long id);
}
