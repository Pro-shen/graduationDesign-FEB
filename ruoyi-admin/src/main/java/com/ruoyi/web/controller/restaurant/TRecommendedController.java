package com.ruoyi.web.controller.restaurant;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TMenu;
import com.ruoyi.system.domain.TPayroll;
import com.ruoyi.system.domain.TSalestable;
import com.ruoyi.system.service.ITSalestableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/restaurant/recommended")
public class TRecommendedController extends BaseController {

    @Autowired
    private ITSalestableService itSalestableService;

    @PreAuthorize("@ss.hasPermi('restaurant:recommended:list')")
    @GetMapping("/list")
    public TableDataInfo list(TSalestable tSalestable) {
        startPage();
        List<TSalestable> tSalestables = itSalestableService.list(tSalestable);
        return getDataTable(tSalestables);
    }

    @PreAuthorize("@ss.hasPermi('restaurant:recommended:recommend')")
    @GetMapping("/recommend")
    public AjaxResult recommend(TSalestable tSalestable) {
        AjaxResult ajax = new AjaxResult();
        ajax.put("data",itSalestableService.list(tSalestable));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:recommended:listById')")
    @GetMapping("/selectTSalestableListById/{id}")
    public AjaxResult selectTSalestableListById(@PathVariable("id") Long id) {
        AjaxResult ajax = new AjaxResult();
        ajax.put("data", itSalestableService.selectTSalestableListById(id));
        return ajax;
    }


    @PreAuthorize("@ss.hasAnyPermi('restaurant:recommended:add')")
    @Log(title = "进菜管理添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody TSalestable tSalestable) {
        AjaxResult ajax = new AjaxResult();
        ajax.put("data", itSalestableService.add(tSalestable));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:recommended:edit')")
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody TSalestable tSalestable){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itSalestableService.edit(tSalestable));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:recommended:remove')")
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody TSalestable tSalestable){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itSalestableService.remove(tSalestable));
        return ajax;
    }


    @Log(title = "进菜管理表单", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('restaurant:recommended:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, TSalestable tSalestable)
    {
        List<TSalestable> list = itSalestableService.list(tSalestable);
        ExcelUtil<TSalestable> util = new ExcelUtil<TSalestable>(TSalestable.class);
        util.exportExcel(response, list, "进菜管理表单");
    }

    @Log(title = "月工资定时任务", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('restaurant:recommended:timedTasks')")
    @PostMapping("/timedTasks")
    public AjaxResult timedTasks()
    {
        AjaxResult ajax = new AjaxResult();
        ajax.put("data",itSalestableService.dayPayrollCountParams());
        return ajax;
    }

}
