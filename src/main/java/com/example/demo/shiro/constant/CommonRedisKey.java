package com.example.demo.shiro.constant;

/**
 * <p>
 *  redis key 常量
 * </p>
 * @author geekidea
 * @date 2019-05-23
 **/
public interface CommonRedisKey {

    /**
     * 登陆用户token信息key
     */
    String LOGIN_TOKEN = "login:token:%s";

    /**
     * 登陆用户信息key
     */
    String LOGIN_USER = "login:user:%s";

    /**
     * 登陆用户盐值信息key
     */
    String LOGIN_SALT= "login:salt:%s";

    /**
     * 登陆用户username token
     */
    String LOGIN_USER_TOKEN = "login:user:token:%s:%s";

}
