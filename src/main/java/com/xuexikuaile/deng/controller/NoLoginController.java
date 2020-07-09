package com.xuexikuaile.deng.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cytern
 * @Date: 2020/6/10 16:51
 */
@RestController
public class NoLoginController {
    @RequestMapping("error/needLogin")
    public String noLogin(){
        String needLogin = "请先登录";
        return needLogin;
    }
}
