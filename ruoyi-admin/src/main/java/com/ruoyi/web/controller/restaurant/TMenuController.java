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
import com.ruoyi.system.service.ITMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/restaurant/menu")
public class TMenuController extends BaseController {

    @Autowired
    private ITMenuService itMenuService;

    @PreAuthorize("@ss.hasPermi('restaurant:menu:list')")
    @GetMapping("/list")
    public TableDataInfo list(TMenu tMenu){
        startPage();
        List<TMenu> tMenus = itMenuService.selectTMenuList(tMenu);
        return getDataTable(tMenus);
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:menu:listTree')")
    @Log(title = "菜单树查询", businessType = BusinessType.INSERT)
    @PostMapping("/listTree")
    public AjaxResult listTree(@Validated @RequestBody List<TFixPrice> tFixPrices){
        AjaxResult ajax = new AjaxResult();
        ajax.put("data",itMenuService.listTree(tFixPrices));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:menu:add')")
    @Log(title = "菜单增加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody TMenu tMenu){
        AjaxResult ajax = new AjaxResult();
        ajax.put("data",itMenuService.add(tMenu));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:menu:edit')")
    @GetMapping("/selectTMenuListById/{id}")
    public AjaxResult selectTMenuListById(@PathVariable("id") Long id){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itMenuService.selectTMenuListById(id));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:menu:edit')")
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody TMenu tMenu){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itMenuService.edit(tMenu));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:menu:remove')")
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody TMenu tMenu){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itMenuService.remove(tMenu));
        return ajax;
    }

    @Log(title = "菜单表单", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('restaurant:menu:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, TMenu tMenu)
    {
        List<TMenu> list = itMenuService.selectTMenuList(tMenu);
        ExcelUtil<TMenu> util = new ExcelUtil<TMenu>(TMenu.class);
        util.exportExcel(response, list, "菜单表单");
    }

}
