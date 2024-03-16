package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TUserRestaurant;

import java.util.List;

public interface TStaffMapper {

    public List<TUserRestaurant> selectTUserRestaurant(TUserRestaurant tUserRestaurant);

    public int add(TUserRestaurant tUserRestaurant);

}
