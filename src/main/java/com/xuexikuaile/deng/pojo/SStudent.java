package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * s_student
 * @author 
 */
@Data
public class SStudent implements Serializable {
    private Integer sStudentId;

    /**
     * 学生真实姓名
     */
    private String studentName;

    /**
     * 学生证件照
     */
    private String studentUrl;

    /**
     * 学生昵称
     */
    private String studentNickName;

    /**
     * 学生自定义头像url
     */
    private String studentNickUrl;

    /**
     * 学生荣誉 例如 全国数学竞赛第一名
     */
    private String studentHonor;

    /**
     * 学生简介 允许自由填写
     */
    private String studentConf;

    /**
     * 学生称号 例如  学霸
     */
    private String studentDesignation;

    /**
     * 班级id
     */
    private Integer classId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uTime;

    private Integer userId;

    private static final long serialVersionUID = 1L;
}