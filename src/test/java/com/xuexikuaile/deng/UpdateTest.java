package com.xuexikuaile.deng;

import com.xuexikuaile.deng.dao.STestDao;
import com.xuexikuaile.deng.plugin.ChangeDate;
import com.xuexikuaile.deng.pojo.STest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @Author: cytern
 * @Date: 2020/6/12 14:52
 */
@SpringBootTest
public class UpdateTest {
    @Autowired
    private STestDao sTestDao;

    ChangeDate changeDate = new ChangeDate();
    @Test
    void update() throws ParseException {
        String[] year = {"2018年","2019年","2020年"};
        String[] test = {"上学年第一次月考","上学年期中考试","上学年第二次月考","上学年期末考试","下学年第一次月考","下学年期中考试","下学年第二次月考","下学年期末考试"};
        List<STest> sTests = sTestDao.getAllTest();
        for (STest sTest:sTests){
            String name = sTest.getTestName();
            if (name.contains("2018")){
                String month = getMonth(name);
                String date = "2018-" +month + " 12:00:00";
                Date useDate = changeDate.changeDate(date);
                sTest.setTestTime(useDate);
                sTest.setTestType("allTest");
                sTestDao.updateByPrimaryKeySelective(sTest);

            }else if (name.contains("2019")){
                String month = getMonth(name);
                String date = "2019-" +month + " 12:00:00";
                Date useDate = changeDate.changeDate(date);
                sTest.setTestTime(useDate);
                sTest.setTestType("allTest");
                sTestDao.updateByPrimaryKeySelective(sTest);
            }else if (name.contains("2020")){
                String month = getMonth(name);
                String date = "2020-" +month + " 12:00:00";
                Date useDate = changeDate.changeDate(date);
                sTest.setTestTime(useDate);
                sTest.setTestType("allTest");
                sTestDao.updateByPrimaryKeySelective(sTest);
            }
        }
    }

    public String getMonth(String name){
        if (name.contains("上学年第一次月考")){
            return "10-13";
        }else if (name.contains("上学年期中考试")){
            return "11-13";
        }else if (name.contains("上学年第二次月考")){
            return "12-13";
        }else if (name.contains("上学年期末考试")){
            return "01-23";
        }else if (name.contains("下学年第一次月考")){
            return "03-13";
        }else if (name.contains("下学年期中考试")){
            return "04-13";
        }else if (name.contains("下学年第二次月考")){
            return "05-13";
        }else if (name.contains("下学年期末考试")){
            return "06-23";
        }else {
            return null;
        }
    }

}
