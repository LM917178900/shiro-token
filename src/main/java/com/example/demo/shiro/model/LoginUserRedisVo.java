package com.example.demo.shiro.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 登录用户Redis对象，后台使用
 *
 * @author geekidea
 * @date 2019-9-30
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class LoginUserRedisVo extends LoginUserVo {

    private static final long serialVersionUID = 2783720332894188358L;

    /**
     * 登录ip
     */
    private ClientInfo clientInfo;

}
