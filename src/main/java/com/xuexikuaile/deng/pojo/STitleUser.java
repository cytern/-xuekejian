package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * s_title_user
 * @author 
 */
@Data
public class STitleUser implements Serializable {
    /**
     * 公告用户表/用于分类推送消息提示
     */
    private Integer titleUserId;

    private Integer titleId;

    private Integer userId;

    private Date cTime;

    private static final long serialVersionUID = 1L;
}