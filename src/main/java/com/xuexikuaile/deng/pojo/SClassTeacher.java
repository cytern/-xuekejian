package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * s_class_teacher
 * @author 
 */
@Data
public class SClassTeacher implements Serializable {
    private Integer sClassTeacherId;

    private Integer classId;

    private Integer teacherId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uTime;

    private static final long serialVersionUID = 1L;
}