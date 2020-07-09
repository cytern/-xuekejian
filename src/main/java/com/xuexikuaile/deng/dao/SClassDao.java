package com.xuexikuaile.deng.dao;

import com.xuexikuaile.deng.pojo.SClass;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface SClassDao {
    int insert(SClass record);

    int insertSelective(SClass record);


    int insertSelectiveList(List<SClass> sClasses);

    List<SClass> getAllClass();

    SClass getById(Integer classId);

}