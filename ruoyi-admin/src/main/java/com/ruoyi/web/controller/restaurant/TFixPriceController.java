package com.ruoyi.web.controller.restaurant;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.TMenu;
import com.ruoyi.system.domain.TFixPrice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/restaurant/fixPrice")
public class TFixPriceController extends BaseController {

    @PreAuthorize("@ss.hasPermi('restaurant:fixPrice:list')")
    @GetMapping("/list")
    public TableDataInfo list(TFixPrice tFixPrice) {
        startPage();
        List<TFixPrice> tFixPrices = new ArrayList<>();
        return getDataTable(tFixPrices);
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:fixPrice:add')")
    @Log(title = "价格管理", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(@Validated @RequestBody TFixPrice tFixPrice) {
//        if()
        return null;
    }
}
