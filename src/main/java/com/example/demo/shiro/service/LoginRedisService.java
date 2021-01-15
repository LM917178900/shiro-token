package com.example.demo.shiro.service;


import com.example.demo.shiro.jwt.JwtToken;
import com.example.demo.shiro.model.LoginUserRedisVo;
import com.example.demo.shiro.model.LoginUserVo;

/**
 * 登陆信息Redis缓存操作服务
 *
 * @author geekidea
 * @date 2019-09-30
 * @since 1.3.0.RELEASE
 **/
public interface LoginRedisService {

    /**
     * 缓存登陆信息
     *
     * @param jwtToken
     * @param loginSysUserVo
     * @param generate       true:生成，false:刷新
     */
    void cacheLoginInfo(JwtToken jwtToken, LoginUserVo loginSysUserVo, boolean generate);

	/**
	 * 刷新登陆信息
	 * @param oldToken
	 * @param username
	 * @param newJwtToken
	 */
	void refreshLoginInfo(String oldToken,String username,JwtToken newJwtToken);

	/**
     * 通过token，从缓存中获取登陆用户LoginSysUserRedisVo
     *
     * @param username
     * @return
     */
    LoginUserRedisVo getLoginUserRedisVo(String username);

    LoginUserVo getLoginUserVo(String username);

    /**
     * 通过用户名称获取盐值
     *
     * @param username
     * @return
     */
    String getSalt(String username);

    /**
     * 删除对应用户的Redis缓存
     *
     * @param token
     * @param username
     */
    void deleteLoginInfo(String token, String username);

    /**
     * 判断token在redis中是否存在
     *
     * @param token
     * @return
     */
    boolean exists(String token);

    /**
     * 删除用户所有登陆缓存
     * @param username
     */
    void deleteUserAllCache(String username);

}
