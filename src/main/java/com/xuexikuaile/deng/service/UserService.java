package com.xuexikuaile.deng.service;

import com.xuexikuaile.deng.dao.SRootDao;
import com.xuexikuaile.deng.dao.SStudentDao;
import com.xuexikuaile.deng.dao.STeacherDao;
import com.xuexikuaile.deng.dao.SUserDao;
import com.xuexikuaile.deng.pojo.SRoot;
import com.xuexikuaile.deng.pojo.SStudent;
import com.xuexikuaile.deng.pojo.STeacher;
import com.xuexikuaile.deng.pojo.SUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: cytern
 * @Date: 2020/7/3 9:46
 */
@Component
public class UserService {
    @Autowired
    private SUserDao sUserDao;
    @Autowired
    private SStudentDao sStudentDao;
    @Autowired
    private STeacherDao sTeacherDao;
    @Autowired
    private SRootDao sRootDao;

    public String getUserRealName(Integer userId){
        SUser sUser = sUserDao.selectByPrimaryKey(userId);
        if (sUser.getUserType().contains("student")){
            SStudent sStudent = sStudentDao.getStudentByUserId(userId);
            try {
                if (sStudent.getStudentName() == null){
                    return "无效的用户名";
                }
            } catch (Exception e) {
                return "无效的用户id";
            }
            return sStudent.getStudentName();
        }else if (sUser.getUserType().contains("teacher")){
            STeacher sTeacher = sTeacherDao.getTeacherByUserId(userId);
            try {
                if (sTeacher.getTeacherName() == null){
                    return "无效的用户名";
                }
            } catch (Exception e) {
                return "无效的用户id";
            }
            return sTeacher.getTeacherName();
        }else if (sUser.getUserType().contains("root")){
            SRoot sRoot =sRootDao.getRootByUserId(userId);
            try {
                if (sRoot.getSRootName() == null){
                    return "无效的用户名";
                }
            } catch (Exception e) {
                return "无效的用户id";
            }
            return sRoot.getSRootName();
        }
        return "无效的用户id";
    }
}
