package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * grade_rule
 * @author 
 */
@Data
public class GradeRule implements Serializable {
    private Integer gradeRuleId;

    /**
     * 平均分>?>最高分》？>个人评分？
     */
    private String gradeType;

    /**
     * 测试id
     */
    private Integer gradeTestId;

    private BigDecimal bioPrice;

    private BigDecimal chinesePrice;

    private BigDecimal englishPrice;

    private BigDecimal mathPrice;

    private BigDecimal chePrice;

    private BigDecimal phyPrice;

    private BigDecimal hisPrice;

    private BigDecimal polPrice;

    private BigDecimal geoPrice;

    /**
     * 如果是个人评分，那么会有student
     */
    private Integer studentId;

    private BigDecimal sciencePrice;

    private BigDecimal artsPrice;

    /**
     * 0为分数rule，1为名次rule
     */
    private Integer scoreorgit;

    private BigDecimal totalPrice;

    private static final long serialVersionUID = 1L;
}