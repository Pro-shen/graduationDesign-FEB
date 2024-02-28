package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.TFixPriceMapper;
import com.ruoyi.system.mapper.TMenuMapper;
import com.ruoyi.system.mapper.TPayrollMapper;
import com.ruoyi.system.mapper.TSalestableMapper;
import com.ruoyi.system.service.ITCheckInService;
import com.ruoyi.system.service.ITPerformanceService;
import com.ruoyi.system.service.ITSalestableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ITSalestableServiceImpl implements ITSalestableService {

    @Autowired
    private TSalestableMapper tSalestableMapper;

    @Autowired
    private TMenuMapper tMenuMapper;

    @Autowired
    private TFixPriceMapper tFixPriceMapper;

    @Autowired
    private TPayrollMapper tPayrollMapper;

    @Autowired
    private ITPerformanceService itPerformanceService;

    @Autowired
    private ITCheckInService itCheckInService;

    @Override
    @DataScope(deptAlias = "ts")
    public List<TSalestable> list(TSalestable tSalestable) {
        List<TSalestable> tSalestables = tSalestableMapper.list(tSalestable);
        for (int i = 0; i < tSalestables.size(); i++) {
            tSalestables.get(i).setDishProfitTotal(tSalestables.get(i).getNumber() * tSalestables.get(i).getDishProfit());
        }
        return tSalestables;
    }

    @Override
    @DataScope(deptAlias = "ts")
    public List<TSalestable> listExport(TSalestable tSalestable) {
        List<TSalestable> tSalestables = tSalestableMapper.list(tSalestable);
        List<TSalestable> tSalestableList = new ArrayList<>();
        for (int i = 0; i < tSalestables.size(); i++) {
            int flag = 0;
            for (int j = 0; j < tSalestableList.size(); j++) {
                if (Objects.equals(tSalestables.get(i).getDishId(), tSalestableList.get(j).getDishId())) {
                    tSalestableList.get(j).setNumber(tSalestableList.get(j).getNumber() + tSalestables.get(i).getNumber());
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                TSalestable tSalestable1 = new TSalestable();
                tSalestable1.setDishId(tSalestables.get(i).getDishId());
                tSalestable1.setNumber(tSalestables.get(i).getNumber());
                tSalestable1.setDishName(tSalestables.get(i).getDishName());
                tSalestable1.setCreateTime(tSalestables.get(i).getCreateTime());
                tSalestable1.setUpdateTime(tSalestables.get(i).getUpdateTime());
                tSalestable1.setDishProfit(tSalestables.get(i).getDishProfit());
                tSalestableList.add(tSalestable1);
            }
        }
        for (int i = 0; i < tSalestableList.size(); i++) {
            tSalestableList.get(i).setDishProfitTotal(tSalestableList.get(i).getNumber() * tSalestableList.get(i).getDishProfit());
        }
        return tSalestableList;
    }

    @Override
    public List<TSalestable> listExportMonth(TSalestable tSalestable) {
        TSalestable tSalestable2 = new TSalestable();
        Map<String, Object> params = new HashMap<>();
        params.put("beginTime", DateUtils.getFirstDay());
        params.put("endTime", DateUtils.getLastDay());
        tSalestable2.setParams(params);
        List<TSalestable> tSalestables = tSalestableMapper.list(tSalestable2);
        List<TSalestable> tSalestableList = new ArrayList<>();
        for (int i = 0; i < tSalestables.size(); i++) {
            int flag = 0;
            for (int j = 0; j < tSalestableList.size(); j++) {
                if (Objects.equals(tSalestables.get(i).getDishId(), tSalestableList.get(j).getDishId())) {
                    tSalestableList.get(j).setNumber(tSalestableList.get(j).getNumber() + tSalestables.get(i).getNumber());
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                TSalestable tSalestable1 = new TSalestable();
                tSalestable1.setDishId(tSalestables.get(i).getDishId());
                tSalestable1.setNumber(tSalestables.get(i).getNumber());
                tSalestable1.setDishName(tSalestables.get(i).getDishName());
                tSalestable1.setCreateTime(tSalestables.get(i).getCreateTime());
                tSalestable1.setUpdateTime(tSalestables.get(i).getUpdateTime());
                tSalestable1.setDishProfit(tSalestables.get(i).getDishProfit());
                tSalestableList.add(tSalestable1);
            }
        }
        for (int i = 0; i < tSalestableList.size(); i++) {
            tSalestableList.get(i).setDishProfitTotal(tSalestableList.get(i).getNumber() * tSalestableList.get(i).getDishProfit());
        }
        return tSalestableList;
    }

    @Override
    public List<TSalestable> listExportQuarter(TSalestable tSalestable) {
        TSalestable tSalestable2 = new TSalestable();
        Map<String, Object> params = new HashMap<>();
        params.put("beginTime", DateUtils.getStartDayOfQuarter());
        params.put("endTime", DateUtils.getLastDayOfQuarter());
        tSalestable2.setParams(params);
        List<TSalestable> tSalestables = tSalestableMapper.list(tSalestable2);
        List<TSalestable> tSalestableList = new ArrayList<>();
        for (int i = 0; i < tSalestables.size(); i++) {
            int flag = 0;
            for (int j = 0; j < tSalestableList.size(); j++) {
                if (Objects.equals(tSalestables.get(i).getDishId(), tSalestableList.get(j).getDishId())) {
                    tSalestableList.get(j).setNumber(tSalestableList.get(j).getNumber() + tSalestables.get(i).getNumber());
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                TSalestable tSalestable1 = new TSalestable();
                tSalestable1.setDishId(tSalestables.get(i).getDishId());
                tSalestable1.setNumber(tSalestables.get(i).getNumber());
                tSalestable1.setDishName(tSalestables.get(i).getDishName());
                tSalestable1.setCreateTime(tSalestables.get(i).getCreateTime());
                tSalestable1.setUpdateTime(tSalestables.get(i).getUpdateTime());
                tSalestable1.setDishProfit(tSalestables.get(i).getDishProfit());
                tSalestableList.add(tSalestable1);
            }
        }
        for (int i = 0; i < tSalestableList.size(); i++) {
            tSalestableList.get(i).setDishProfitTotal(tSalestableList.get(i).getNumber() * tSalestableList.get(i).getDishProfit());
        }
        return tSalestableList;
    }

    @Override
    public List<TSalestable> listExportYear(TSalestable tSalestable) {
        TSalestable tSalestable2 = new TSalestable();
        Map<String, Object> params = new HashMap<>();
        System.out.println(DateUtils.getStartDayOfYear() + "    " + DateUtils.getLastDayOfYear());
        params.put("beginTime", DateUtils.getStartDayOfYear());
        params.put("endTime", DateUtils.getLastDayOfYear());
        tSalestable2.setParams(params);
        List<TSalestable> tSalestables = tSalestableMapper.list(tSalestable2);
        List<TSalestable> tSalestableList = new ArrayList<>();
        for (int i = 0; i < tSalestables.size(); i++) {
            int flag = 0;
            for (int j = 0; j < tSalestableList.size(); j++) {
                if (Objects.equals(tSalestables.get(i).getDishId(), tSalestableList.get(j).getDishId())) {
                    tSalestableList.get(j).setNumber(tSalestableList.get(j).getNumber() + tSalestables.get(i).getNumber());
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                TSalestable tSalestable1 = new TSalestable();
                tSalestable1.setDishId(tSalestables.get(i).getDishId());
                tSalestable1.setNumber(tSalestables.get(i).getNumber());
                tSalestable1.setDishName(tSalestables.get(i).getDishName());
                tSalestable1.setCreateTime(tSalestables.get(i).getCreateTime());
                tSalestable1.setUpdateTime(tSalestables.get(i).getUpdateTime());
                tSalestable1.setDishProfit(tSalestables.get(i).getDishProfit());
                tSalestableList.add(tSalestable1);
            }
        }
        for (int i = 0; i < tSalestableList.size(); i++) {
            tSalestableList.get(i).setDishProfitTotal(tSalestableList.get(i).getNumber() * tSalestableList.get(i).getDishProfit());
        }
        return tSalestableList;
    }

    @Override
    @DataScope(deptAlias = "ts")
    public List<TSalestable> selectTSalestableListById(Long id) {
        return tSalestableMapper.selectListById(id);
    }

    @Override
    @DataScope(deptAlias = "ts")
    public int addList(List<TSalestable> tSalestableList) {
        int fres = 0, res;
        for (int i = 0; i < tSalestableList.size(); i++) {
            List<TMenu> tMenus = tMenuMapper.selectTMenuListById(tSalestableList.get(i).getDishId());
            if (tMenus != null) {
                tSalestableList.get(i).setDishName(tMenus.get(0).getDishName());
                TFixPrice tFixPrice = new TFixPrice();
                tFixPrice.setPlateColor(tMenus.get(0).getPlateColor());
                List<TFixPrice> tFixPrices = tFixPriceMapper.selectTFixPriceListByPlateColor(tFixPrice);
                if (tFixPrices != null) {
                    tSalestableList.get(i).setPriceId(tFixPrices.get(0).getId());
                    List<TSalestable> tSalestables = tSalestableMapper.selectListByDishNameAndDate(tSalestableList.get(i));
                    if (tSalestables.size() == 0) {
                        TMenu tMenu = new TMenu();
                        tMenu.setDishName(tSalestableList.get(i).getDishName());
                        List<TMenu> tMenus1 = tMenuMapper.selectTMenuList(tMenu);
                        if (tMenus1.size() > 0) {
                            tSalestableList.get(i).setDishProfit(tMenus1.get(0).getDishProfit());
                        }
                        res = tSalestableMapper.add(tSalestableList.get(i));
                    } else {
                        TMenu tMenu = new TMenu();
                        tMenu.setDishName(tSalestableList.get(i).getDishName());
                        List<TMenu> tMenus1 = tMenuMapper.selectTMenuList(tMenu);
                        if (tMenus1.size() > 0) {
                            tSalestableList.get(i).setDishProfit(tMenus1.get(0).getDishProfit());
                        }
                        tSalestableList.get(i).setId(tSalestables.get(0).getId());
                        tSalestableList.get(i).setNumber(tSalestableList.get(i).getNumber() + tSalestables.get(0).getNumber());
                        res = tSalestableMapper.update(tSalestableList.get(i));
                    }
                    if (res == 0) {
                        fres++;
                    }
                } else {
                    fres = -1;
                }
            } else {
                fres = -1;
            }
        }
        return fres;
    }

    @Override
    @DataScope(deptAlias = "ts")
    public int add(TSalestable tSalestable) {
        List<TSalestable> tSalestables = tSalestableMapper.selectListByDishNameAndDate(tSalestable);
        TMenu tMenu = new TMenu();
        tMenu.setDishName(tSalestable.getDishName());
        List<TMenu> tMenus = tMenuMapper.selectTMenuList(tMenu);
        if (tMenus.size() > 0) {
            tSalestable.setDishProfit(tMenus.get(0).getDishProfit());
        }
        if (tSalestables.size() > 0) {
            return -1;
        } else {
            return tSalestableMapper.addHaveCreateTime(tSalestable);
        }
    }

    @Override
    public int edit(TSalestable tSalestable) {
        List<TSalestable> tSalestables = tSalestableMapper.selectListByDishNameAndDate(tSalestable);
        TMenu tMenu = new TMenu();
        tMenu.setDishName(tSalestable.getDishName());
        List<TMenu> tMenus = tMenuMapper.selectTMenuList(tMenu);
        if (tMenus.size() > 0) {
            tSalestable.setDishProfit(tMenus.get(0).getDishProfit());
        }
        if (tSalestables.size() > 0 && !tSalestables.get(0).getId().equals(tSalestable.getId())) {
            return -1;
        } else {
            return tSalestableMapper.edit(tSalestable);
        }
    }

    @Override
    public TSalestable remove(TSalestable tSalestable) {
        List<Long> resByIdsList = new ArrayList<>();
        TSalestable tSalestable1 = new TSalestable();
        if (tSalestable.getId() != null) {
            int res = tSalestableMapper.remove(tSalestable);
            if (res == 0) {
                resByIdsList.add(tSalestable.getId());
                tSalestable1.setIds(resByIdsList);
                return tSalestable1;
            } else {
                tSalestable1.setIds(resByIdsList);
                return tSalestable1;
            }
        } else if (!tSalestable.getIds().isEmpty()) {
            int resById;
            for (int i = 0; i < tSalestable.getIds().size(); i++) {
                resById = tSalestableMapper.removeByIds(tSalestable.getIds().get(i));
                if (resById == 0) {
                    resByIdsList.add(tSalestable.getIds().get(i));
                }
            }
            tSalestable1.setIds(resByIdsList);
            return tSalestable1;
        } else {
            resByIdsList.add(-1L);
            tSalestable1.setIds(resByIdsList);
            return tSalestable1;
        }
    }

    @Override
    public int dayPayrollCountParams() {
        List<TSalestable> tSalestables = tSalestableMapper.selectListByDate(DateUtils.getDate());
        int dishscount = 0;
        if (tSalestables.size() > 0) {
            for (int i = 0; i < tSalestables.size(); i++) {
                dishscount = dishscount + tSalestables.get(i).getNumber();
            }
        }
        TPerformance tPerformance = new TPerformance();
        List<TPerformance> tPerformances = itPerformanceService.list(tPerformance);
        double payroll = 0;
        for (int i = 0; i < tPerformances.size(); i++) {
            if (dishscount > tPerformances.get(i).getPerformancePosition()) {
                payroll = tPerformances.get(i).getPerformancePay();
            }
        }
        List<TAttendancesheet> tAttendancesheets = itCheckInService.selectCheckInByDate(DateUtils.getDate());
        /**
         * 因为懒:
         * 借用 Lit<tAttendancesheet> tAttendancesheets1存储上班下班打卡的用户,attendanceType当上班状态（0：未签到，1：签到），
         * isState当下班状态（0：未签到，1：签到）
         */
        List<TAttendancesheet> tAttendancesheets1 = new ArrayList<>();
        if (tSalestables.size() > 0) {
            for (int i = 0; i < tAttendancesheets.size(); i++) {
                int user = 0;
                for (int j = 0; j < tAttendancesheets1.size(); j++) {
                    if (Objects.equals(tAttendancesheets.get(i).getUserId(), tAttendancesheets1.get(j).getUserId())) {
                        user = 1;
                        if (tAttendancesheets.get(i).getAttendanceType() == 0) {
                            tAttendancesheets1.get(j).setAttendanceType(1);
                        } else if (tAttendancesheets.get(i).getAttendanceType() == 1) {
                            tAttendancesheets1.get(j).setIsState(1);
                        }

                    }
                }
                if (user == 0) {
                    TAttendancesheet tAttendancesheet = new TAttendancesheet();
                    tAttendancesheet.setAttendanceType(0);
                    tAttendancesheet.setIsState(0);
                    tAttendancesheet.setUserName(tAttendancesheets.get(i).getUserName());
                    tAttendancesheet.setUserId(tAttendancesheets.get(i).getUserId());
                    if (tAttendancesheets.get(i).getAttendanceType() == 0) {
                        tAttendancesheet.setAttendanceType(1);
                    } else if (tAttendancesheets.get(i).getAttendanceType() == 1) {
                        tAttendancesheet.setIsState(1);
                    }
                    tAttendancesheets1.add(tAttendancesheet);
                }
            }
            if (tAttendancesheets1.size() == 0) {
                return -2;
            }
            int res = 0;
            for (int i = 0; i < tAttendancesheets1.size(); i++) {
                if (tAttendancesheets1.get(i).getAttendanceType() == 1 && tAttendancesheets1.get(i).getIsState() == 1) {
                    TPayroll tPayroll = new TPayroll();
                    tPayroll.setUserId(tAttendancesheets1.get(i).getUserId());
                    tPayroll.setPayroll(payroll);
                    tPayroll.setUserName(tAttendancesheets1.get(i).getUserName());
                    tPayroll.setIsState(1);
                    tPayroll.setCreateTime(new Date());
                    TDay tDay = new TDay();
                    tDay.setStartDay(DateUtils.getDate());
                    tDay.setEndDay(DateUtils.getDate());
                    tDay.setUserName(tAttendancesheets1.get(i).getUserName());
                    List<TPayroll> tPayrolls = tPayrollMapper.selectTPayrollListByDay(tDay);
                    if (tPayrolls.size() > 0) {
                        tPayroll.setId(tPayrolls.get(0).getId());
                        if (tPayrollMapper.edit(tPayroll) == 0) {
                            res++;
                        }
                    } else {
                        if (tPayrollMapper.add(tPayroll) == 0) {
                            res++;
                        }
                    }
                }
            }
            return res;
        } else {
            return -1;
        }
    }
}
