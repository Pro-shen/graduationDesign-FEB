package com.ruoyi.quartz.task;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.service.ITPayrollService;
import com.ruoyi.system.service.ITSalestableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("TRecommendedTask")
public class TRecommendedTask {

    @Autowired
    private ITSalestableService itSalestableService;

    /**
     * 进菜管理到日工资定时任务
     */
    @Log(title = "进菜管理到日工资定时任务", businessType = BusinessType.INSERT)
    public void DayPayrollCountParams(){
        itSalestableService.dayPayrollCountParams();
    }

}
