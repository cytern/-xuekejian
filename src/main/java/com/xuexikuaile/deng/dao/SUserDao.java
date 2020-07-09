package com.xuexikuaile.deng.dao;

import com.xuexikuaile.deng.pojo.SUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SUserDao {
    int deleteByPrimaryKey(Integer userId);

    int insert(SUser record);

    int insertSelective(SUser record);

    SUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(SUser record);

    int updateByPrimaryKey(SUser record);

    SUser getUserByUserName(String userName);

    List<SUser> getAllUsers();

    List<SUser> getAllStudent();
}