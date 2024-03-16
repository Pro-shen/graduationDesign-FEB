package com.ruoyi.web.controller.restaurant;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.TRestaurant;
import com.ruoyi.system.domain.TUserRestaurant;
import com.ruoyi.system.service.ITStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant/staff")
public class TStaffController extends BaseController {

    @Autowired
    private ITStaffService itStaffService;

    @PreAuthorize("@ss.hasPermi('restaurant:staff:list')")
    @GetMapping("/list")
    @Log(title = "员工餐厅关系查询", businessType = BusinessType.OTHER)
    public TableDataInfo list(TUserRestaurant tRestaurant) {
        startPage();
        List<TUserRestaurant> tUserRestaurantList = itStaffService.selectTUserRestaurant(tRestaurant);
        return getDataTable(tUserRestaurantList);
    }

    @PreAuthorize("@ss.hasPermi('restaurant:staff:add')")
    @PostMapping("/userList")
    @Log(title = "员工列表关系查询", businessType = BusinessType.OTHER)
    public List<SysUser> userList(TUserRestaurant tRestaurant) {
        return itStaffService.userList(tRestaurant);
    }

    @PreAuthorize("@ss.hasPermi('restaurant:staff:add')")
    @PostMapping("/restaurantList")
    @Log(title = "餐厅列表关系查询", businessType = BusinessType.OTHER)
    public List<TRestaurant> restaurantList(TUserRestaurant tRestaurant) {
        return itStaffService.restaurantList(tRestaurant);
    }

    @PreAuthorize("@ss.hasAnyPermi('restaurant:staff:add')")
    @Log(title = "餐厅列表关系增加", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody TUserRestaurant tUserRestaurant){
        AjaxResult ajax = new AjaxResult();
        ajax.put("data",itStaffService.add(tUserRestaurant));
        return ajax;
    }


}
