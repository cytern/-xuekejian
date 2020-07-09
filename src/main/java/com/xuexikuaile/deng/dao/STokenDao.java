package com.xuexikuaile.deng.dao;

import com.xuexikuaile.deng.pojo.SToken;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface STokenDao {
    int deleteByPrimaryKey(Integer sTokenId);

    int insert(SToken record);

    int insertSelective(SToken record);

    SToken selectByPrimaryKey(Integer sTokenId);

    int updateByPrimaryKeySelective(SToken record);

    int updateByPrimaryKey(SToken record);

    SToken getTokenByToken(String token);
}