package com.xuexikuaile.deng.dao;

import com.xuexikuaile.deng.pojo.SClassTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SClassTeacherDao {
    int deleteByPrimaryKey(Integer sClassTeacherId);

    int insert(SClassTeacher record);

    int insertSelective(SClassTeacher record);

    SClassTeacher selectByPrimaryKey(Integer sClassTeacherId);

    int updateByPrimaryKeySelective(SClassTeacher record);

    int updateByPrimaryKey(SClassTeacher record);
}