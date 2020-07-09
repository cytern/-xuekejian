package com.xuexikuaile.deng.dao;

import com.xuexikuaile.deng.pojo.STeacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface STeacherDao {
    int deleteByPrimaryKey(Integer sTeacherId);

    int insert(STeacher record);

    int insertSelective(STeacher record);

    STeacher selectByPrimaryKey(Integer sTeacherId);

    int updateByPrimaryKeySelective(STeacher record);

    int updateByPrimaryKey(STeacher record);

    STeacher getTeacherByUserId(Integer userId);

    int insertTeacherList(List<STeacher> record);
}