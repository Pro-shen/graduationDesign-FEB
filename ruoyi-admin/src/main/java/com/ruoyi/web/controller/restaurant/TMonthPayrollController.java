package com.ruoyi.web.controller.restaurant;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TMonthPayroll;
import com.ruoyi.system.domain.TPayroll;
import com.ruoyi.system.service.ITMonthPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/restaurant/monthPayroll")
public class TMonthPayrollController extends BaseController {

    @Autowired
    private ITMonthPayrollService itMonthPayrollService;

    @PreAuthorize("@ss.hasPermi('restaurant:monthPayroll:list')")
    @GetMapping("/list")
    public TableDataInfo list(TMonthPayroll tMonthPayroll) {
        startPage();
        List<TMonthPayroll> tMonthPayrolls = itMonthPayrollService.list(tMonthPayroll);
        return getDataTable(tMonthPayrolls);
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:monthPayroll:add')")
    @Log(title = "工资增加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody TMonthPayroll tMonthPayroll){
        AjaxResult ajax = new AjaxResult();
        ajax.put("data",itMonthPayrollService.add(tMonthPayroll));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:monthPayroll:edit')")
    @GetMapping("/selectTPayrollListById/{id}")
    public AjaxResult selectTMenuListById(@PathVariable("id") Long id){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itMonthPayrollService.selectTMonthPayrollListById(id));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:monthPayroll:edit')")
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody TMonthPayroll tMonthPayroll){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itMonthPayrollService.edit(tMonthPayroll));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:monthPayroll:remove')")
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody TMonthPayroll tMonthPayroll){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itMonthPayrollService.remove(tMonthPayroll));
        return ajax;
    }

    @Log(title = "工资表单", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('restaurant:monthPayroll:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, TMonthPayroll tMonthPayroll)
    {
        List<TMonthPayroll> list = itMonthPayrollService.list(tMonthPayroll);
        ExcelUtil<TMonthPayroll> util = new ExcelUtil<TMonthPayroll>(TMonthPayroll.class);
        util.exportExcel(response, list, "工资表单");
    }

}
