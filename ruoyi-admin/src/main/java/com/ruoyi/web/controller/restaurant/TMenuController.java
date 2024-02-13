package com.ruoyi.web.controller.restaurant;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.TMenu;
import com.ruoyi.system.service.ITMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant/menu")
public class TMenuController extends BaseController {

    @Autowired
    ITMenuService itMenuService;

    @PreAuthorize("@ss.hasPermi('restaurant:menu:list')")
    @GetMapping("/list")
    public TableDataInfo list(TMenu tMenu){
        startPage();
        List<TMenu> tMenus = itMenuService.selectTMenuList(tMenu);
        return getDataTable(tMenus);
    }

}
