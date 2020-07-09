package com.xuexikuaile.deng;

import com.xuexikuaile.deng.dao.SClassDao;
import com.xuexikuaile.deng.plugin.ChangeDate;
import com.xuexikuaile.deng.pojo.SClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: cytern
 * @Date: 2020/6/10 17:24
 */
@Component
public class addClass {
    @Autowired
    private SClassDao sClassDao;

    ChangeDate changeDate =new ChangeDate();
    Random random = new Random();
    ChineseName chineseName =new ChineseName();

  private String[] chinese= {"十","一","二","三","四","五","六","七","八","九"};
  private String[] honor = {"优秀班级","","","","","","文明集体","","","",""};
  private String[] des = {"","","","","年级之星","纪律严明","","","活泼积极","",""};
  private String[] wold ={"互相学习","取长补短","勇攀高峰","与时俱进","开拓创新","顽强拼搏","励精图治","争创一流","好好学习","猛虎出山","锐不可当","奋力拼搏","扬我班风","勇争第一"};
    List<SClass> sClasses =new ArrayList<>();
  public List<SClass> getClasses(){
      for (int i = 1;i<4;i++){
          for (int a = 1;a<21;a++){
              String className = "高" +"(" + i +  ")" +a + "班";
              for (int s =0 ;s<10;s++){
                className =   className.replaceAll(String.valueOf(s),chinese[s]);

              }
              SClass sClass = new SClass();
              sClass.setClassConf(className+wold[random.nextInt(wold.length)] +"  " + honor[random.nextInt(honor.length)]);
              sClass.setClassDesignation(des[random.nextInt(11)]);
              sClass.setClassHonor(honor[random.nextInt(11)]);
              sClass.setCTime(changeDate.getDate());
              sClass.setUTime(changeDate.getDate());
              sClass.setClassWatchword(wold[random.nextInt(wold.length)]+"  " + wold[random.nextInt(wold.length)]);
              sClass.setClassNickName(chineseName.backClassName());
              sClass.setClassNoName(className);
              sClass.setClassUrl("/");
              sClasses.add(sClass);
          }
      }

      return sClasses;
  }



}
