package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * s_class
 * @author 
 */
@Data
public class SClass implements Serializable {
    private Integer sClassId;

    /**
     * 班级号 例如 高二一班
     */
    private String classNoName;

    /**
     * 班级别称 例如  起航
     */
    private String classNickName;

    /**
     * 班级图标
     */
    private String classUrl;

    /**
     * 班级口号 例如 追求卓越
     */
    private String classWatchword;

    /**
     * 创建时间
     */
    private Date cTime;

    private Date uTime;

    /**
     * 班级简介
     */
    private String classConf;

    /**
     * 班级荣耀  例如 先进班级
     */
    private String classHonor;

    /**
     * 班级称号 例如  卓越
     */
    private String classDesignation;

    private static final long serialVersionUID = 1L;
}