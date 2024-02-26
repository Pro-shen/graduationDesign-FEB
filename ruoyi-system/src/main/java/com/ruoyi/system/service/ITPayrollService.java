package com.ruoyi.system.service;

import com.ruoyi.system.domain.TPayroll;

import java.util.List;

public interface ITPayrollService {

    public List<TPayroll> list(TPayroll tPayroll);

    public int add(TPayroll tPayroll);

    public List<TPayroll> selectTPayrollListById(Long id);

    public int selectTPayrollListByDay();

    public int edit(TPayroll tPayroll);

    public TPayroll remove(TPayroll tPayroll);

}
