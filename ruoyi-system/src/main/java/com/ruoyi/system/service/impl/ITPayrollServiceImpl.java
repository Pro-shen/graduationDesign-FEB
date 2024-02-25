package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.system.domain.TPayroll;
import com.ruoyi.system.mapper.TPayrollMapper;
import com.ruoyi.system.service.ITPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ITPayrollServiceImpl implements ITPayrollService {

    @Autowired
    private TPayrollMapper tPayrollMapper;

    @Override
    @DataScope(deptAlias = "tp")
    public List<TPayroll> list(TPayroll tPayroll) {
        return tPayrollMapper.selectTPayrollList(tPayroll);
    }

}
