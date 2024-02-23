package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TSalestable;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface TSalestableMapper {

    public List<TSalestable> list(TSalestable tSalestable);

    public List<TSalestable> selectListByDishNameAndDate(TSalestable tSalestable);

    public List<TSalestable> selectListById(@Param("id")Long id);

    public int addList(List<TSalestable> tSalestableList);

    public int add(TSalestable tSalestable);

    public int addHaveCreateTime(TSalestable tSalestable);

    public int update(TSalestable tSalestable);

    public int edit(TSalestable tSalestable);

    public int remove(TSalestable tSalestable);

    public int removeByIds(@Param("id")Long id);


}
