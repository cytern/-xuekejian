package com.xuexikuaile.deng;

import com.xuexikuaile.deng.service.GetGradeRule;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

/**
 * @Author: cytern
 * @Date: 2020/6/28 15:24
 */

@Rollback
@SpringBootTest
public class GradeRule {
    @Autowired
    GetGradeRule getGradeRule = new GetGradeRule();
    @Test
    void getRule() throws InterruptedException {
        getGradeRule.addGradeRuleClass();
    }
}
