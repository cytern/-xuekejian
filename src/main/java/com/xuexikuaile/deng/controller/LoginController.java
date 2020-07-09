package com.xuexikuaile.deng.controller;

import com.xuexikuaile.deng.dao.STokenDao;
import com.xuexikuaile.deng.dao.SUserDao;
import com.xuexikuaile.deng.plugin.ChangeDate;
import com.xuexikuaile.deng.pojo.SToken;
import com.xuexikuaile.deng.pojo.SUser;
import com.xuexikuaile.deng.service.TotalUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: cytern
 * @Date: 2020/6/10 14:32
 */
@RestController
@Api(tags = "登录接口")
public class LoginController {
    @Autowired
    private SUserDao sUserDao;
    @Autowired
    TotalUser totalUser = new TotalUser();
    @Autowired
    private STokenDao sTokenDao;

    ChangeDate changeDate = new ChangeDate();
    @RequestMapping("everyOne/login")
    @ApiOperation(value = "登录")
    public Map<String,Object> loginEveryOne(@RequestParam String userName,@RequestParam String userPassword){
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("准备登录");
        System.out.println("准备登录");
        SUser sUser1 = sUserDao.getUserByUserName(userName);
        System.out.println(sUser1);
        System.out.println(userPassword);
        Map<String,Object> user = new HashMap<>();
        try {
            if (null != sUser1.getUserId()){
                if (sUser1.getUserPassword().equals(userPassword)){
                    user.put("user",sUser1);
                    Map<String,Object> map  =   totalUser.getTotalUser(user,sUser1.getUserId());
                    String token = "" + changeDate.getTime() + map.hashCode();
                    SToken sToken = new SToken();
                    sToken.setCTime(changeDate.getDate());
                    sToken.setToken(token);
                    sToken.setUserId(sUser1.getUserId());
                    sToken.setUserType(sUser1.getUserType());
                     sTokenDao.insertSelective(sToken);
                     if (sToken.getSTokenId() == 0){
                         logger.error("token注入失败");
                     }
                    map.put("token",token);
                     logger.info("用户id:"+sUser1.getUserId() + " " + "登录成功");
                    return map;

                }
                else {
                    user.put("error","用户名或密码错误");
                    logger.warn("用户id" + sUser1.getUserId() + " " + "登录失败，用户名或密码错误"); ;
                    return user;
                }
            }else {
              user.put("error","无效的用户名");
              logger.warn("登录失败，无效的用户名");
              return user;
            }
        } catch (Exception e) {
            user.put("error","用户名或密码错误");
            return user;
        }

    }
}
