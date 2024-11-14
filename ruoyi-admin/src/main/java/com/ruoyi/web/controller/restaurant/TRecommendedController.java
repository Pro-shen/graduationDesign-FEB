package com.ruoyi.web.controller.restaurant;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TFixPrice;
import com.ruoyi.system.domain.TMenu;
import com.ruoyi.system.domain.TPayroll;
import com.ruoyi.system.domain.TSalestable;
import com.ruoyi.system.service.ITFixPriceService;
import com.ruoyi.system.service.ITMenuService;
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

    @Autowired
    private ITMenuService itMenuService;

    @Autowired
    private ITFixPriceService itFixPriceService;

    @PreAuthorize("@ss.hasPermi('restaurant:recommended:list')")
    @Log(title = "进菜管理查询", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    public TableDataInfo list(TSalestable tSalestable) {
        startPage();
        List<TSalestable> tSalestables = itSalestableService.list(tSalestable);
        return getDataTable(tSalestables);
    }

    @PreAuthorize("@ss.hasPermi('restaurant:recommended:recommend')")
    @Log(title = "进菜管理建议", businessType = BusinessType.OTHER)
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
    @Log(title = "进菜管理修改", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody TSalestable tSalestable){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itSalestableService.edit(tSalestable));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:recommended:remove')")
    @Log(title = "进菜管理删除", businessType = BusinessType.DELETE)
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

    @Log(title = "日工资定时任务", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('restaurant:recommended:timedTasks')")
    @PostMapping("/timedTasks")
    public AjaxResult timedTasks()
    {
        AjaxResult ajax = new AjaxResult();
        ajax.put("data",itSalestableService.dayPayrollCountParams());
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:recommended:listTree')")
    @Log(title = "菜单树查询", businessType = BusinessType.OTHER)
    @PostMapping("/listTree")
    public AjaxResult listTree(@Validated @RequestBody List<TFixPrice> tFixPrices){
        AjaxResult ajax = new AjaxResult();
        ajax.put("data",itMenuService.listTree(tFixPrices));
        return ajax;
    }

    @PreAuthorize("@ss.hasPermi('restaurant:recommended:listTree')")
    @GetMapping("/listPrice")
    @Log(title = "价格管理列表", businessType = BusinessType.OTHER)
    public TableDataInfo list(TFixPrice tFixPrice) {
        startPage();
        List<TFixPrice> tFixPrices = itFixPriceService.list(tFixPrice);
        return getDataTable(tFixPrices);
    }

}
