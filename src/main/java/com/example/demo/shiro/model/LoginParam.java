package com.example.demo.shiro.model;

import lombok.Data;


/**
 * 登录参数
 *
 * @author geekidea
 * @date 2019-05-15
 **/
@Data
public class LoginParam {

    private String username;

    private String password;

    private String code;

    private String token;

    private Integer type;

}
