package com.ruoyi.system.service;

import com.ruoyi.system.domain.TSalestable;

import java.util.List;

public interface ITSalestableService {

    public List<TSalestable> list(TSalestable tSalestable);

    public List<TSalestable> selectTSalestableListById(Long id);

    public int addList(List<TSalestable> tSalestableList);

    public int add(TSalestable tSalestable);

    public int edit(TSalestable tSalestable);

    public TSalestable remove(TSalestable tSalestable);

    public int dayPayrollCountParams();
}
