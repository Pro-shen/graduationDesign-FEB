package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TAttendancesheet;
import com.ruoyi.system.domain.TMenu;
import com.ruoyi.system.domain.TPerformance;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface TPerformanceMapper {

    public int add(TPerformance tPerformance);

    public List<TPerformance> list(TPerformance tPerformance);

    public List<TPerformance> selectTPerformanceListByPerformancePosition(TPerformance tPerformance);

    public List<TPerformance> selectTPerformanceListById(Long id);

    public int edit(TPerformance tPerformance);

    public int remove(TPerformance tPerformance);

    public int removeByIds(@Param("id")Long id);

}
