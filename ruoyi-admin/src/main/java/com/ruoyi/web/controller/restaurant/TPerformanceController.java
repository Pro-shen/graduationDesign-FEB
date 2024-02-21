package com.ruoyi.web.controller.restaurant;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.domain.TMenu;
import com.ruoyi.system.domain.TPerformance;
import com.ruoyi.system.service.ITPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/restaurant/performance")
public class TPerformanceController extends BaseController {

    @Autowired
    private ITPerformanceService itPerformanceService;

    @PreAuthorize("@ss.hasPermi('restaurant:performance:list')")
    @GetMapping("/list")
    public TableDataInfo list(TPerformance tPerformance){
        startPage();
        List<TPerformance> tPerformances = itPerformanceService.list(tPerformance);
        return getDataTable(tPerformances);
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:performance:add')")
    @Log(title = "绩效规则添加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody TPerformance tPerformance){
        AjaxResult ajax = new AjaxResult();
        ajax.put("data",itPerformanceService.add(tPerformance));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:performance:edit')")
    @GetMapping("selectTPerformanceListById/{id}")
    public AjaxResult selectTPerformanceListById(@PathVariable("id") Long id){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itPerformanceService.selectTPerformanceListById(id));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:performance:edit')")
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody TPerformance tPerformance){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itPerformanceService.edit(tPerformance));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:performance:remove')")
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody TPerformance tPerformance){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itPerformanceService.remove(tPerformance));
        return ajax;
    }


}
