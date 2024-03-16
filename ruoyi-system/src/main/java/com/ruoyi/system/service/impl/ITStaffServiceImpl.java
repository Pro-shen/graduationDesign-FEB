package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.domain.TRestaurant;
import com.ruoyi.system.domain.TUserRestaurant;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ITStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ITStaffServiceImpl implements ITStaffService {

    @Autowired
    private TStaffMapper tStaffMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TRestaurantMapper tRestaurantMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<TUserRestaurant> selectTUserRestaurant(TUserRestaurant tUserRestaurant) {
        List<TUserRestaurant> tUserRestaurantList = tStaffMapper.selectTUserRestaurant(tUserRestaurant);
        for (int i = 0; i < tUserRestaurantList.size(); i++) {
            tUserRestaurantList.get(i).setUserName(sysUserMapper.selectUserById(tUserRestaurantList.get(i).getUserId()).getUserName());
            tUserRestaurantList.get(i).setRestaurantName(tRestaurantMapper.selectTRestaurantByTRestaurantId(tUserRestaurantList.get(i).getRestaurantId()).getRestaurantName());
        }
        return tUserRestaurantList;
    }

    @Override
    public List<SysUser> userList(TUserRestaurant tUserRestaurant) {
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectUserListByRoleId(101L);
        List<SysUser> sysUsers = new ArrayList<>();
        for (int i = 0; i < sysUserRoles.size(); i++) {
            sysUsers.add(sysUserMapper.selectUserById(sysUserRoles.get(i).getUserId()));
        }
        return sysUsers;
    }

    @Override
    public List<TRestaurant> restaurantList(TUserRestaurant tUserRestaurant) {
        return tRestaurantMapper.selectTRestaurant(new TRestaurant());
    }

    @Override
    public int add(TUserRestaurant tUserRestaurant) {
        return tStaffMapper.add(tUserRestaurant);
    }
}
