package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * grade_num
 * @author 
 */
@Data
public class GradeNum implements Serializable {
    private Integer gradeNumId;

    private String gradeName;

    private Integer gradeNum;

    private static final long serialVersionUID = 1L;
}