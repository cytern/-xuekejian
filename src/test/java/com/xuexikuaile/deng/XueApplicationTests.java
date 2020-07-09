package com.xuexikuaile.deng;

import com.xuexikuaile.deng.dao.SClassDao;
import com.xuexikuaile.deng.dao.SClassTeacherDao;
import com.xuexikuaile.deng.dao.SUserDao;
import com.xuexikuaile.deng.pojo.SClass;
import com.xuexikuaile.deng.pojo.SClassTeacher;
import com.xuexikuaile.deng.pojo.SUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class XueApplicationTests {
@Autowired
private SClassTeacherDao sClassTeacherDao;
    @Test
    void contextLoads() {
        SClassTeacher sClassTeacher =new SClassTeacher();
        for (int i =1;i<541;i++){
            sClassTeacher.setSClassTeacherId(i);
            sClassTeacher.setTeacherId(540 + i);
            sClassTeacherDao.updateByPrimaryKeySelective(sClassTeacher);
        }
    }

}
