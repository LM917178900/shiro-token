package com.example.demo.shiro.model;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 * 登录用户对象，响应给前端
 * </p>
 *
 * @author geekidea
 * @date 2019-05-15
 **/
@Data
@Accessors(chain = true)
public class LoginUserVo implements Serializable {

    private static final long serialVersionUID = -1758338570596088158L;

    private Integer userID;

    private String itCode;

    private String password;

    private Set<String> roles;

    private JSONArray permissions;

    private ClientInfo clientInfo;

    private String email;

    private String realname;

    private Integer type;

    private String department;

	private String manager;
}
