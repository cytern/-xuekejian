package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * s_test
 * @author 
 */
@Data
public class STest implements Serializable {
    private Integer sTestId;

    /**
     * 考试名称
     */
    private String testName;

    /**
     * 开始时间
     */
    private Date testTime;

    private Integer testStartUserId;

    private Date cTime;

    private String testType;

    private static final long serialVersionUID = 1L;
}