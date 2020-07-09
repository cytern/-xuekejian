package com.xuexikuaile.deng.controller;

import com.xuexikuaile.deng.dao.*;
import com.xuexikuaile.deng.plugin.ChangeDate;
import com.xuexikuaile.deng.pojo.*;
import com.xuexikuaile.deng.service.StudentConf;
import com.xuexikuaile.deng.service.TitleService;
import com.xuexikuaile.deng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * @Author: cytern
 * @Date: 2020/6/11 15:51
 */
@RestController
public class StudentOneController {
    @Autowired
   private SStudentDao sStudentDao;
    @Autowired
    private SClassDao sClassDao;
    @Autowired
    private GradeNumDao gradeNumDao;
    @Autowired
    StudentConf studentConf = new StudentConf();
      @Autowired
    private STitleUserDao sTitleUserDao;
     @Autowired
      private STitleDao sTitleDao;
     @Autowired
     private STokenDao sTokenDao;
     @Autowired
     private UserService userService;
     @Autowired
     private TitleService titleService;
     @RequestMapping("student/getMyAbility")
    public Map<String,Object> getStudentMainConf(@RequestParam(defaultValue = "0",required = true)Integer studentId){
         Map<String,Object> map = studentConf.mainMethod(studentId);
         return map;
    }

    @RequestMapping("student/getTiles")
    public List<Map<String,Object>> getTitles(@RequestParam(defaultValue = "0",required = true)Integer userId){
         List<STitleUser> sTitleUsers = sTitleUserDao.getByUserId(userId);
        ChangeDate changeDate = new ChangeDate();
         if (sTitleUsers.size()<1){
             return null;
         }
         List<Map<String,Object>> map = new ArrayList<>();
         for (STitleUser sTitleUser:sTitleUsers){
            STitle sTitle = sTitleDao.selectByPrimaryKey(sTitleUser.getTitleId());
          Map<String,Object> map1 = new HashMap<>();
             try {
                 map1.put("sTitleId",sTitle.getSTitleId());

             } catch (Exception e) {
               break;
             }
             try {
                 map1.put("sTitleTitle",sTitle.getSTitleTitle());
             } catch (Exception e) {
                 map1.put("sTitleTitle",null);
             }
             try {
                 map1.put("startUserId",sTitle.getStartUserId());
                 map1.put("startName",userService.getUserRealName(sTitle.getStartUserId()));
             } catch (Exception e) {
               break;
             }
             try {
                 map1.put("sTitleConf",sTitle.getSTitleConf());
             } catch (Exception e) {
                 map1.put("sTitleConf",null);
             }
             try {
                 map1.put("cTime",changeDate.changeTime(sTitle.getCTime()));
                 new Date().getTime();
                 if (sTitle.getCTime().getTime() < changeDate.getDate().getTime()){
                     map1.put("isStart",true);
                 }else {
                     map1.put("isStart",false);
                 }
             } catch (Exception e) {
                break;
             }
             try {
                 map1.put("endTime",changeDate.changeTime(sTitle.getEndTime()));
                 if (sTitle.getEndTime().getTime() < changeDate.getDate().getTime()){
                     map1.put("isEnd",true);
                 }else {
                     map1.put("isEnd",false);
                 }
             } catch (Exception e) {
                 break;
             }
             try {
                 map1.put("titleType",sTitle.getTitleType());
             } catch (Exception e) {
                 map1.put("titleType",null);
             }
             try {

                 if (sTitle.getStartUserId().equals(userId)){
                  map1.put("isMine",true);
                    }else{
                  map1.put("isMine",false);
                    }
             } catch (Exception e) {
                 break;
             }
             map.add(map1);
         }
         return map;
    }
    @PostMapping("student/addTitle/{titleType}")
    public Map<String,Object> addTitles(@RequestParam String startUserId,
                                        @RequestParam String sTitleConf,
                                        @RequestParam String cTime,
                                        @RequestParam String endTime,
                                        @RequestParam String sTitleTitle,
                                        @PathVariable  String titleType, HttpServletRequest request) throws ParseException {
         ChangeDate changeDate = new ChangeDate();
        Map<String,Object> map = new HashMap<>();
        String token = "";
        STitle sTitle = new STitle();
        sTitle.setStartUserId(Integer.valueOf(startUserId));
        sTitle.setSTitleConf(sTitleConf);
        sTitle.setCTime(changeDate.changeDate(cTime));
        sTitle.setEndTime(changeDate.changeDate(endTime));
        sTitle.setSTitleTitle(sTitleTitle);
         try {
           token = request.getHeader("User-Token");
        } catch (Exception e) {
             map.put("error","过期登录");
             return map;
        }
        SToken sToken = sTokenDao.getTokenByToken(token);
        try {
            if (sToken.getUserId() ==0 || sToken.getUserId() ==null){
                map.put("error","过期登录");
                return map;
            }
        } catch (Exception e) {
            map.put("error","过期登录");
            return map;
        }
        if (!sTitle.getStartUserId().equals(sToken.getUserId())){
            map.put("error","错误的使用他人信息");
            return map;
        }
          map = titleService.addTitle(sTitle.getStartUserId(),titleType,sTitle);
        return map;

    }
    @GetMapping("student/deleteTitle/{titleId}")
    public Map<String,Object> deleteTitle(HttpServletRequest request,@PathVariable Integer titleId){
        Map<String,Object> map = new HashMap<>();
        String token = "";
        try {
            token = request.getHeader("User-Token");
        } catch (Exception e) {
            map.put("error","过期登录");
            return map;
        }
        SToken sToken = sTokenDao.getTokenByToken(token);
        try {
            if (sToken.getUserId() ==0 || sToken.getUserId() ==null){
                map.put("error","过期登录");
                return map;
            }
        } catch (Exception e) {
            map.put("error","过期登录");
            return map;
        }
        STitle sTitle = sTitleDao.selectByPrimaryKey(titleId);
        if (!sTitle.getStartUserId().equals(sToken.getUserId())){
            map.put("error","错误的使用他人信息");
            return map;
        }
        map = titleService.deleteTitle(titleId);
        return map;
    }
    @PostMapping("student/updateTitle")
    public Map<String,Object> editTitle(@RequestParam Integer sTitleId,
            @RequestParam String startUserId,
                                        @RequestParam String sTitleConf,
                                        @RequestParam String cTime,
                                        @RequestParam String endTime,
                                        @RequestParam String sTitleTitle,
                                        @RequestParam  String titleType,HttpServletRequest request) throws ParseException {
        Map<String,Object> map = new HashMap<>();
        ChangeDate changeDate = new ChangeDate();
        STitle sTitle = new STitle();
        sTitle.setStartUserId(Integer.valueOf(startUserId));
        sTitle.setSTitleConf(sTitleConf);
        sTitle.setCTime(changeDate.changeDate(cTime));
        sTitle.setEndTime(changeDate.changeDate(endTime));
        sTitle.setSTitleTitle(sTitleTitle);
        sTitle.setSTitleId(sTitleId);
        String token = "";
        try {
            token = request.getHeader("User-Token");
        } catch (Exception e) {
            map.put("error","过期登录");
            return map;
        }
        SToken sToken = sTokenDao.getTokenByToken(token);
        try {
            if (sToken.getUserId() ==0 || sToken.getUserId() ==null){
                map.put("error","过期登录");
                return map;
            }
        } catch (Exception e) {
            map.put("error","过期登录");
            return map;
        }
        STitle sTitle2 = sTitleDao.selectByPrimaryKey(sTitle.getSTitleId());
        if (!sTitle2.getStartUserId().equals(sToken.getUserId())){
            map.put("error","错误的使用他人信息");
            return map;
        }
        System.out.println(sTitle + "id");
        map =  titleService.editTitle(sTitle);
        return map;
    }
}
