package com.xuexikuaile.deng.service;

import com.xuexikuaile.deng.dao.*;
import com.xuexikuaile.deng.plugin.Response;
import com.xuexikuaile.deng.pojo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: cytern
 * @Date: 2020/6/12 13:56
 */
@Component
public class StudentConf {
    @Autowired
    private STokenDao sTokenDao;
    @Autowired
    private SUserDao sUserDao;
    @Autowired
    private SStudentDao sStudentDao;
    @Autowired
    private SGradeDao sGradeDao;
    @Autowired
    Response response =new Response();
    @Autowired
    private STestDao sTestDao;

    @Autowired
    private SClassDao sClassDao;
    @Autowired
    private GradeNumDao gradeNumDao;
    @Autowired
    private GradeRuleDao gradeRuleDao;

    public Map<String,Object> getConfByToken(String token){
        SToken sToken = sTokenDao.getTokenByToken(token);
        try {
            if (sToken.getUserId() == null){
                  return  response.responseError("登录验证失效");
            }
        } catch (Exception e) {
            return  response.responseError("登录验证失效");
        }
       SStudent sStudent = sStudentDao.getStudentByUserId(sToken.getUserId());
        try {
            if (sStudent.getUserId() == null){
                return  response.responseError("无效用户");
            }
        } catch (Exception e) {
            return  response.responseError("无效用户");
        }

  return null;

    }

    public Map<String,Object> getGradeLike(Integer studentId){
        List<SGrade> gradeList = sGradeDao.getAllGrade(studentId);
        List<STest> tests = new ArrayList<>();
        for (SGrade sGrade:gradeList){
            STest sTest = sTestDao.selectByPrimaryKey(sGrade.getTestId());
            tests.add(sTest);
        }
        return null;
    }

    /**
     *
     * @param studentId
     * @return  获取学生的成绩信息，两种雷达图，层次化成绩信息
     */
  public Map<String,Object>  mainMethod(Integer studentId){
      Map<String,Object> map = new HashMap<>();
      //获取个人信息
      SStudent sStudent =sStudentDao.selectByPrimaryKey(studentId);
      try {
          if (sStudent.getSStudentId() == 0){
              return response.responseError("无效的id");
          }
      }catch (Exception e){
          return response.responseError("无效的id");
      }
      map.put("student",sStudent);
      int classNum = sStudentDao.getNumOfOneClass(sStudent.getClassId());
      int gradeNum = new Integer(0);
      SClass sClass = sClassDao.getById(sStudent.getClassId());
      map.put("class",sClass);
      if (sClass.getClassNoName().contains("高（一）")){
          gradeNum = 1220;
      }
      if (sClass.getClassNoName().contains("高（二）")){
          gradeNum = 1220;
      }
      if (sClass.getClassNoName().contains("高（三）")){
          gradeNum = 1220;
      }

       List<STest> sTests = sTestDao.getAllTest();
      sTests.sort(Comparator.comparing(STest::getTestTime));
      STest latestTest = sTests.get(sTests.size()-1);
      SGrade sGrade = sGradeDao.selectByStudentIdAndTestId(latestTest.getSTestId(),studentId);
      map.put("lastGrade",sGrade);
      GradeRule g = new GradeRule();
      g.setStudentId(sStudent.getSStudentId());
      g.setGradeType("classMax");
      g.setGradeTestId(latestTest.getSTestId());
      GradeRule classRule = gradeRuleDao.getByAllType(g);
      System.out.println(classRule);
      g.setGradeType("gradeMax");
      GradeRule gradeRule = gradeRuleDao.getByAllType(g);
      System.out.println(gradeRule);
      try {
          map.put("classGradeChart",getIndicator(classRule,61));
      } catch (Exception e) {
          System.out.println("班级能力表获取失败");
      }
      try {

          map.put("GradeGradeChart",getIndicator(gradeRule,1220));
      } catch (Exception e) {
          System.out.println("年级能力表获取失败");
      }
      List<GradeRule> classRules = gradeRuleDao.getByStudentId(studentId,"classMax");
      List<GradeRule> gradeRules = gradeRuleDao.getByStudentId(studentId,"gradeMax");
      classRules.sort(Comparator.comparing(GradeRule::getGradeTestId).reversed());
      gradeRules.sort(Comparator.comparing(GradeRule::getGradeTestId).reversed());
      List<SGrade> sGrades = sGradeDao.getByStudentId(studentId);


      try {
          sGrades.sort(Comparator.comparing(SGrade::getTestId).reversed());
      } catch (Exception e) {
      }
        //获取条形图能力展示
      Map<String,Object> veBarData = new HashMap<>();
          List<String> columns = new ArrayList<>();
          columns.add("科目");
          columns.add("分数（旧）");
          columns.add("分数（新）");
          veBarData.put("columns",columns);
          List<Map<String,Object>> rows = new ArrayList<>();
          SGrade  newGrade = new SGrade();
          try {
            newGrade = sGrades.get(sGrades.size() -1);
          } catch (Exception e) {
               newGrade = new SGrade();
          }
          try {
              SGrade oldGrade = sGrades.get(sGrades.size() -2);
               rows = getRows(newGrade,oldGrade);
               veBarData.put("rows",rows);
          } catch (Exception e) {
              SGrade oldGrade = new SGrade();
              rows = getRows(newGrade,oldGrade);
              veBarData.put("rows",rows);
          }
         map.put("veBarData",veBarData);

          //添加最近考试成绩
        map.put("classLineData",getLineData(classRules));
        map.put("gradeLineData",getLineData(gradeRules));



      return map;

  }

    /**
     *
     * @param gradeRules
     * @return 获取全部成绩的三方表/成绩取最近的六次考试
     */
    Map<String,Object> getLineData(List<GradeRule> gradeRules){
      Map<String,Object> mainThreeData = new HashMap<>();
      Map<String,Object>  scienceThreeData= new HashMap<>();
      Map<String,Object>  artThreeData= new HashMap<>();
      Map<String,Object>  totalThreeData= new HashMap<>();
      List<Map<String,Object>> mainRows = new ArrayList<>();
      List<Map<String,Object>> scienceRows = new ArrayList<>();
      List<Map<String,Object>> artRows = new ArrayList<>();
      List<Map<String,Object>> totalRows = new ArrayList<>();
      List<String> mainColumns =new ArrayList<>();
      mainColumns.add("考试日期");
      mainColumns.add("语文");
      mainColumns.add("数学");
      mainColumns.add("英语");
      List<String> artColumns =new ArrayList<>();
        artColumns.add("考试日期");
        artColumns.add("政治");
        artColumns.add("地理");
        artColumns.add("历史");
      List<String> scienceColumns =new ArrayList<>();
        scienceColumns.add("考试日期");
        scienceColumns.add("生物");
        scienceColumns.add("化学");
        scienceColumns.add("物理");
      List<String> totalColumns =new ArrayList<>();
        totalColumns.add("考试日期");
        totalColumns.add("理综");
        totalColumns.add("文综");
        totalColumns.add("整体");
      if (gradeRules.size() <6){
          for (int i =0;i<gradeRules.size();i++){
              GradeRule gradeRule = gradeRules.get(gradeRules.size()-i);
              BigDecimal totalNum = BigDecimal.valueOf(0);
              if (gradeRule.getGradeType().contains("class")){
                  totalNum = BigDecimal.valueOf(62);
              }else {
                  totalNum = BigDecimal.valueOf(1261);
              }
              Map<String,Object> mainMap =new HashMap<>();
              Map<String,Object> scienceMap =new HashMap<>();
              Map<String,Object> artMap =new HashMap<>();
              Map<String,Object> totalMap =new HashMap<>();
              STest sTest = sTestDao.selectByPrimaryKey(gradeRule.getGradeTestId());
              //添加主三科成绩
              mainMap.put("考试日期",sTest.getTestName());
              try {
                  mainMap.put("语文",totalNum.subtract(gradeRule.getChinesePrice()));
              } catch (Exception e) {
                  System.out.println("主三科成绩折线图 ： 语文添加失败  " + e);
                  mainMap.put("语文",0);
              }
              try {
                  mainMap.put("数学",totalNum.subtract(gradeRule.getMathPrice()));
              } catch (Exception e) {
                  System.out.println("主三科成绩折线图 ： 数学添加失败  " + e);
                  mainMap.put("数学",0);
              }
              try {
                  mainMap.put("英语",totalNum.subtract(gradeRule.getEnglishPrice()));
              } catch (Exception e) {
                  System.out.println("主三科成绩折线图 ： 英语添加失败  " + e);
                  mainMap.put("英语",0);
              }
              mainRows.add(mainMap);
              //添加理三科成绩
              scienceMap.put("考试日期",sTest.getTestName());
              try {
                  scienceMap.put("生物",totalNum.subtract(gradeRule.getBioPrice()));
              } catch (Exception e) {
                  System.out.println("理三科成绩折线图  ： 生物添加失败" + e);
                  scienceMap.put("生物",0);
                  e.printStackTrace();
              }
              try {
                  scienceMap.put("化学",totalNum.subtract(gradeRule.getChePrice()));
              } catch (Exception e) {
                  System.out.println("理三科成绩折线图  ： 化学添加失败" + e);
                  scienceMap.put("化学",0);
                  e.printStackTrace();
              }
              try {
                  scienceMap.put("物理",totalNum.subtract(gradeRule.getPhyPrice()));
              } catch (Exception e) {
                  System.out.println("理三科成绩折线图  ： 物理添加失败" + e);
                  scienceMap.put("物理",0);
                  e.printStackTrace();
              }
              scienceRows.add(scienceMap);
              //添加文三科成绩
              artMap.put("考试日期",sTest.getTestName());
              try {
                  artMap.put("政治",totalNum.subtract(gradeRule.getPolPrice()));
              } catch (Exception e) {
                  artMap.put("政治",0);
                  System.out.println("文三科成绩折线图  ： 政治添加失败" + e);
                  e.printStackTrace();
              }
              try {
                  artMap.put("地理",totalNum.subtract(gradeRule.getGeoPrice()));
              } catch (Exception e) {
                  artMap.put("地理",0);
                  System.out.println("文三科成绩折线图  ： 地理添加失败" + e);
                  e.printStackTrace();
              }
              try {
                  artMap.put("历史",totalNum.subtract(gradeRule.getHisPrice()));
              } catch (Exception e) {
                  artMap.put("历史",0);
                  System.out.println("文三科成绩折线图  ： 历史添加失败" + e);
                  e.printStackTrace();
              }
              artRows.add(artMap);
              //总三科成绩添加
             totalMap.put("考试日期",sTest.getTestName());
              try {
                  totalMap.put("理综",totalNum.subtract(gradeRule.getSciencePrice()));
              } catch (Exception e) {
                  totalMap.put("理综",0);
                  System.out.println("总三科成绩折线图  ： 理综添加失败" + e);
                  e.printStackTrace();
              }
              try {
                  totalMap.put("文综",totalNum.subtract(gradeRule.getArtsPrice()));
              } catch (Exception e) {
                  totalMap.put("文综",0);
                  System.out.println("总三科成绩折线图  ： 文综添加失败" + e);
                  e.printStackTrace();
              }
              try {
                  totalMap.put("整体",totalNum.subtract(gradeRule.getTotalPrice()));
              } catch (Exception e) {
                  totalMap.put("整体",0);
                  System.out.println("总三科成绩折线图  ： 整体添加失败" + e);
                  e.printStackTrace();
              }
              totalRows.add(totalMap);
          }

      }else {
          for (int i =0;i<6;i++){
              GradeRule gradeRule = gradeRules.get(5-i);
              BigDecimal totalNum = BigDecimal.valueOf(0);
              if (gradeRule.getGradeType().contains("class")){
                  totalNum = BigDecimal.valueOf(61);
              }else {
                  totalNum = BigDecimal.valueOf(1201);
              }
              Map<String,Object> mainMap =new HashMap<>();
              Map<String,Object> scienceMap =new HashMap<>();
              Map<String,Object> artMap =new HashMap<>();
              Map<String,Object> totalMap =new HashMap<>();
              STest sTest = sTestDao.selectByPrimaryKey(gradeRule.getGradeTestId());
              //添加主三科成绩
              mainMap.put("考试日期",sTest.getTestName());
              try {
                  mainMap.put("语文",totalNum.subtract(gradeRule.getChinesePrice()));
              } catch (Exception e) {
                  System.out.println("主三科成绩折线图 ： 语文添加失败  " + e);
                  mainMap.put("语文",0);
              }
              try {
                  mainMap.put("数学",totalNum.subtract(gradeRule.getMathPrice()));
              } catch (Exception e) {
                  System.out.println("主三科成绩折线图 ： 数学添加失败  " + e);
                  mainMap.put("数学",0);
              }
              try {
                  mainMap.put("英语",totalNum.subtract(gradeRule.getEnglishPrice()));
              } catch (Exception e) {
                  System.out.println("主三科成绩折线图 ： 英语添加失败  " + e);
                  mainMap.put("英语",0);
              }
              mainRows.add(mainMap);
              //添加理三科成绩
              scienceMap.put("考试日期",sTest.getTestName());
              try {
                  scienceMap.put("生物",totalNum.subtract(gradeRule.getBioPrice()));
              } catch (Exception e) {
                  System.out.println("理三科成绩折线图  ： 生物添加失败" + e);
                  scienceMap.put("生物",0);
                  e.printStackTrace();
              }
              try {
                  scienceMap.put("化学",totalNum.subtract(gradeRule.getChePrice()));
              } catch (Exception e) {
                  System.out.println("理三科成绩折线图  ： 化学添加失败" + e);
                  scienceMap.put("化学",0);
                  e.printStackTrace();
              }
              try {
                  scienceMap.put("物理",totalNum.subtract(gradeRule.getPhyPrice()));
              } catch (Exception e) {
                  System.out.println("理三科成绩折线图  ： 物理添加失败" + e);
                  scienceMap.put("物理",0);
                  e.printStackTrace();
              }
              scienceRows.add(scienceMap);
              //添加文三科成绩
              artMap.put("考试日期",sTest.getTestName());
              try {
                  artMap.put("政治",totalNum.subtract(gradeRule.getPolPrice()));
              } catch (Exception e) {
                  artMap.put("政治",0);
                  System.out.println("文三科成绩折线图  ： 政治添加失败" + e);
                  e.printStackTrace();
              }
              try {
                  artMap.put("地理",totalNum.subtract(gradeRule.getGeoPrice()));
              } catch (Exception e) {
                  artMap.put("地理",0);
                  System.out.println("文三科成绩折线图  ： 地理添加失败" + e);
                  e.printStackTrace();
              }
              try {
                  artMap.put("历史",totalNum.subtract(gradeRule.getHisPrice()));
              } catch (Exception e) {
                  artMap.put("历史",0);
                  System.out.println("文三科成绩折线图  ： 历史添加失败" + e);
                  e.printStackTrace();
              }
              artRows.add(artMap);
              //总三科成绩添加
              totalMap.put("考试日期",sTest.getTestName());
              try {
                  totalMap.put("理综",totalNum.subtract(gradeRule.getSciencePrice()));
              } catch (Exception e) {
                  totalMap.put("理综",0);
                  System.out.println("总三科成绩折线图  ： 理综添加失败" + e);
                  e.printStackTrace();
              }
              try {
                  totalMap.put("文综",totalNum.subtract(gradeRule.getArtsPrice()));
              } catch (Exception e) {
                  totalMap.put("文综",0);
                  System.out.println("总三科成绩折线图  ： 文综添加失败" + e);
                  e.printStackTrace();
              }
              try {
                  totalMap.put("整体",totalNum.subtract(gradeRule.getTotalPrice()));
              } catch (Exception e) {
                  totalMap.put("整体",0);
                  System.out.println("总三科成绩折线图  ： 整体添加失败" + e);
                  e.printStackTrace();
              }
              totalRows.add(totalMap);
          }
      }
      mainThreeData.put("columns",mainColumns);
      mainThreeData.put("rows",mainRows);
      scienceThreeData.put("columns",scienceColumns);
        scienceThreeData.put("rows",scienceRows);
        artThreeData.put("columns",artColumns);
        artThreeData.put("rows",artRows);
        totalThreeData.put("columns",totalColumns);
        totalThreeData.put("rows",totalRows);
        Map<String,Object> mainMap = new HashMap<>();
        mainMap.put("mainThreeData",mainThreeData);
        mainMap.put("scienceThreeData",scienceThreeData);
        mainMap.put("artThreeData",artThreeData);
        mainMap.put("totalThreeData",totalThreeData);
        return mainMap;
    }


    /**
     *
     * @param newGrade
     * @param oldGrade
     * @return  获取柱形图数据，最近两次考试成绩分数对比
     */
    List<Map<String,Object>> getRows(SGrade newGrade,SGrade oldGrade){
        List<Map<String,Object>> rows = new ArrayList<>();

        //语文
       Map<String,Object> chineseRow = new HashMap<>();
        chineseRow.put("科目","语文");
        try {
            chineseRow.put("分数（旧）",oldGrade.getGChinese());
        } catch (Exception e) {
            chineseRow.put("分数（旧）",0);
        }
        try {
            chineseRow.put("分数（新）",newGrade.getGChinese());
        } catch (Exception e) {
            chineseRow.put("分数（新）",0);
        }
        rows.add(chineseRow);

       //数学
        Map<String,Object> mathRow = new HashMap<>();
        mathRow.put("科目","数学");
        try {
            mathRow.put("分数（旧）",oldGrade.getGMath());
        } catch (Exception e) {
            mathRow.put("分数（旧）",0);
        }
        try {
            mathRow.put("分数（新）",newGrade.getGMath());
        } catch (Exception e) {
            mathRow.put("分数（新）",0);
        }
        rows.add(mathRow);

        //英语
        Map<String,Object> engRow = new HashMap<>();
        engRow.put("科目","英语");
        try {
            engRow.put("分数（旧）",oldGrade.getGEnglish());
        } catch (Exception e) {
            engRow.put("分数（旧）",0);
        }
        try {
            engRow.put("分数（新）",newGrade.getGEnglish());
        } catch (Exception e) {
            engRow.put("分数（新）",0);
        }
        rows.add(engRow);

        //物理
        Map<String,Object> phyRow = new HashMap<>();
        phyRow.put("科目","物理");
        try {
            phyRow.put("分数（旧）",oldGrade.getGPysics());
        } catch (Exception e) {
            phyRow.put("分数（旧）",0);
        }
        try {
            phyRow.put("分数（新）",newGrade.getGPysics());
        } catch (Exception e) {
            phyRow.put("分数（新）",0);
        }
        if (phyRow.get("分数（新）").equals(BigDecimal.valueOf(0)) &&phyRow.get("分数（旧）").equals(BigDecimal.valueOf(0)) ){
            System.out.println("物理成绩无效");
        }else {
            rows.add(phyRow);
        }

        //化学
        Map<String,Object> cheRow = new HashMap<>();
        cheRow.put("科目","化学");
        try {
            cheRow.put("分数（旧）",oldGrade.getGChemistry());
        } catch (Exception e) {
            cheRow.put("分数（旧）",0);
        }
        try {
            cheRow.put("分数（新）",newGrade.getGChemistry());
        } catch (Exception e) {
            cheRow.put("分数（新）",0);
        }
        if (cheRow.get("分数（新）").equals(BigDecimal.valueOf(0)) &&cheRow.get("分数（旧）").equals(BigDecimal.valueOf(0)) ){
            System.out.println("化学成绩无效");
        }else {
            rows.add(cheRow);
        }

        //生物
        Map<String,Object> bioRow = new HashMap<>();
        bioRow.put("科目","生物");
        try {
            bioRow.put("分数（旧）",oldGrade.getGBiology());
        } catch (Exception e) {
            bioRow.put("分数（旧）",0);
        }
        try {
            bioRow.put("分数（新）",newGrade.getGBiology());
        } catch (Exception e) {
            bioRow.put("分数（新）",0);
        }
        if (bioRow.get("分数（新）").equals(BigDecimal.valueOf(0)) &&bioRow.get("分数（旧）").equals(BigDecimal.valueOf(0)) ){

            System.out.println("生物成绩无效");
        }else {
            rows.add(bioRow);
        }

        //地理
        Map<String,Object> geoRow = new HashMap<>();
        geoRow.put("科目","地理");
        try {
            geoRow.put("分数（旧）",oldGrade.getGGeography());
        } catch (Exception e) {
            geoRow.put("分数（旧）",0);
        }
        try {
            geoRow.put("分数（新）",newGrade.getGGeography());
        } catch (Exception e) {
            geoRow.put("分数（新）",0);
        }
        if (geoRow.get("分数（新）").equals(BigDecimal.valueOf(0)) &&geoRow.get("分数（旧）").equals(BigDecimal.valueOf(0)) ){

            System.out.println("地理成绩无效");
        }else {
            rows.add(geoRow);
        }

        //政治
        Map<String,Object> polRow = new HashMap<>();
        polRow.put("科目","政治");
        try {
            polRow.put("分数（旧）",oldGrade.getGPolitics());
        } catch (Exception e) {
            polRow.put("分数（旧）",0);
        }
        try {
            polRow.put("分数（新）",newGrade.getGPolitics());
        } catch (Exception e) {
            polRow.put("分数（新）",0);
        }
        if (polRow.get("分数（新）").equals(BigDecimal.valueOf(0)) &&polRow.get("分数（旧）").equals(BigDecimal.valueOf(0)) ){

            System.out.println("政治成绩无效");
        }else {
            rows.add(polRow);
        }

        //历史
        Map<String,Object> hisRow = new HashMap<>();
        hisRow.put("科目","历史");
        try {
            hisRow.put("分数（旧）",oldGrade.getGHistory());
        } catch (Exception e) {
            hisRow.put("分数（旧）",0);
        }
        try {
            hisRow.put("分数（新）",newGrade.getGHistory());
        } catch (Exception e) {
            hisRow.put("分数（新）",0);
        }
        if (hisRow.get("分数（新）").equals(BigDecimal.valueOf(0)) &&hisRow.get("分数（旧）").equals(BigDecimal.valueOf(0)) ){

            System.out.println("历史成绩无效");
        }else {
            rows.add(hisRow);
        }
        return rows;
    }

    /**
     *
     * @param
     * @param gradeRule
     * @param totalNum
     * @return 获取雷达图数据
     */
  Map<String,Object> getIndicator(GradeRule gradeRule,Integer totalNum){
      System.out.println("获取六边形数据");
      System.out.println(gradeRule);
      System.out.println("总人数  "+ totalNum);
          List<Map<String,Object>> indicator = new ArrayList<>();
          List<BigDecimal> totalValue = new ArrayList<>();
          //语文成绩
      try {
          Map<String,Object> map = new HashMap<>();
          if (gradeRule.getChinesePrice() !=null && gradeRule.getChinesePrice() != BigDecimal.valueOf(0)){
              BigDecimal gradeNum = BigDecimal.valueOf(totalNum+1).subtract(gradeRule.getChinesePrice());
              String size = getSSS(gradeNum,BigDecimal.valueOf(totalNum));
              size = "语文" + "(" + size + ")";
              map.put("text",size);
              map.put("max",totalNum);
              indicator.add(map);
              totalValue.add(gradeNum.setScale(2,BigDecimal.ROUND_HALF_UP));
          }else {
              map.put("text","语文");
              map.put("max",totalNum);
              indicator.add(map);
              totalValue.add(BigDecimal.valueOf(0));
          }
      } catch (Exception e) {
          System.out.println(e);
          Map<String,Object> map = new HashMap<>();
          map.put("text","语文");
          map.put("max",totalNum);
          indicator.add(map);
          totalValue.add(BigDecimal.valueOf(0));
      }
      //数学成绩
      try {
          Map<String,Object> map = new HashMap<>();
          if (gradeRule.getMathPrice() !=null && gradeRule.getMathPrice() != BigDecimal.valueOf(0)){
              BigDecimal gradeNum = BigDecimal.valueOf(totalNum+1).subtract(gradeRule.getMathPrice());
              String size = getSSS(gradeNum,BigDecimal.valueOf(totalNum));
              size = "数学" + "(" + size + ")";
              map.put("text",size);
              map.put("max",totalNum);
              indicator.add(map);
              totalValue.add(gradeNum.setScale(2,BigDecimal.ROUND_HALF_UP));
          }else {
              map.put("text","数学");
              map.put("max",totalNum);
              indicator.add(map);
              totalValue.add(BigDecimal.valueOf(0));
          }
      } catch (Exception e) {
          Map<String,Object> map = new HashMap<>();
          map.put("text","数学");
          map.put("max",totalNum);
          indicator.add(map);
          totalValue.add(BigDecimal.valueOf(0));
      }
      //英语成绩
      try {
          Map<String,Object> map = new HashMap<>();
          if (gradeRule.getEnglishPrice() !=null && gradeRule.getEnglishPrice() != BigDecimal.valueOf(0)){
              BigDecimal gradeNum = BigDecimal.valueOf(totalNum+1).subtract(gradeRule.getEnglishPrice());
              String size = getSSS(gradeNum,BigDecimal.valueOf(totalNum));
              size = "英语" + "(" + size + ")";
              map.put("text",size);
              map.put("max",totalNum);
              indicator.add(map);
              totalValue.add(gradeNum.setScale(2,BigDecimal.ROUND_HALF_UP));
          }else {
              map.put("text","英语");
              map.put("max",totalNum);
              indicator.add(map);
              totalValue.add(BigDecimal.valueOf(0));
          }
      } catch (Exception e) {
          Map<String,Object> map = new HashMap<>();
          map.put("text","英语");
          map.put("max",totalNum);
          indicator.add(map);
          totalValue.add(BigDecimal.valueOf(0));
      }
      //物理
      try {
          Map<String,Object> map = new HashMap<>();
          if (gradeRule.getPhyPrice() !=null && gradeRule.getPhyPrice() != BigDecimal.valueOf(0)){
              BigDecimal gradeNum = BigDecimal.valueOf(totalNum+1).subtract(gradeRule.getPhyPrice());
              String size = getSSS(gradeNum,BigDecimal.valueOf(totalNum));
              size = "物理" + "(" + size + ")";
              map.put("text",size);
              map.put("max",totalNum);
              indicator.add(map);
              totalValue.add(gradeNum.setScale(2,BigDecimal.ROUND_HALF_UP));
          }else {

          }
      } catch (Exception e) {

      }
      //化学
      try {
          Map<String,Object> map = new HashMap<>();
          if (gradeRule.getChePrice() !=null && gradeRule.getChePrice() != BigDecimal.valueOf(0)){
              BigDecimal gradeNum = BigDecimal.valueOf(totalNum+1).subtract(gradeRule.getChePrice());
              String size = getSSS(gradeNum,BigDecimal.valueOf(totalNum));
              size = "化学" + "(" + size + ")";
              map.put("text",size);
              map.put("max",totalNum);
              indicator.add(map);
              totalValue.add(gradeNum.setScale(2,BigDecimal.ROUND_HALF_UP));
          }else {

          }
      } catch (Exception e) {

      }
      //生物
      try {
          Map<String,Object> map = new HashMap<>();
          if (gradeRule.getBioPrice() !=null && gradeRule.getBioPrice() != BigDecimal.valueOf(0)){
              BigDecimal gradeNum = BigDecimal.valueOf(totalNum+1).subtract(gradeRule.getBioPrice());
              String size = getSSS(gradeNum,BigDecimal.valueOf(totalNum));
              size = "生物" + "(" + size + ")";
              map.put("text",size);
              map.put("max",totalNum);
              indicator.add(map);
              totalValue.add(gradeNum.setScale(2,BigDecimal.ROUND_HALF_UP));
          }else {

          }
      } catch (Exception e) {

      }
      //政治
      try {
          Map<String,Object> map = new HashMap<>();
          if (gradeRule.getPolPrice() !=null && gradeRule.getPolPrice() != BigDecimal.valueOf(0)){
              BigDecimal gradeNum = BigDecimal.valueOf(totalNum+1).subtract(gradeRule.getPolPrice());
              String size = getSSS(gradeNum,BigDecimal.valueOf(totalNum));
              size = "政治" + "(" + size + ")";
              map.put("text",size);
              map.put("max",totalNum);
              indicator.add(map);
              totalValue.add(gradeNum.setScale(2,BigDecimal.ROUND_HALF_UP));
          }else {

          }
      } catch (Exception e) {

      }
      //地理
      try {
          Map<String,Object> map = new HashMap<>();
          if (gradeRule.getGeoPrice() !=null && gradeRule.getGeoPrice() != BigDecimal.valueOf(0)){
              BigDecimal gradeNum = BigDecimal.valueOf(totalNum+1).subtract(gradeRule.getGeoPrice());
              String size = getSSS(gradeNum,BigDecimal.valueOf(totalNum));
              size = "地理" + "(" + size + ")";
              map.put("text",size);
              map.put("max",totalNum);
              indicator.add(map);
              totalValue.add(gradeNum.setScale(2,BigDecimal.ROUND_HALF_UP));
          }else {

          }
      } catch (Exception e) {

      }
      //历史
      try {
          Map<String,Object> map = new HashMap<>();
          if (gradeRule.getHisPrice() !=null && gradeRule.getHisPrice() != BigDecimal.valueOf(0)){
              BigDecimal gradeNum = BigDecimal.valueOf(totalNum+1).subtract(gradeRule.getHisPrice());
              String size = getSSS(gradeNum,BigDecimal.valueOf(totalNum));
              size = "历史" + "(" + size + ")";
              map.put("text",size);
              map.put("max",totalNum);
              indicator.add(map);
              totalValue.add(gradeNum.setScale(2,BigDecimal.ROUND_HALF_UP));
          }else {

          }
      } catch (Exception e) {

      }
      Map<String,Object> backData = new HashMap<>();
      backData.put("indicator",indicator);
      backData.put("totalValue",totalValue);
      return backData;
  }


    /**
     *
     * @param gradeNum
     * @param totalNum
     * @return  获取得到成绩所属范围E - SSS
     */

  String getSSS(BigDecimal gradeNum,BigDecimal totalNum){
        if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.98)) == 1){
            return "SSS";
        }
        if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.97)) == 1){
            return "SS";
        }
        if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.95)) == 1){
            return "S";
        }
         if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.90)) == 1){
          return "AAA";
      }
      if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.85)) == 1){
          return "AA";
      }
      if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.8)) == 1){
          return "A";
      }
      if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.75)) == 1){
          return "BBB";
      }
      if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.60)) == 1){
          return "BB";
      }
      if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.55)) == 1){
          return "B";
      }
      if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.40)) == 1){
          return "CCC";
      }
      if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.30)) == 1){
          return "CC";
      }
      if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.20)) == 1){
          return "C";
      }
      if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.10)) == 1){
          return "DDD";
      }
      if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.05)) == 1){
          return "DD";
      }
      if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.02)) == 1){
          return "D";
      }
      if (gradeNum.divide(totalNum,3).compareTo(BigDecimal.valueOf(0.01)) == 1){
          return "E";
      }
      return "???";
  }


}
