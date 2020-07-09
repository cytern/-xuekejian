package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * s_title
 * @author 
 */
@Data
public class STitle implements Serializable {
    /**
     * 公告id
     */
    private Integer sTitleId;

    /**
     * 公告标题
     */
    private String sTitleTitle;

    /**
     * 公告内容
     */
    private String sTitleConf;

    private Integer startUserId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    private String titleType;

    private static final long serialVersionUID = 1L;
}