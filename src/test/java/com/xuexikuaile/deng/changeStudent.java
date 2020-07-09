package com.xuexikuaile.deng;

import com.xuexikuaile.deng.dao.SUserDao;
import com.xuexikuaile.deng.pojo.SUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/7/1 13:43
 */
@SpringBootTest
public class changeStudent {
    @Autowired
   private SUserDao sUserDao;

    @Test
    void changeStudent(){
        List<SUser> sUsers = sUserDao.getAllStudent();
        for (SUser sUser:sUsers){
            Date date = new Date();
            sUser.setUserName((String.valueOf(date.getTime())));
            sUser.setUserPassword(String.valueOf(date.getTime()));
            sUser.setUTime(date);
            sUserDao.updateByPrimaryKeySelective(sUser);
        }
    }
}
