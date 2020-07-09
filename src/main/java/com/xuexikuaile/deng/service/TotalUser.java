package com.xuexikuaile.deng.service;

import com.xuexikuaile.deng.dao.SRootDao;
import com.xuexikuaile.deng.dao.SStudentDao;
import com.xuexikuaile.deng.dao.STeacherDao;
import com.xuexikuaile.deng.dao.SUserDao;
import com.xuexikuaile.deng.pojo.SRoot;
import com.xuexikuaile.deng.pojo.SStudent;
import com.xuexikuaile.deng.pojo.STeacher;
import com.xuexikuaile.deng.pojo.SUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: cytern
 * @Date: 2020/6/10 15:03
 */
@Component
public class TotalUser {
    @Autowired
   private SUserDao sUserDao;
    @Autowired
    private STeacherDao sTeacherDao;
    @Autowired
    private SStudentDao sStudentDao;
    @Autowired
    private SRootDao sRootDao;


    public Map<String,Object> getTotalUser(Map<String,Object> map,int userId){
      Map<String,Object> map1 = addUser(userId);
      String type = (String) map1.get("type");
      switch (type){
          case "teacher":{
             map.put("teacher",map1.get("teacher"));
              break;
          }
          case "student":{
              map.put("student",map1.get("student"));
              break;
          }
          case "root": {
              map.put("root",map1.get("root"));
              break;
          }

      }
      return map;
    }
    public Map<String,Object> getTotalUser(int userId){
        Map<String,Object> map1 = addUser(userId);
        return map1;
    }

    private Map<String,Object> addUser(int userId){
        Map<String,Object> newMap = new HashMap<>();
        SUser sUser = sUserDao.selectByPrimaryKey(userId);
        switch (sUser.getUserType()){
            case "teacher":{
                STeacher sTeacher = sTeacherDao.getTeacherByUserId(userId);
                newMap.put("teacher",sTeacher);
                newMap.put("type","teacher");
                break;
            }
            case "student":{
                SStudent sStudent = sStudentDao.getStudentByUserId(userId);
                newMap.put("student",sStudent);
                newMap.put("type","student");
                break;
            }
            case "root": {
                SRoot sRoot = sRootDao.getRootByUserId(userId);
                newMap.put("root",sRoot);
                newMap.put("type","root");
                break;
            }
        }
        return newMap;
    }
}
