package com.xuexikuaile.deng;

import com.xuexikuaile.deng.dao.SClassTeacherDao;
import com.xuexikuaile.deng.dao.STeacherDao;
import com.xuexikuaile.deng.dao.SUserDao;
import com.xuexikuaile.deng.pojo.SClass;
import com.xuexikuaile.deng.pojo.SClassTeacher;
import com.xuexikuaile.deng.pojo.STeacher;
import com.xuexikuaile.deng.pojo.SUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/6/10 20:57
 */
@SpringBootTest
public class addTeachersTest {
    @Autowired
    GetTeacheres getTeacheres = new GetTeacheres();
    @Autowired
    private STeacherDao sTeacherDao;
    @Autowired
    private SClassTeacherDao sClassTeacherDao;
    @Autowired
    private SUserDao sUserDao;
    @Test
    void contextLoads() throws InterruptedException {
    List<STeacher> teachers = getTeacheres.getTeachers();
    for (STeacher sTeacher:teachers){
        SUser sUser =new SUser();
        sUser.setCTime(new Date());
        sUser.setUserType("teacher");
        sUser.setUserName(String.valueOf(sTeacher.hashCode()));
        sUser.setUserPassword(String.valueOf(sTeacher.hashCode()));
        sUser.setCTime(new Date());
        sUserDao.insertSelective(sUser);
        Thread.currentThread().sleep(100);
        sTeacher.setUserId(sUser.getUserId());
        sTeacherDao.insertSelective(sTeacher);
        Thread.currentThread().sleep(100);
        SClassTeacher sClassTeacher =new SClassTeacher();
        sClassTeacher.setCTime(new Date());
        sClassTeacher.setClassId(Integer.valueOf(sTeacher.getTeacherUrl()));
        sClassTeacher.setTeacherId(sTeacher.getSTeacherId());
        sClassTeacher.setUTime(new Date());
        sClassTeacherDao.insertSelective(sClassTeacher);
        Thread.currentThread().sleep(100);
    }


    }
}
