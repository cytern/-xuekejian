package com.xuexikuaile.deng;

import com.xuexikuaile.deng.dao.SStudentDao;
import com.xuexikuaile.deng.pojo.SStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Author: cytern
 * @Date: 2020/6/11 11:10
 */
@Component
public class GetGrade {
    @Autowired
    private SStudentDao sStudentDao;

    Random random = new Random();


    public BigDecimal get100Score(){
          return getRandomScore();
    }
    public BigDecimal get150Score(){
return getRandomScore().multiply(BigDecimal.valueOf(1.5));
    }
    public BigDecimal getRandomScore(){
        BigDecimal bigDecimal =new BigDecimal(0);
        int a = random.nextInt(21);
        int score = 0;
        if (a <= 1){
            score =random.nextInt(10) + 25;
        }else if (a>1 && a<=5 ){
            score= random.nextInt(10) +35;
        }else if (a>5 && a<=10){
            score = random.nextInt(10) +45;
        }else if (a>10 && a<=18){
            score = random.nextInt(30) +55;
        }else if (a>18 && a<=19){
            score = random.nextInt(8) +85;
        }else if (a == 20){
            score = random.nextInt(8) + 93;
        }
        bigDecimal= BigDecimal.valueOf(score);
        return bigDecimal;
    }
}
