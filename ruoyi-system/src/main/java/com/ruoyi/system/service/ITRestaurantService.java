package com.ruoyi.system.service;

import com.ruoyi.system.domain.TRestaurant;

import java.util.List;

public interface ITRestaurantService {

    public List<TRestaurant> selectTRestaurant(TRestaurant tRestaurant);

    public int add(TRestaurant tRestaurant);

    public int edit(TRestaurant tRestaurant);

    public List<TRestaurant> selectTRestaurantListById(Long id);

    public TRestaurant remove(TRestaurant tRestaurant);

}
