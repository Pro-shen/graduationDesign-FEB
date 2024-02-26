package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.TMonthPayroll;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.TMonthPayrollMapper;
import com.ruoyi.system.service.ITMonthPayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ITMonthPayrollServiceImpl implements ITMonthPayrollService {
    @Autowired
    private TMonthPayrollMapper tMonthPayrollMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @DataScope(deptAlias = "tmp")
    public List<TMonthPayroll> list(TMonthPayroll tMonthPayroll) {
        return tMonthPayrollMapper.selectTMonthPayrollList(tMonthPayroll);
    }

    @Override
    public int add(TMonthPayroll tMonthPayroll) {
        SysUser sysUser = sysUserMapper.selectUserByUserName(tMonthPayroll.getUserName());
        if (sysUser != null) {
            tMonthPayroll.setUserId(sysUser.getUserId());
            List<TMonthPayroll> tMonthPayrolls = tMonthPayrollMapper.selectTMonthPayrollListByUserNameAndCreateTime(tMonthPayroll);
            if (tMonthPayrolls.size() > 0) {
                return -1;
            } else {
                return tMonthPayrollMapper.add(tMonthPayroll);
            }
        } else {
            return -2;
        }
    }

    @Override
    public List<TMonthPayroll> selectTMonthPayrollListById(Long id) {
        return tMonthPayrollMapper.selectTMonthPayrollListById(id);
    }

    @Override
    public int edit(TMonthPayroll tMonthPayroll) {
        List<TMonthPayroll> tMonthPayrolls = tMonthPayrollMapper.selectTMonthPayrollListByUserNameAndCreateTime(tMonthPayroll);
        if (tMonthPayrolls.size() > 0 && !Objects.equals(tMonthPayrolls.get(0).getId(), tMonthPayroll.getId())) {
            return -2;
        } else {
            SysUser sysUser = sysUserMapper.selectUserByUserName(tMonthPayroll.getUserName());
            if (sysUser != null) {
                tMonthPayroll.setUserId(sysUser.getUserId());
                return tMonthPayrollMapper.edit(tMonthPayroll);
            } else {
                return -1;
            }
        }
    }

    @Override
    public TMonthPayroll remove(TMonthPayroll tMonthPayroll) {
        List<Long> resByIdsList = new ArrayList<>();
        TMonthPayroll tMonthPayroll1 = new TMonthPayroll();
        if(tMonthPayroll.getId() != null){
            int res = tMonthPayrollMapper.remove(tMonthPayroll);
            if (res == 0) {
                resByIdsList.add(tMonthPayroll.getId());
                tMonthPayroll1.setIds(resByIdsList);
                return tMonthPayroll1;
            } else {
                tMonthPayroll1.setIds(resByIdsList);
                return tMonthPayroll1;
            }
        }else if (!tMonthPayroll.getIds().isEmpty()) {
            int resById;
            for (int i = 0; i < tMonthPayroll.getIds().size(); i++) {
                resById = tMonthPayrollMapper.removeByIds(tMonthPayroll.getIds().get(i));
                if (resById == 0) {
                    resByIdsList.add(tMonthPayroll.getIds().get(i));
                }
            }
            tMonthPayroll1.setIds(resByIdsList);
            return tMonthPayroll1;
        } else {
            resByIdsList.add(-1L);
            tMonthPayroll1.setIds(resByIdsList);
            return tMonthPayroll1;
        }
    }
}
