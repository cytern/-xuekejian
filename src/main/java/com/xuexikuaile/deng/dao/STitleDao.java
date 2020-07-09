package com.xuexikuaile.deng.dao;

import com.xuexikuaile.deng.pojo.STitle;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface STitleDao {
    int deleteByPrimaryKey(Integer sTitleId);

    int insert(STitle record);

    int insertSelective(STitle record);

    STitle selectByPrimaryKey(Integer sTitleId);

    int updateByPrimaryKeySelective(STitle record);

    int updateByPrimaryKey(STitle record);
}