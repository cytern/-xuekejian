package com.xuexikuaile.deng;

import com.xuexikuaile.deng.dao.*;
import com.xuexikuaile.deng.pojo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/6/16 17:20
 */
@SpringBootTest
public class UpdateGrade {
    @Autowired
    private SGradeDao sGradeDao;
    @Autowired
    private STestDao sTestDao;
    @Autowired
    private STestClassDao sTestClassDao;
    @Autowired
    private SStudentDao sStudentDao;
    @Autowired
    private GradeRuleDao gradeRuleDao;

    @Autowired
    private SClassDao sClassDao;
    @Autowired
    GetGrade getGrade= new GetGrade();

  @Test
    void update() throws InterruptedException {
        List<STestClass> sTestClasses = sTestClassDao.getAll();
        for (STestClass sTestClass:sTestClasses){
            List<SStudent> sStudents = sStudentDao.getStudentByClassId(sTestClass.getClassId());
            STest sTest = sTestDao.selectByPrimaryKey(sTestClass.getTestId());
            SClass sClass = sClassDao.getById(sTestClass.getClassId());
            for (SStudent sStudent:sStudents){
                 SGrade sGrade = new SGrade();
                sGrade.setStudentId(sStudent.getSStudentId());
                sGrade.setTestId(sTestClass.getTestId());
                sGrade.setGradeStatus("success");
                sGrade.setClassId(sTestClass.getClassId());
                String testYear = sTest.getTestName();
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
            }
        }
    }

     @Test
    void updates() throws InterruptedException {
   List<STest> sTests = sTestDao.getAllTest();
   for (STest sTest:sTests){
       List<SGrade> sGrades = sGradeDao.getAllGradeByTestId(sTest.getSTestId());
       for (SGrade sGrade:sGrades){
           BigDecimal totalPrice = new BigDecimal(0);
           try {
               totalPrice = sGrade.getGBiology()
                       .add(sGrade.getGChinese())
                       .add(sGrade.getGChemistry())
                       .add(sGrade.getGEnglish())
                       .add(sGrade.getGGeography())
                       .add(sGrade.getGMath())
                       .add(sGrade.getGHistory())
                       .add(sGrade.getGPolitics())
                       .add(sGrade.getGPysics());
           } catch (Exception e) {
               try {
                   totalPrice =totalPrice.add(sGrade.getGBiology());
               } catch (Exception exception) {
               }
               try {
                   totalPrice = totalPrice.add(sGrade.getGChinese());
               } catch (Exception exception) {
                   exception.printStackTrace();
               }
               try {
                   totalPrice = totalPrice.add(sGrade.getGPysics());
               } catch (Exception exception) {
                   exception.printStackTrace();
               }
               try {
                   totalPrice = totalPrice.add(sGrade.getGPolitics());
               } catch (Exception exception) {
                   exception.printStackTrace();
               }
               try {
                   totalPrice = totalPrice.add(sGrade.getGHistory());
               } catch (Exception exception) {
                   exception.printStackTrace();
               }
               try {
                   totalPrice = totalPrice.add(sGrade.getGMath());
               } catch (Exception exception) {
                   exception.printStackTrace();
               }
               try {
                   totalPrice = totalPrice.add(sGrade.getGGeography());
               } catch (Exception exception) {
                   exception.printStackTrace();
               }
               try {
                   totalPrice = totalPrice.add(sGrade.getGEnglish());
               } catch (Exception exception) {
                   exception.printStackTrace();
               }
               try {
                   totalPrice = totalPrice.add(sGrade.getGChemistry());
               } catch (Exception exception) {
                   exception.printStackTrace();
               }


           }
           sGrade.setTotalPrice(totalPrice);
           sGradeDao.updateByPrimaryKeySelective(sGrade);
           System.out.println(sGrade);
           Thread.currentThread().sleep(50);
       }
   }
    }
}
