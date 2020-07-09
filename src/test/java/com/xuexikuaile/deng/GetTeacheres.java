package com.xuexikuaile.deng;

import com.xuexikuaile.deng.dao.SClassDao;
import com.xuexikuaile.deng.plugin.ChangeDate;
import com.xuexikuaile.deng.pojo.SClass;
import com.xuexikuaile.deng.pojo.STeacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author: cytern
 * @Date: 2020/6/10 21:29
 */
@Component
public class GetTeacheres {
@Autowired
private SClassDao sClassDao;

ChangeDate changeDate =new ChangeDate();

ChineseName chineseName =new ChineseName();

GetRandomUser getRandomUser =new GetRandomUser();

Random random =new Random();
    public List<STeacher> getTeachers(){
        List<SClass> sClasses = sClassDao.getAllClass();
        List<STeacher> teachers = new ArrayList<>();
        String[] types = {"g_chinese","g_english","g_math","g_pysics","g_biology","g_chemistry","g_history","g_politics","g_geography"};
        String[] des = {"桃李满天下","严于律己","孩子王","人生大师","文质彬彬","风趣幽默","得心应手"};
        String[] degree={"本科","硕士","博士","博士后"};
        String[] honor={"优秀教师","杰出青年","学术带头人","","","","","","","","","","","","","","","","","","",""};
        String[] col = {"复旦大学","上海交通大学","中国科学技术大学","南京大学","浙江大学","中国人民大学","同济大学","南开大学","西安交通大学","华中科技大学","武汉大学","东南大学","电子科技大学","中山大学","四川大学"};
        List<Map<String,String>> maps = new ArrayList<>();
        for (SClass sClass:sClasses){
                 for (int i =0;i<types.length;i++){
                     STeacher sTeacher = new STeacher();
                     sTeacher.setCTime(changeDate.getDate());
                     sTeacher.setUTime(changeDate.getDate());
                     sTeacher.setTeacherConf(chineseName.getManName()+chineseName.getManName());
                     sTeacher.setTeacherDesignation(des[random.nextInt(des.length)]);
                     sTeacher.setTeacherEducation(col[random.nextInt(col.length)]+degree[random.nextInt(degree.length)]);
                     sTeacher.setTeacherHonor(honor[random.nextInt(honor.length)]);
                     sTeacher.setTeacherPhone(getRandomUser.getTel());
                     sTeacher.setTeacherName(chineseName.getManName());
                     sTeacher.setTeacherType(types[i]);
                     sTeacher.setTeacherUrl(String.valueOf(sClass.getSClassId()));
                     teachers.add(sTeacher);
                 }
        }
        return teachers;
    }
}
