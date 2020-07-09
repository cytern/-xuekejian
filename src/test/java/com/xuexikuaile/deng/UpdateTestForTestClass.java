package com.xuexikuaile.deng;

import com.xuexikuaile.deng.dao.*;
import com.xuexikuaile.deng.plugin.ChangeDate;
import com.xuexikuaile.deng.pojo.SClass;
import com.xuexikuaile.deng.pojo.STest;
import com.xuexikuaile.deng.pojo.STestClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/6/13 17:48
 */
@SpringBootTest
public class UpdateTestForTestClass {

    @Autowired
    private STestDao sTestDao;
    @Autowired
    private STestClassDao sTestClassDao;
    @Autowired
    private SGradeDao sGradeDao;
    @Autowired
    private SStudentDao sStudentDao;
    @Autowired
    private SClassDao sClassDao;
    ChangeDate changeDate = new ChangeDate();
    String[] year = {"2018年","2019年","2020年"};
    String[] test = {"上学年第一次月考","上学年期中考试","上学年第二次月考","上学年期末考试","下学年第一次月考","下学年期中考试","下学年第二次月考","下学年期末考试"};
    @Test
    void  update(){
      addTestClass();
    }

    public void  addTestClass(){
        List<STest> sTests = sTestDao.getAllTest();
        List<SClass> sClasses = sClassDao.getAllClass();
        for (STest sTest:sTests){
            if (sTest.getTestName().contains("2018级")){
                System.out.println("2018级");
                for (SClass sClass:sClasses){
                    if (sClass.getClassNoName().contains("高(三)")){
                        STestClass sTestClass = new STestClass();
                        sTestClass.setTestId(sTest.getSTestId());
                        sTestClass.setClassId(sClass.getSClassId());
                        sTestClass.setCTime(changeDate.getDate());
                        sTestClassDao.insertSelective(sTestClass);
                    }
                }
            }
            if (sTest.getTestName().contains("2019级")){
                System.out.println("2019级");
                for (SClass sClass:sClasses){
                    if (sClass.getClassNoName().contains("高(二)")){
                        STestClass sTestClass = new STestClass();
                        sTestClass.setTestId(sTest.getSTestId());
                        sTestClass.setClassId(sClass.getSClassId());
                        sTestClass.setCTime(changeDate.getDate());
                       sTestClassDao.insertSelective(sTestClass);
                    }
                }
            }
            if (sTest.getTestName().contains("2020级")){
                System.out.println("2020级");
                for (SClass sClass:sClasses){
                    if (sClass.getClassNoName().contains("高(一)")){
                        STestClass sTestClass = new STestClass();
                        sTestClass.setTestId(sTest.getSTestId());
                        sTestClass.setClassId(sClass.getSClassId());
                        sTestClass.setCTime(changeDate.getDate());
                        sTestClassDao.insertSelective(sTestClass);
                    }
                }
            }
        }
    }
    public void addTest(){
        for (int a =2;a<year.length;a++){
            for (int b = 0;b<test.length;b++){
                STest sTest = new STest();
                sTest.setTestName("2020级"+ year[a] + test[b]);
                sTest.setTestType("total");
                sTest.setTestStartUserId(1);
                sTest.setCTime(changeDate.getDate());
                sTestDao.insertSelective(sTest);
            }
        }
    }

}
