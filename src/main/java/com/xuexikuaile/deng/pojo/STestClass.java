package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * s_test_class
 * @author 
 */
@Data
public class STestClass implements Serializable {
    /**
     * 考试班级表/多对多关联考试与班级
     */
    private Integer testClassId;

    private Integer classId;

    private Integer testId;

    private Date cTime;

    private static final long serialVersionUID = 1L;
}