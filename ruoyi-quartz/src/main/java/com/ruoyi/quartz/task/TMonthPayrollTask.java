package com.ruoyi.quartz.task;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.service.ITMonthPayrollService;
import com.ruoyi.system.service.ITPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 工资计算定时任务
 */

@Component("TMonthPayrollTask")
public class TMonthPayrollTask {

    @Autowired
    private ITPayrollService itPayrollService;

    /**
     * 日工资结算到月工资定时任务
     */
    @Log(title = "日工资结算到月工资定时任务", businessType = BusinessType.INSERT)
    public void MonthPayrollCountParams(){
        itPayrollService.selectTPayrollListByDay();
    }

}
