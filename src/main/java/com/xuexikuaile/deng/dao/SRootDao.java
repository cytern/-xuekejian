package com.xuexikuaile.deng.dao;

import com.xuexikuaile.deng.pojo.SRoot;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SRootDao {
    int deleteByPrimaryKey(Integer sRootId);

    int insert(SRoot record);

    int insertSelective(SRoot record);

    SRoot selectByPrimaryKey(Integer sRootId);

    int updateByPrimaryKeySelective(SRoot record);

    int updateByPrimaryKey(SRoot record);

    SRoot getRootByUserId(Integer userId);
}