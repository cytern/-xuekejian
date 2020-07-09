package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * s_user
 * @author 
 */
@Data
public class SUser implements Serializable {
    private Integer userId;

    /**
     * 账号
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 类型
     */
    private String userType;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cTime;

    /**
     * 最近修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uTime;

    private static final long serialVersionUID = 1L;
}