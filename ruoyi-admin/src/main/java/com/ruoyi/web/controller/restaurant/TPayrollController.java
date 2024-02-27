package com.ruoyi.web.controller.restaurant;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.domain.TFixPrice;
import com.ruoyi.system.domain.TMenu;
import com.ruoyi.system.domain.TPayroll;
import com.ruoyi.system.domain.TSalestable;
import com.ruoyi.system.service.ITPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/restaurant/payroll")
public class TPayrollController extends BaseController {

    @Autowired
    private ITPayrollService itPayrollService;

    @PreAuthorize("@ss.hasPermi('restaurant:payroll:list')")
    @Log(title = "日工资查询", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    public TableDataInfo list(TPayroll tPayroll) {
        startPage();
        List<TPayroll> tPayrolls = itPayrollService.list(tPayroll);
        return getDataTable(tPayrolls);
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:payroll:add')")
    @Log(title = "日工资增加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody TPayroll tPayroll){
        AjaxResult ajax = new AjaxResult();
        ajax.put("data",itPayrollService.add(tPayroll));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:payroll:timedTasks')")
    @Log(title = "日工资定时任务", businessType = BusinessType.OTHER)
    @PostMapping("/selectTPayrollListByDay")
    public AjaxResult selectTPayrollListByDay(){
        AjaxResult ajax = new AjaxResult();
        ajax.put("data",itPayrollService.selectTPayrollListByDay());
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:payroll:edit')")
    @GetMapping("/selectTPayrollListById/{id}")
    public AjaxResult selectTMenuListById(@PathVariable("id") Long id){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itPayrollService.selectTPayrollListById(id));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:payroll:edit')")
    @Log(title = "日工资修改", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody TPayroll tPayroll){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itPayrollService.edit(tPayroll));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:payroll:remove')")
    @Log(title = "日工资删除", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody TPayroll tPayroll){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itPayrollService.remove(tPayroll));
        return ajax;
    }

    @Log(title = "日工资表单", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('restaurant:payroll:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, TPayroll tPayroll)
    {
        List<TPayroll> list = itPayrollService.list(tPayroll);
        ExcelUtil<TPayroll> util = new ExcelUtil<TPayroll>(TPayroll.class);
        util.exportExcel(response, list, "工资表单");
    }

}
