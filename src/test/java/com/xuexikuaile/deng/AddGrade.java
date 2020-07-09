package com.xuexikuaile.deng;

import com.xuexikuaile.deng.dao.*;
import com.xuexikuaile.deng.plugin.ChangeDate;
import com.xuexikuaile.deng.pojo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/6/11 12:13
 */
@SpringBootTest
public class AddGrade {
    @Autowired
    GetGrade getGrade = new GetGrade();
    @Autowired
    private SStudentDao sStudentDao;
    @Autowired
    private STestDao sTestDao;
    @Autowired
    private STestClassDao sTestClassDao;
    @Autowired
    private SClassDao sClassDao;
    @Autowired
    private SGradeDao sGradeDao;

    ChangeDate changeDate =new ChangeDate();

    @Test
    void addTest() throws InterruptedException {
        List<SClass> sClasses = sClassDao.getAllClass();
        String[] year = {"2018年","2019年","2020年"};
        String[] test = {"上学年第一次月考","上学年期中考试","上学年第二次月考","上学年期末考试","下学年第一次月考","下学年期中考试","下学年第二次月考","下学年期末考试"};
        for (SClass sClass:sClasses){
            String className = sClass.getClassNoName();
            if (className.indexOf("高(一)") > -1){
                for (int i=2;i<3;i++){
                    for (int a=0;a<test.length;a++){
                        String testName = year[i] + test[a];
                        String testYear = year[i];
                        addTests001(testName,sClass,testYear);
                    }
                }

            }else if (className.indexOf("高(二)")>-1){
                for (int i=1;i<3;i++){
                    for (int a=0;a<test.length;a++){
                        String testName = year[i] + test[a];
                        String testYear = year[i];
                        addTests001(testName,sClass,testYear);
                    }
                }

            }else if (className.indexOf("高(三)")>-1){
                for (int i=0;i<3;i++){
                    for (int a=0;a<test.length;a++){
                        String testName = year[i] + test[a];
                        String testYear = year[i];
                        addTests001(testName,sClass,testYear);
                    }
                }
            }
        }

    }

    public void addTests001(String testName,SClass sClass,String testYear) throws InterruptedException {
        STest sTest = new STest();
        sTest.setCTime(changeDate.getDate());
        sTest.setTestName(testName);
        sTest.setTestTime(null);
        sTest.setTestStartUserId(1);
        sTestDao.insertSelective(sTest);
        Thread.currentThread().sleep(100);
        STestClass sTestClass = new STestClass();
        sTestClass.setCTime(changeDate.getDate());
        sTestClass.setClassId(sClass.getSClassId());
        sTestClass.setTestId(sTest.getSTestId());
        sTestClassDao.insertSelective(sTestClass);
        Thread.currentThread().sleep(100);

        List<SStudent> sStudents = sStudentDao.getStudentByClassId(sClass.getSClassId());
        for (SStudent sStudent :sStudents){
            SGrade sGrade =new SGrade();
            sGrade.setStudentId(sStudent.getSStudentId());
            sGrade.setTestId(sTest.getSTestId());
            sGrade.setGradeStatus("success");
           if (testYear.contains("2020")){
               if (sClass.getClassNoName().contains("高(一)")){
                   sGrade.setGBiology(getGrade.get100Score());
                   sGrade.setGChemistry(getGrade.get100Score());
                   sGrade.setGChinese(getGrade.get150Score());
                   sGrade.setGEnglish(getGrade.get150Score());
                   sGrade.setGGeography(getGrade.get100Score());
                   sGrade.setGHistory(getGrade.get100Score());
                   sGrade.setGMath(getGrade.get150Score());
                   sGrade.setGPolitics(getGrade.get100Score());
                   sGrade.setGPysics(getGrade.get100Score());
               }else if (sClass.getClassNoName().contains("高(二)")){
                   if (sClass.getClassNoName().contains("高(二)一")){
                       sGrade.setGBiology(getGrade.get100Score());
                       sGrade.setGChemistry(getGrade.get100Score());
                       sGrade.setGChinese(getGrade.get150Score());
                       sGrade.setGEnglish(getGrade.get150Score());


                       sGrade.setGMath(getGrade.get150Score());

                       sGrade.setGPysics(getGrade.get100Score());
                   }else{

                       sGrade.setGChinese(getGrade.get150Score());
                       sGrade.setGEnglish(getGrade.get150Score());
                       sGrade.setGGeography(getGrade.get100Score());
                       sGrade.setGHistory(getGrade.get100Score());
                       sGrade.setGMath(getGrade.get150Score());
                       sGrade.setGPolitics(getGrade.get100Score());

                   }
               }else if (sClass.getClassNoName().contains("高(三)")){
                   if (sClass.getClassNoName().contains("高(三)一")){
                       sGrade.setGBiology(getGrade.get100Score());
                       sGrade.setGChemistry(getGrade.get100Score());
                       sGrade.setGChinese(getGrade.get150Score());
                       sGrade.setGEnglish(getGrade.get150Score());


                       sGrade.setGMath(getGrade.get150Score());

                       sGrade.setGPysics(getGrade.get100Score());
                   }else{

                       sGrade.setGChinese(getGrade.get150Score());
                       sGrade.setGEnglish(getGrade.get150Score());
                       sGrade.setGGeography(getGrade.get100Score());
                       sGrade.setGHistory(getGrade.get100Score());
                       sGrade.setGMath(getGrade.get150Score());
                       sGrade.setGPolitics(getGrade.get100Score());

                   }
               }
           }else if (testYear.contains("2019")){
               if (sClass.getClassNoName().contains("高(二)")){
                   sGrade.setGBiology(getGrade.get100Score());
                   sGrade.setGChemistry(getGrade.get100Score());
                   sGrade.setGChinese(getGrade.get150Score());
                   sGrade.setGEnglish(getGrade.get150Score());
                   sGrade.setGGeography(getGrade.get100Score());
                   sGrade.setGHistory(getGrade.get100Score());
                   sGrade.setGMath(getGrade.get150Score());
                   sGrade.setGPolitics(getGrade.get100Score());
                   sGrade.setGPysics(getGrade.get100Score());
               }else {
                 if (sClass.getClassNoName().contains("高(三)一")){
                     sGrade.setGBiology(getGrade.get100Score());
                     sGrade.setGChemistry(getGrade.get100Score());
                     sGrade.setGChinese(getGrade.get150Score());
                     sGrade.setGEnglish(getGrade.get150Score());


                     sGrade.setGMath(getGrade.get150Score());

                     sGrade.setGPysics(getGrade.get100Score());
                 }else{

                     sGrade.setGChinese(getGrade.get150Score());
                     sGrade.setGEnglish(getGrade.get150Score());
                     sGrade.setGGeography(getGrade.get100Score());
                     sGrade.setGHistory(getGrade.get100Score());
                     sGrade.setGMath(getGrade.get150Score());
                     sGrade.setGPolitics(getGrade.get100Score());

                 }
               }
           }else if (testYear.contains("2018")){
               sGrade.setGBiology(getGrade.get100Score());
               sGrade.setGChemistry(getGrade.get100Score());
               sGrade.setGChinese(getGrade.get150Score());
               sGrade.setGEnglish(getGrade.get150Score());
               sGrade.setGGeography(getGrade.get100Score());
               sGrade.setGHistory(getGrade.get100Score());
               sGrade.setGMath(getGrade.get150Score());
               sGrade.setGPolitics(getGrade.get100Score());
               sGrade.setGPysics(getGrade.get100Score());
           }
           sGradeDao.insertSelective(sGrade);
            Thread.currentThread().sleep(100);
            System.out.println("班级为"+sClass.getClassNoName());
            System.out.println(sGrade);
            System.out.println("学年为"+sTest.getTestName());




        }
    }

}
