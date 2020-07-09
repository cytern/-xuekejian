package com.xuexikuaile.deng.dao;

import com.xuexikuaile.deng.pojo.SChat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SChatDao {
    int deleteByPrimaryKey(Integer sChatId);

    int insert(SChat record);

    int insertSelective(SChat record);

    SChat selectByPrimaryKey(Integer sChatId);

    int updateByPrimaryKeySelective(SChat record);

    int updateByPrimaryKey(SChat record);
}