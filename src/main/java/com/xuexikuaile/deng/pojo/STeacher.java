package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * s_teacher
 * @author 
 */
@Data
public class STeacher implements Serializable {
    private Integer sTeacherId;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 教师证件照
     */
    private String teacherUrl;

    /**
     * 教师简介
     */
    private String teacherConf;

    private Integer userId;

    /**
     * 教师电话号码
     */
    private String teacherPhone;

    /**
     * 教师教育经历
     */
    private String teacherEducation;

    /**
     * 教师称号  例如  博学多才
     */
    private String teacherDesignation;

    /**
     * 教师荣誉 例如 先进教师
     */
    private String teacherHonor;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uTime;

    /**
     * 教师教授何种课
     */
    private String teacherType;

    private static final long serialVersionUID = 1L;
}