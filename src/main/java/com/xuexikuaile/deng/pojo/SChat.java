package com.xuexikuaile.deng.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * s_chat
 * @author 
 */
@Data
@ApiModel(value = "论坛",description = "论坛类")
public class SChat implements Serializable {
    private Integer sChatId;

    /**
     * 论坛标题
     */
    @ApiModelProperty(value = "论坛标题")
    private String sChatTitle;

    /**
     * 论坛内容
     */
    private String sChatConf;

    private String userId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cTime;

    private static final long serialVersionUID = 1L;
}
