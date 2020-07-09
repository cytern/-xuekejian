package com.xuexikuaile.deng.dao;

import com.xuexikuaile.deng.pojo.STestClass;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface STestClassDao {
    int deleteByPrimaryKey(Integer testClassId);

    int insert(STestClass record);

    int insertSelective(STestClass record);

    STestClass selectByPrimaryKey(Integer testClassId);

    int updateByPrimaryKeySelective(STestClass record);

    int updateByPrimaryKey(STestClass record);

    List<STestClass> getByTestId(Integer testId);

    List<STestClass> getAll();
}