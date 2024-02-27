package com.ruoyi.quartz.task;

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

    public void DayPayrollCountParams(){
        itSalestableService.dayPayrollCountParams();
    }

}
