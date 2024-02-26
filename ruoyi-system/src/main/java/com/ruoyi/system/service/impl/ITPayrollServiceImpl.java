package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.TDay;
import com.ruoyi.system.domain.TMonthPayroll;
import com.ruoyi.system.domain.TPayroll;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.TMenuMapper;
import com.ruoyi.system.mapper.TMonthPayrollMapper;
import com.ruoyi.system.mapper.TPayrollMapper;
import com.ruoyi.system.service.ITPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ITPayrollServiceImpl implements ITPayrollService {

    @Autowired
    private TPayrollMapper tPayrollMapper;

    @Autowired
    private TMonthPayrollMapper tMonthPayrollMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @DataScope(deptAlias = "tp")
    public List<TPayroll> list(TPayroll tPayroll) {
        return tPayrollMapper.selectTPayrollList(tPayroll);
    }

    @Override
    public int add(TPayroll tPayroll) {
        SysUser sysUser = sysUserMapper.selectUserByUserName(tPayroll.getUserName());
        if (sysUser != null) {
            tPayroll.setUserId(sysUser.getUserId());
            List<TPayroll> tPayrolls = tPayrollMapper.selectTPayrollListByUserNameAndCreateTime(tPayroll);
            if (tPayrolls.size() > 0) {
                return -1;
            } else {
                return tPayrollMapper.add(tPayroll);
            }
        } else {
            return -2;
        }
    }

    @Override
    public List<TPayroll> selectTPayrollListById(Long id) {
        return tPayrollMapper.selectTPayrollListById(id);
    }

    @Override
    public int selectTPayrollListByDay() {
        TDay tDay = new TDay();
        tDay.setStartDay(DateUtils.getFirstDay());
        tDay.setEndDay(DateUtils.getLastDay());
        List<TPayroll> tPayrolls = tPayrollMapper.selectTPayrollListByDay(tDay);
        List<TMonthPayroll> tMonthPayrolls = new ArrayList<>();
        int newItemFlag;
        for (int i = 0; i < tPayrolls.size(); i++) {
            newItemFlag = 0;
            for (int j = 0; j < tMonthPayrolls.size(); j++) {
                if (tPayrolls.get(i).getUserId() == tMonthPayrolls.get(j).getUserId()) {
                    newItemFlag = 1;
                    tMonthPayrolls.get(j).setPayroll(tMonthPayrolls.get(j).getPayroll() + tPayrolls.get(i).getPayroll());
                    break;
                }
            }
            if (newItemFlag == 0) {
                TMonthPayroll tMonthPayroll = new TMonthPayroll();
                tMonthPayroll.setUserId(tPayrolls.get(i).getUserId());
                tMonthPayroll.setPayroll(tPayrolls.get(i).getPayroll());
                tMonthPayroll.setIsState(tPayrolls.get(i).getIsState());
                tMonthPayroll.setUserName(tPayrolls.get(i).getUserName());
                tMonthPayroll.setCreateTime(DateUtils.getFirstDayByDate());
                tMonthPayrolls.add(tMonthPayroll);
            }
        }
        int res = 0;
        for (int i = 0; i < tMonthPayrolls.size(); i++) {
            List<TMonthPayroll> tMonthPayrolls1 = tMonthPayrollMapper.selectTMonthPayrollList(tMonthPayrolls.get(i));
            if (tMonthPayrolls1.size() > 0) {
                tMonthPayrolls.get(i).setId(tMonthPayrolls1.get(0).getId());
                if (tMonthPayrollMapper.edit(tMonthPayrolls.get(i)) == 0) {
                    res++;
                }
            } else {
                if (tMonthPayrollMapper.add(tMonthPayrolls.get(i)) == 0) {
                    res++;
                }
            }
        }
        return res;
    }

    @Override
    public int edit(TPayroll tPayroll) {
        List<TPayroll> tPayrolls = tPayrollMapper.selectTPayrollListByUserNameAndCreateTime(tPayroll);
        if (tPayrolls.size() > 0 && !Objects.equals(tPayrolls.get(0).getId(), tPayroll.getId())) {
            return -2;
        } else {
            SysUser sysUser = sysUserMapper.selectUserByUserName(tPayroll.getUserName());
            if (sysUser != null) {
                tPayroll.setUserId(sysUser.getUserId());
                return tPayrollMapper.edit(tPayroll);
            } else {
                return -1;
            }
        }
    }

    @Override
    public TPayroll remove(TPayroll tPayroll) {
        List<Long> resByIdsList = new ArrayList<>();
        TPayroll tPayroll1 = new TPayroll();
        if (tPayroll.getId() != null) {
            int res = tPayrollMapper.remove(tPayroll);
            if (res == 0) {
                resByIdsList.add(tPayroll.getId());
                tPayroll1.setIds(resByIdsList);
                return tPayroll1;
            } else {
                tPayroll1.setIds(resByIdsList);
                return tPayroll1;
            }
        } else if (!tPayroll.getIds().isEmpty()) {
            int resById;
            for (int i = 0; i < tPayroll.getIds().size(); i++) {
                resById = tPayrollMapper.removeByIds(tPayroll.getIds().get(i));
                if (resById == 0) {
                    resByIdsList.add(tPayroll.getIds().get(i));
                }
            }
            tPayroll1.setIds(resByIdsList);
            return tPayroll1;
        } else {
            resByIdsList.add(-1L);
            tPayroll1.setIds(resByIdsList);
            return tPayroll1;
        }
    }


}
