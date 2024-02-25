package com.ruoyi.web.controller.restaurant;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.TPayroll;
import com.ruoyi.system.domain.TSalestable;
import com.ruoyi.system.service.ITPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant/payroll")
public class TPayrollController extends BaseController {

    @Autowired
    private ITPayrollService itPayrollService;

    @PreAuthorize("@ss.hasPermi('restaurant:payroll:list')")
    @GetMapping("/list")
    public TableDataInfo list(TPayroll tPayroll) {
        startPage();
        List<TPayroll> tPayrolls = itPayrollService.list(tPayroll);
        return getDataTable(tPayrolls);
    }



}
