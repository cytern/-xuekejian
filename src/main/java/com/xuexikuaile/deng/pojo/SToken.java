package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * s_token
 * @author 
 */
@Data
public class SToken implements Serializable {
    private Integer sTokenId;

    private String token;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cTime;

    private Integer userId;

    private String userType;

    private static final long serialVersionUID = 1L;
}