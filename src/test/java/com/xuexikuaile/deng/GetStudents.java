package com.xuexikuaile.deng;

import com.xuexikuaile.deng.dao.SClassDao;
import com.xuexikuaile.deng.plugin.ChangeDate;
import com.xuexikuaile.deng.pojo.SClass;
import com.xuexikuaile.deng.pojo.SStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: cytern
 * @Date: 2020/6/10 22:35
 */
@Component
public class GetStudents {
    @Autowired
    private SClassDao sClassDao;

    ChangeDate changeDate =new ChangeDate();

    Random random =new Random();

    ChineseName chineseName =new ChineseName();
    public List<SStudent> getStudents(){
        List<SStudent> sStudents =new ArrayList<>();
        List<SClass> sClasses =sClassDao.getAllClass();
        String[] des = {"学霸","学弱","学神","精神小伙","葬爱家族","中二患者","多面体","小透明"};
        String[] honor = {"六边形战神","全能战神","优秀学生","优秀班干部","","","","","","","","","","","","","","","","","",""};
        for (SClass sClass :sClasses){
            for (int i=0;i<61;i++){
                SStudent sStudent = new SStudent();
                sStudent.setClassId(sClass.getSClassId());
                sStudent.setStudentConf("这个人很懒，什么都没有写");
                sStudent.setStudentDesignation(des[random.nextInt(des.length)]);
                sStudent.setClassId(sClass.getSClassId());
                sStudent.setCTime(changeDate.getDate());
                sStudent.setStudentHonor(honor[random.nextInt(honor.length)]);
                sStudent.setStudentName(chineseName.getManName());
                sStudent.setStudentNickName(chineseName.getManName());
                sStudent.setUTime(changeDate.getDate());
                sStudents.add(sStudent);
            }
        }
        return sStudents;
    }
}
