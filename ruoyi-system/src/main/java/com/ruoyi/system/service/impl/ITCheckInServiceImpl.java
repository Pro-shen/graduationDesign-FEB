package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.TAttendancesheet;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.TCheckInMapper;
import com.ruoyi.system.service.ITCheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ITCheckInServiceImpl implements ITCheckInService {


    @Autowired
    private TCheckInMapper tCheckInMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @DataScope(deptAlias = "ta")
    public int add(TAttendancesheet tAttendancesheet) {
        SysUser sysUser = sysUserMapper.selectUserByUserName(tAttendancesheet.getUserName());
        tAttendancesheet.setUserId(sysUser.getUserId());
        TAttendancesheet tAttendancesheet1 = tCheckInMapper.selectTAttendancesheet(tAttendancesheet);
        if(tAttendancesheet1!=null){
            return -1;
        }else{
            return tCheckInMapper.add(tAttendancesheet);
        }
    }

    @Override
    @DataScope(deptAlias = "ta")
    public List<TAttendancesheet> list(TAttendancesheet tAttendancesheet) {
        SysUser sysUser = sysUserMapper.selectUserByUserName(tAttendancesheet.getUserName());
        tAttendancesheet.setUserId(sysUser.getUserId());
        List<TAttendancesheet> tAttendancesheets = tCheckInMapper.list(tAttendancesheet);
        for(int i = 0;i<tAttendancesheets.size();i++){
            tAttendancesheets.get(i).setUserName(tAttendancesheet.getUserName());
        }
        return tAttendancesheets;
    }
}
