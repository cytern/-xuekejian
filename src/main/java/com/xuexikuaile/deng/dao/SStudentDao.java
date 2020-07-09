package com.xuexikuaile.deng.dao;

import com.xuexikuaile.deng.pojo.SStudent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SStudentDao {
    int deleteByPrimaryKey(Integer sStudentId);

    int insert(SStudent record);

    int insertSelective(SStudent record);

    SStudent selectByPrimaryKey(Integer sStudentId);

    int updateByPrimaryKeySelective(SStudent record);

    int updateByPrimaryKey(SStudent record);

    SStudent getStudentByUserId(Integer userId);

    List<SStudent> getAllStudent();
    List<SStudent> getStudentByClassId(Integer classId);

    int getNumOfOneClass(Integer classId);
}