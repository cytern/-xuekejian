package com.xuexikuaile.deng.service;

import com.xuexikuaile.deng.dao.*;
import com.xuexikuaile.deng.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: cytern
 * @Date: 2020/6/12 15:46
 */
@Service
public class GetGradeRule {
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

         //获取那些没有rule 的测试
    public List<STest>  getNoRuleTest(){
        List<STest> sTests = sTestDao.getAllTest();
        List<STest> sTests1 = new ArrayList<>();
        for (STest sTest:sTests){
            try {
                List<GradeRule> gradeRules = gradeRuleDao.getByTestId(sTest.getSTestId());
                boolean ave = false;
                boolean max = false;
                try {
                    for (GradeRule gradeRule:gradeRules){
                        if (gradeRule.getGradeType().contains("classAve")){
                            ave = true;
                        }
                        if (gradeRule.getGradeType().contains("classMax")){
                            max = true;
                        }
                    }
                } catch (Exception e) {
                    sTests1.add(sTest);
                }
                if (ave == true&& max ==true){

                }else {
                    sTests1.add(sTest);
                }
            } catch (Exception e) {
                sTests1.add(sTest);
            }
        }
        return sTests1;
    }
    //添加rule 主方法
    @Scheduled(cron = "0 0 12 * * ?")
    public void addGradeRuleClass() throws InterruptedException {
       List<STestClass> sTestClasses = sTestClassDao.getAll();
       List<STest> sTests = getNoRuleTest();
        System.out.println(sTests);
       for (STestClass sTestClass:sTestClasses){
           for (STest sTest:sTests){
               if (1  ==1){
                   STest sTest1 = sTestDao.selectByPrimaryKey(sTestClass.getTestId());
                   List<SGrade> sGrades = sGradeDao.getByTestClassId(sTest1.getSTestId(),sTestClass.getClassId());
                   Map<String,Object> ruleGrade = getRule(sGrades);
                   for (SGrade sGrade:sGrades){
                       Map<String,Object> maps = getOneGrade(ruleGrade,sGrade.getStudentId(),"classMax",sGrade.getTestId());

                   }
               }
           }


       }
      List<STest> sTests1 = sTestDao.getAllTest();
      for (STest sTest:sTests1){
          List<SGrade> sGrades = sGradeDao.getAllGradeByTestId(sTest.getSTestId());
          Map<String,Object> ruleGrade = getRule(sGrades);
          for (SGrade sGrade:sGrades){
              Map<String,Object> maps = getOneGrade(ruleGrade,sGrade.getStudentId(),"gradeMax",sGrade.getTestId());
          }
      }
    }
  //把所有数据系列化
    public Map<String,Object> getRule(List<SGrade> sGrades){
     List<SGrade> engList,chineseList,mathList,bioList = new ArrayList<>(),cheList = new ArrayList<>(),phyList =new ArrayList<>(),geoList=new ArrayList<>(),polList = new ArrayList<>(),hisList= new ArrayList<>();
     List<TotalScience> scienceList,artList,totalList  = new ArrayList<>();
      engList = sGrades.stream().sorted(Comparator.comparing(SGrade::getGEnglish).reversed()).collect(Collectors.toList());
        chineseList = sGrades.stream().sorted(Comparator.comparing(SGrade::getGChinese).reversed()).collect(Collectors.toList());
        mathList = sGrades.stream().sorted(Comparator.comparing(SGrade::getGMath).reversed()).collect(Collectors.toList());
        try {
            bioList = sGrades.stream().sorted(Comparator.comparing(SGrade::getGBiology).reversed()).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cheList = sGrades.stream().sorted(Comparator.comparing(SGrade::getGChemistry).reversed()).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            phyList = sGrades.stream().sorted(Comparator.comparing(SGrade::getGPysics).reversed()).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            geoList = sGrades.stream().sorted(Comparator.comparing(SGrade::getGGeography).reversed()).collect(Collectors.toList());
        } catch (Exception e) {

        }
        try {
            polList = sGrades.stream().sorted(Comparator.comparing(SGrade::getGPolitics).reversed()).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            hisList = sGrades.stream().sorted(Comparator.comparing(SGrade::getGHistory).reversed()).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<TotalScience> totalSciences =new ArrayList<>();
        for (SGrade sGrade:sGrades){
            BigDecimal totalScience = new BigDecimal(0);
            BigDecimal totalArt = new BigDecimal(0);
            BigDecimal totalPrice = new BigDecimal(0);
            try {
                totalScience = sGrade.getGChemistry().add(sGrade.getGPysics()).add(sGrade.getGBiology());
            } catch (Exception e) {
                totalScience= new BigDecimal(0);
            }
            try {
                totalArt = sGrade.getGGeography().add(sGrade.getGPolitics()).add(sGrade.getGHistory());
            } catch (Exception e) {
              totalArt = new BigDecimal(0);
            }
            try {
                totalPrice = sGrade.getGChinese();
            } catch (Exception e) {
                totalPrice = new BigDecimal(0);
            }
            try {
                totalPrice = totalPrice.add(sGrade.getGEnglish());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                totalPrice = totalPrice.add(sGrade.getGMath());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                totalPrice = totalPrice.add(sGrade.getGHistory());
            } catch (Exception e) {
            }
            try {
                totalPrice = totalPrice.add(sGrade.getGBiology());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                totalPrice = totalPrice.add(sGrade.getGChemistry());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                totalPrice = totalPrice.add(sGrade.getGPysics());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                totalPrice = totalPrice.add(sGrade.getGPolitics());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                totalPrice = totalPrice.add(sGrade.getGGeography());
            } catch (Exception e) {
                e.printStackTrace();
            }
            TotalScience totalScience1 = new TotalScience();
            totalScience1.setClassId(sGrade.getClassId());
            totalScience1.setStudentId(sGrade.getStudentId());
            totalScience1.setSGradeId(sGrade.getSGradeId());
            totalScience1.setTestId(sGrade.getTestId());
            totalScience1.setTotalScience(totalScience);
            totalScience1.setTotalArt(totalArt);
            totalScience1.setTotalPrice(totalPrice);
            totalSciences.add(totalScience1);
        }
        scienceList = totalSciences.stream().sorted(Comparator.comparing(TotalScience::getTotalScience).reversed()).collect(Collectors.toList());
        artList = totalSciences.stream().sorted(Comparator.comparing(TotalScience::getTotalArt).reversed()).collect(Collectors.toList());
        totalList = totalSciences.stream().sorted(Comparator.comparing(TotalScience::getTotalPrice).reversed()).collect(Collectors.toList());
        Map<String,Object> map = new HashMap<>();
        map.put("chinese",chineseList);
        map.put("english",engList);
        map.put("bio",bioList);
        map.put("mathList",mathList);
        map.put("cheList",cheList);
        map.put("phyList",phyList);
        map.put("geoList",geoList);
        map.put("polList",polList);
        map.put("hisList",hisList);
        map.put("scienceList",scienceList);
        map.put("artList",artList);
        map.put("totalList",totalList);
     return map;
    }
    //把学生个人的sgrade rule 输出
    public Map<String,Object> getOneGrade(Map<String,Object> ruleGrade,int studentId,String gradeType,int testId) throws InterruptedException {
        List<SGrade> engList,chineseList,mathList,bioList,cheList,phyList,geoList,polList,hisList= new ArrayList<>();
        List<TotalScience> scienceList,artList,totalList  = new ArrayList<>();
        chineseList = (List<SGrade>) ruleGrade.get("chinese");
        engList = (List<SGrade>) ruleGrade.get("english");
        mathList = (List<SGrade>) ruleGrade.get("mathList");
        bioList = (List<SGrade>) ruleGrade.get("bio");
        cheList = (List<SGrade>) ruleGrade.get("cheList");
        phyList = (List<SGrade>) ruleGrade.get("phyList");
        geoList = (List<SGrade>) ruleGrade.get("geoList");
        polList = (List<SGrade>) ruleGrade.get("polList");
        hisList = (List<SGrade>) ruleGrade.get("hisList");
        scienceList = (List<TotalScience>) ruleGrade.get("scienceList");
        artList = (List<TotalScience>) ruleGrade.get("artList");
        totalList = (List<TotalScience>) ruleGrade.get("totalList");
        GradeRule gradeRule = new GradeRule();

        //拿出语文名次
        for (int i =0;i<chineseList.size();i++){
            if (studentId == chineseList.get(i).getStudentId()){
                gradeRule.setChinesePrice(new BigDecimal(i+1));
            }
        }
        //拿出数学名次
        for (int i =0;i<mathList.size();i++){
            if (studentId == mathList.get(i).getStudentId()){
                gradeRule.setMathPrice(new BigDecimal(i+1));
            }
        }

        //英语名次
        for (int i =0;i<engList.size();i++){
            if (studentId == engList.get(i).getStudentId()){
                gradeRule.setEnglishPrice(new BigDecimal(i+1));
            }
        }
        //生物名次
        try {
            for (int i =0;i<bioList.size();i++){
                if (studentId == bioList.get(i).getStudentId()){
                    gradeRule.setBioPrice(new BigDecimal(i+1));
                }
            }
        } catch (Exception e) {
            System.out.println("无生物名次");
        }
        //化学名次
        try {
            for (int i =0;i<cheList.size();i++){
                if (studentId == cheList.get(i).getStudentId()){
                    gradeRule.setChePrice(new BigDecimal(i+1));
                }
            }
        } catch (Exception e) {
            System.out.println("无化学名次");
        }
        //物理名次
        try {
            for (int i =0;i<phyList.size();i++){
                if (studentId == phyList.get(i).getStudentId()){
                    gradeRule.setPhyPrice(new BigDecimal(i+1));
                }
            }
        } catch (Exception e) {
            System.out.println("无物理名次");
        }
        //政治名次
        try {
            for (int i =0;i<polList.size();i++){
                if (studentId == polList.get(i).getStudentId()){
                    gradeRule.setPolPrice(new BigDecimal(i+1));
                }
            }
        } catch (Exception e) {
            System.out.println("无政治名次");
        }
        //历史名次
        try {
            for (int i =0;i<hisList.size();i++){
                if (studentId == hisList.get(i).getStudentId()){
                    gradeRule.setHisPrice(new BigDecimal(i+1));
                }
            }
        } catch (Exception e) {
            System.out.println("无政治名次");
        }
        //地理名次
        try {
            for (int i =0;i<geoList.size();i++){
                if (studentId == geoList.get(i).getStudentId()){
                    gradeRule.setGeoPrice(new BigDecimal(i+1));
                }
            }
        } catch (Exception e) {
            System.out.println("无地理名次");
        }

        //理综名次
        try {
            for (int i =0;i<scienceList.size();i++){
                if (studentId == scienceList.get(i).getStudentId()){
                    gradeRule.setSciencePrice(new BigDecimal(i+1));
                }
            }
        } catch (Exception e) {
            System.out.println("无理综名次");
        }
        //文综名次
        try {
            for (int i =0;i<artList.size();i++){
                if (studentId == artList.get(i).getStudentId()){
                    gradeRule.setArtsPrice(new BigDecimal(i+1));
                }
            }
        } catch (Exception e) {
            System.out.println("无文综名次");
        }

        //总名次

        try {
            for (int i =0;i<totalList.size();i++){
                if (studentId == totalList.get(i).getStudentId()){
                    gradeRule.setTotalPrice(new BigDecimal(i+1));
                    gradeRule.setStudentId(studentId);
                    gradeRule.setGradeTestId(testId);
                    gradeRule.setScoreorgit(1);
                    gradeRule.setGradeType(gradeType);
                }
            }
        } catch (Exception e) {
            System.out.println("无总名次？？");
        }
        GradeRule gradeRule1 =  gradeRuleDao.getByAllType(gradeRule);
        try {
            if (gradeRule1.getGradeRuleId() !=0){
                return null;
            }
        } catch (Exception e) {

        }
        gradeRuleDao.insertSelective(gradeRule);
        System.out.println(gradeRule);
        Thread.currentThread().sleep(100);
        Map<String,Object> map = new HashMap<>();
        map.put(gradeType,gradeRule);
        return map;



    }


}
