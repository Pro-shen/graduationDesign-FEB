package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TMonthPayroll;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface TMonthPayrollMapper {

    public List<TMonthPayroll> selectTMonthPayrollList(TMonthPayroll tMonthPayroll);

    public int add(TMonthPayroll tMonthPayroll);

    public List<TMonthPayroll> selectTMonthPayrollListByUserNameAndCreateTime(TMonthPayroll tMonthPayroll);

    public List<TMonthPayroll> selectTMonthPayrollListById(Long id);

    public int edit(TMonthPayroll tMonthPayroll);

    public int remove(TMonthPayroll tMonthPayroll);

    public int removeByIds(@Param("id")Long id);

}
