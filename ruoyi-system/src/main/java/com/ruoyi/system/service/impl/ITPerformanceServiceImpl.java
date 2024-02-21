package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.system.domain.TPerformance;
import com.ruoyi.system.mapper.TPerformanceMapper;
import com.ruoyi.system.service.ITPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ITPerformanceServiceImpl implements ITPerformanceService {

    @Autowired
    private TPerformanceMapper tPerformanceMapper;
    @Override
    @DataScope(deptAlias = "tp")
    public List<TPerformance> list(TPerformance tPerformance) {
        return tPerformanceMapper.list(tPerformance);
    }

    @Override
    @DataScope(deptAlias = "tp")
    public int add(TPerformance tPerformance) {
        List<TPerformance> tPerformances = tPerformanceMapper.selectTPerformanceListByPerformancePosition(tPerformance);
        if(tPerformances == null){
            return -1;
        }else {
            return tPerformanceMapper.add(tPerformance);
        }
    }

    @Override
    public List<TPerformance> selectTPerformanceListById(Long id) {
        return tPerformanceMapper.selectTPerformanceListById(id);
    }

    @Override
    public int edit(TPerformance tPerformance) {
        return tPerformanceMapper.edit(tPerformance);
    }

    @Override
    public TPerformance remove(TPerformance tPerformance) {
        List<Long> resByIdsList = new ArrayList<>();
        TPerformance tPerformance1 = new TPerformance();
        if(tPerformance.getId()!=null){
            int res = tPerformanceMapper.remove(tPerformance);
            if(res == 0){
                resByIdsList.add(tPerformance.getId());
                tPerformance1.setIds(resByIdsList);
                return tPerformance1;
            }else {
                tPerformance1.setIds(resByIdsList);
                return tPerformance1;
            }
        }else if(tPerformance.getIds() != null){
            int resById;
            for(int i = 0;i<tPerformance.getIds().size();i++){
                resById = tPerformanceMapper.removeByIds(tPerformance.getIds().get(i));
                if(resById == 0){
                    resByIdsList.add(tPerformance.getIds().get(i));
                }
            }
            tPerformance1.setIds(resByIdsList);
            return tPerformance1;
        }else{
            resByIdsList.add(-1L);
            tPerformance1.setIds(resByIdsList);
            return tPerformance1;
        }
    }
}
