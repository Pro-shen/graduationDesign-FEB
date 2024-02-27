package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TAttendancesheet;

import java.util.List;

public interface TCheckInMapper {

    public TAttendancesheet selectTAttendancesheet(TAttendancesheet tAttendancesheet);

    public int add(TAttendancesheet tAttendancesheet);

    public List<TAttendancesheet> list(TAttendancesheet tAttendancesheet);

    public List<TAttendancesheet> selectCheckInByDate(String createTime);

}
