package com.xuexikuaile.deng;

import com.xuexikuaile.deng.dao.SStudentDao;
import com.xuexikuaile.deng.dao.SUserDao;
import com.xuexikuaile.deng.plugin.ChangeDate;
import com.xuexikuaile.deng.pojo.SStudent;
import com.xuexikuaile.deng.pojo.SUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/6/10 21:28
 */
@SpringBootTest
public class AddStudentTest {
    @Autowired
    GetStudents getStudents =new GetStudents();
    ChangeDate changeDate =new ChangeDate();
    @Autowired
    private SUserDao sUserDao;
    @Autowired
    private SStudentDao sStudentDao;
    @Test
    void addStudentTest() throws InterruptedException {
        List<SStudent> list = getStudents.getStudents();
       for (SStudent sStudent:list){
           SUser sUser =new SUser();
           sUser.setUserType("student");
           sUser.setUserPassword(String.valueOf(sUser.hashCode()));
           sUser.setUserName(String.valueOf(sUser.hashCode()));
           sUser.setCTime(changeDate.getDate());
           sUser.setUTime(changeDate.getDate());
          sUserDao.insertSelective(sUser);
           Thread.currentThread().sleep(100);
          sStudent.setUserId(sUser.getUserId());

          sStudentDao.insertSelective(sStudent);
           Thread.currentThread().sleep(100);
       }
    }

}
