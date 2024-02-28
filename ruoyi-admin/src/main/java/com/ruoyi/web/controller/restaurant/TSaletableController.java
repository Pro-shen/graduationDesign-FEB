package com.ruoyi.web.controller.restaurant;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TMenu;
import com.ruoyi.system.domain.TSalestable;
import com.ruoyi.system.service.ITSalestableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/restaurant/sale")
public class TSaletableController extends BaseController {

    @Autowired
    private ITSalestableService itSalestableService;

    @PreAuthorize("@ss.hasPermi('restaurant:sale:list')")
    @Log(title = "销售管理查询", businessType = BusinessType.OTHER)
    @GetMapping("/list")
    public TableDataInfo list(TSalestable tSalestable) {
        startPage();
        List<TSalestable> tSalestables = itSalestableService.list(tSalestable);
        return getDataTable(tSalestables);
    }

    @Log(title = "销售报表导出", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('restaurant:sale:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, TSalestable tSalestable)
    {
        List<TSalestable> list = itSalestableService.listExport(tSalestable);
        ExcelUtil<TSalestable> util = new ExcelUtil<TSalestable>(TSalestable.class);
        util.exportExcel(response, list, "菜单表单");
    }

    @Log(title = "月销售报表导出", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('restaurant:sale:export')")
    @PostMapping("/exportMonth")
    public void exportMonth(HttpServletResponse response, TSalestable tSalestable)
    {
        List<TSalestable> list = itSalestableService.listExportMonth(tSalestable);
        ExcelUtil<TSalestable> util = new ExcelUtil<TSalestable>(TSalestable.class);
        util.exportExcel(response, list, "菜单表单");
    }

    @Log(title = "季度销售报表导出", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('restaurant:sale:export')")
    @PostMapping("/exportQuarter")
    public void exportQuarter(HttpServletResponse response, TSalestable tSalestable)
    {
        List<TSalestable> list = itSalestableService.listExportQuarter(tSalestable);
        ExcelUtil<TSalestable> util = new ExcelUtil<TSalestable>(TSalestable.class);
        util.exportExcel(response, list, "菜单表单");
    }



    @Log(title = "年销售报表导出", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('restaurant:sale:export')")
    @PostMapping("/exportYear")
    public void exportYear(HttpServletResponse response, TSalestable tSalestable)
    {
        List<TSalestable> list = itSalestableService.listExportYear(tSalestable);
        ExcelUtil<TSalestable> util = new ExcelUtil<TSalestable>(TSalestable.class);
        util.exportExcel(response, list, "菜单表单");
    }

}
