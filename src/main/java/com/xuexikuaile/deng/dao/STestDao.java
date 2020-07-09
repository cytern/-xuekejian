package com.xuexikuaile.deng.dao;

import com.xuexikuaile.deng.pojo.STest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface STestDao {
    int deleteByPrimaryKey(Integer sTestId);

    int insert(STest record);

    int insertSelective(STest record);

    STest selectByPrimaryKey(Integer sTestId);

    int updateByPrimaryKeySelective(STest record);

    int updateByPrimaryKey(STest record);

    List<STest> getAllTest();

}