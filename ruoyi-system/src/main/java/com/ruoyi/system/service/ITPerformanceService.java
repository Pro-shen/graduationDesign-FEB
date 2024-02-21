package com.ruoyi.system.service;

import com.ruoyi.system.domain.TPerformance;

import java.util.List;

public interface ITPerformanceService {

    public List<TPerformance> list(TPerformance tPerformance);

    public int add(TPerformance tPerformance);

    public List<TPerformance> selectTPerformanceListById(Long id);

    public int edit(TPerformance tPerformance);

    public TPerformance remove(TPerformance tPerformance);

}
