package com.xuexikuaile.deng.service;

import com.xuexikuaile.deng.dao.*;
import com.xuexikuaile.deng.plugin.ChangeDate;
import com.xuexikuaile.deng.pojo.SClass;
import com.xuexikuaile.deng.pojo.SStudent;
import com.xuexikuaile.deng.pojo.STitle;
import com.xuexikuaile.deng.pojo.SUser;
import com.xuexikuaile.deng.pojo.STitleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: cytern
 * @Date: 2020/7/3 1:23
 */
@Component
public class TitleService {
    @Autowired
    private STokenDao sTokenDao;
    @Autowired
    private SUserDao sUserDao;
    @Autowired
    private SStudentDao sStudentDao;
    @Autowired
    private STeacherDao sTeacherDao;
    @Autowired
    private STitleDao sTitleDao;
    @Autowired
    private SClassDao sClassDao;
    @Autowired
    private STitleUserDao sTitleUserDao;



   public Map<String,Object> addTitle(Integer userId,String titleType,STitle sTitle){
       ChangeDate changeDate = new ChangeDate();
       SUser sUser = sUserDao.selectByPrimaryKey(userId);
       String userType = sUser.getUserType();
       Map<String,Object> map = new HashMap<>();
       if (userType.contains("teacher") || userType.contains("root")){

       }else {
           SStudent sStudent = sStudentDao.getStudentByUserId(sUser.getUserId());
           List<SStudent> sStudents = sStudentDao.getStudentByClassId(sStudent.getClassId());
           try {
               if (sTitle.getCTime() == null){
                   sTitle.setCTime(changeDate.getDate());
               }
           } catch (Exception e) {
               sTitle.setCTime(changeDate.getDate());
           }
           try {
               if (sTitle.getEndTime() == null){
                  Calendar cal = Calendar.getInstance();
                  cal.setTime(changeDate.getDate());//设置起时间
                  cal.add(Calendar.DATE,3);
                  sTitle.setEndTime(cal.getTime());
              }
           } catch (Exception e) {
               Calendar cal = Calendar.getInstance();
               cal.setTime(changeDate.getDate());//设置起时间
               cal.add(Calendar.DATE,3);
               sTitle.setEndTime(cal.getTime());
           }
           sTitle.setStartUserId(userId);
           sTitle.setTitleType("class");
           int i  =sTitleDao.insertSelective(sTitle);
           try {
               if (sTitle.getSTitleId() >0){
                for (SStudent sStudent1:sStudents){
                    STitleUser sTitleUser = new STitleUser();
                    sTitleUser.setCTime(changeDate.getDate());
                    sTitleUser.setTitleId(sTitle.getSTitleId());
                    sTitleUser.setUserId(sStudent1.getUserId());

                    int a = sTitleUserDao.insertSelective(sTitleUser);
                    if (a>0){
                        map.put("success","添加成功");
                    }else {
                        map.put("error","添加失败");
                    }

                }
               }
           } catch (Exception e) {
               map.put("error","添加失败");
           }


       }

       return map;
   }

   public Map<String,Object> editTitle(STitle sTitle){
       Map<String,Object> map = new HashMap<>();
       sTitle.setStartUserId(null);
       sTitle.setCTime(null);
       System.out.println(sTitle);
       int i = sTitleDao.updateByPrimaryKeySelective(sTitle);
       System.out.println(i);
       if (i>0){
           map.put("success","更新成功");
       }else {
           map.put("error","更新失败");
       }
       return map;
   }

   public Map<String,Object> deleteTitle(Integer titleId){
       STitle sTitle = sTitleDao.selectByPrimaryKey(titleId);
       Map<String,Object> map = new HashMap<>();
       try {
           if (sTitle.getSTitleId() < 0){
               map.put("error","无效的titleId");
               return map;
           }
       } catch (Exception e) {
           map.put("error","无效的titleId");
           return map;
       }
       sTitleUserDao.deleteByTitleId(sTitle.getSTitleId());
       int i = sTitleDao.deleteByPrimaryKey(sTitle.getSTitleId());
       if (i >0){
           map.put("success","删除成功");
       }else {
           map.put("error","删除失败");
       }
       return map;
   }
}
