package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TDay;
import com.ruoyi.system.domain.TMenu;
import com.ruoyi.system.domain.TPayroll;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface TPayrollMapper {

    public List<TPayroll> selectTPayrollList(TPayroll tPayroll);

    public int add(TPayroll tPayroll);

    public List<TPayroll> selectTPayrollListByUserNameAndCreateTime(TPayroll tPayroll);

    public List<TPayroll> selectTPayrollListById(Long id);

    public List<TPayroll> selectTPayrollListByDay(TDay tDay);

    public int edit(TPayroll tPayroll);

    public int remove(TPayroll tPayroll);

    public int removeByIds(@Param("id")Long id);

}
