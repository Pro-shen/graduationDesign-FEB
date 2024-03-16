package com.ruoyi.web.controller.restaurant;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.TMenu;
import com.ruoyi.system.domain.TPayroll;
import com.ruoyi.system.domain.TRestaurant;
import com.ruoyi.system.mapper.TRestaurantMapper;
import com.ruoyi.system.service.ITRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant/restaurant")
public class TRestaurantController extends BaseController {

    @Autowired
    private ITRestaurantService itRestaurantService;

    @PreAuthorize("@ss.hasPermi('restaurant:restaurant:list')")
    @GetMapping("/list")
    @Log(title = "餐厅查询", businessType = BusinessType.OTHER)
    public TableDataInfo list(TRestaurant tRestaurant){
        startPage();
        List<TRestaurant> tRestaurants = itRestaurantService.selectTRestaurant(tRestaurant);
        return getDataTable(tRestaurants);
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:restaurant:add')")
    @Log(title = "餐厅增加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody TRestaurant tRestaurant){
        AjaxResult ajax = new AjaxResult();
        ajax.put("data",itRestaurantService.add(tRestaurant));
        return ajax;
    }



    @PreAuthorize("@ss.hasAnyPermi('restaurant:restaurant:edit')")
    @GetMapping("/selectTRestaurantListById/{id}")
    public AjaxResult selectTRestaurantListById(@PathVariable("id") Long id){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itRestaurantService.selectTRestaurantListById(id));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:restaurant:edit')")
    @Log(title = "餐厅修改", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody TRestaurant tRestaurant){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itRestaurantService.edit(tRestaurant));
        return ajax;
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:restaurant:remove')")
    @Log(title = "餐厅删除", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody TRestaurant tRestaurant){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data",itRestaurantService.remove(tRestaurant));
        return ajax;
    }

}
