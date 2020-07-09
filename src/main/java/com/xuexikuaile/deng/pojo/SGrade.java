package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * s_grade
 * @author 
 */
@Data
public class SGrade implements Serializable {
    private Integer sGradeId;

    /**
     * 语文
     */
    private BigDecimal gChinese;

    /**
     * 英语
     */
    private BigDecimal gEnglish;

    /**
     * 数学
     */
    private BigDecimal gMath;

    /**
     * 物理
     */
    private BigDecimal gPysics;

    /**
     * 生物
     */
    private BigDecimal gBiology;

    /**
     * 化学
     */
    private BigDecimal gChemistry;

    /**
     * 历史
     */
    private BigDecimal gHistory;

    /**
     * 政治
     */
    private BigDecimal gPolitics;

    /**
     * 地理
     */
    private BigDecimal gGeography;

    private Integer testId;

    private Integer studentId;

    private String gradeStatus;

    private Integer classId;

    private BigDecimal totalPrice;

    private static final long serialVersionUID = 1L;
}