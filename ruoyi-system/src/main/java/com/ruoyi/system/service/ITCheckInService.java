package com.ruoyi.system.service;

import com.ruoyi.system.domain.TAttendancesheet;

import java.util.List;

public interface ITCheckInService {

    public int add(TAttendancesheet tAttendancesheet);

    public List<TAttendancesheet> list(TAttendancesheet tAttendancesheet);

}
