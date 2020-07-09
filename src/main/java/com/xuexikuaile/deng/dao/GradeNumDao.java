package com.xuexikuaile.deng.dao;

import com.xuexikuaile.deng.pojo.GradeNum;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GradeNumDao {
    int deleteByPrimaryKey(Integer gradeNumId);

    int insert(GradeNum record);

    int insertSelective(GradeNum record);

    GradeNum selectByPrimaryKey(Integer gradeNumId);

    int updateByPrimaryKeySelective(GradeNum record);

    int updateByPrimaryKey(GradeNum record);

    int getByGradeName(String name);
}