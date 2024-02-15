package com.ruoyi.web.controller.restaurant;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.TFixPrice;
import com.ruoyi.system.service.ITFixPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant/fixPrice")
public class TFixPriceController extends BaseController {

    @Autowired
    private ITFixPriceService itFixPriceService;

    @PreAuthorize("@ss.hasPermi('restaurant:fixPrice:list')")
    @GetMapping("/list")
    public TableDataInfo list(TFixPrice tFixPrice) {
        startPage();
        List<TFixPrice> tFixPrices = itFixPriceService.list(tFixPrice);
        return getDataTable(tFixPrices);
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:fixPrice:add')")
    @Log(title = "价格管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody TFixPrice tFixPrice) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itFixPriceService.add(tFixPrice));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:fixPrice:edit')")
    @GetMapping("/selectTFixPriceListById/{id}")
    public AjaxResult selectTFixPriceListById(@PathVariable("id") Long id){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itFixPriceService.selectTFixPriceListById(id));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:fixPrice:edit')")
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody TFixPrice tFixPrice){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itFixPriceService.edit(tFixPrice));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:fixPrice:remove')")
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody TFixPrice tFixPrice){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itFixPriceService.remove(tFixPrice));
        return ajax;
    }
}
