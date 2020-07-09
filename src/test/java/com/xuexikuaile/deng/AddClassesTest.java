package com.xuexikuaile.deng;

import com.xuexikuaile.deng.dao.SClassDao;
import com.xuexikuaile.deng.dao.SUserDao;
import com.xuexikuaile.deng.pojo.SClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/6/10 20:55
 */
@SpringBootTest
public class AddClassesTest {
    @Autowired
    private SUserDao sUserDao;
    @Autowired
    addClass addClass = new addClass();
    @Autowired
    private SClassDao sClassDao;


    @Test
    void contextLoads() {
        List<SClass> sClasses = addClass.getClasses();
        int i = sClassDao.insertSelectiveList(sClasses);
        System.out.println(i);
    }

}
