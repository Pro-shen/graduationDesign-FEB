package com.ruoyi.system.service;

import com.ruoyi.system.domain.TMonthPayroll;

import java.util.List;

public interface ITMonthPayrollService {

    public List<TMonthPayroll> list(TMonthPayroll tMonthPayroll);

    public int add(TMonthPayroll tMonthPayroll);

    public List<TMonthPayroll> selectTMonthPayrollListById(Long id);

    public int edit(TMonthPayroll tMonthPayroll);

    public TMonthPayroll remove(TMonthPayroll tMonthPayroll);

}
