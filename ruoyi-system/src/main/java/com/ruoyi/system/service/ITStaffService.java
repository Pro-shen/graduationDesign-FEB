package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.TRestaurant;
import com.ruoyi.system.domain.TUserRestaurant;

import java.util.List;

public interface ITStaffService {

    public List<TUserRestaurant> selectTUserRestaurant(TUserRestaurant tUserRestaurant);

    public List<SysUser> userList(TUserRestaurant tUserRestaurant);

    public List<TRestaurant> restaurantList(TUserRestaurant tUserRestaurant);

    public int add(TUserRestaurant tUserRestaurant);

}
