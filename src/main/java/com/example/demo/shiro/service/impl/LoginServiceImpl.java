package com.example.demo.shiro.service.impl;

import com.example.demo.shiro.jwt.JwtProperties;
import com.example.demo.shiro.jwt.JwtToken;
import com.example.demo.shiro.model.ApiResult;
import com.example.demo.shiro.model.LoginParam;
import com.example.demo.shiro.service.LoginService;
import com.example.demo.shiro.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.List;

/**
 * @description: LoginServiceImpl
 * @author: leiming5
 * @date: 2021-01-15 14:53
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JwtProperties jwtProperties;

    public JwtToken login(String itcode){

        // 获取加密的盐
        //使用默认盐值:LENOVO@QIT^ABC$$
        String salt = jwtProperties.getSecret();
        // 生成token字符串并返回，失效时间1小时；
        Duration expireDuration = Duration.ofSeconds(jwtProperties.getExpireSecond());

        /**
         * 生成token:每个人的token是固定的，只要认证了，下次可以继续使用
         * jwtToken：也是固定的，登录后，下次继续用；
         * shiro认证登录：subject.login(jwtToken)
         */
        String token = JwtUtil.generateToken(itcode, salt, expireDuration);
        log.debug("token:{}", token);


        // 使用 itcode\token\salt\expireTime 完成shiro 认证注册；
        // 创建AuthenticationToken
        JwtToken jwtToken = JwtToken.build(token, itcode, salt, jwtProperties.getExpireSecond());

        return jwtToken;
    }

    public void shiroLogin(JwtToken jwtToken){
        // 从SecurityUtils里边创建一个 subject
//        Subject subject = SecurityUtils.getSubject();
        // 执行认证登陆
//        subject.login(jwtToken);
    }

    /**
     * 登陆
     *
     * @param loginParam
     * @param response
     * @return
     */
    @Override
    public ApiResult login(LoginParam loginParam, HttpServletResponse response) {
        return null;
    }

    /**
     * 如果(当前时间+倒计时[10分钟]) > 过期时间，则刷新token
     * 并更新缓存
     * 当前token失效，返回新token
     * 当前请求有效，返回状态码：460
     * 前端下次使用新token
     * 如果token继续发往后台，则提示，此token已失效，请使用新token，不在返回新token，返回状态码：461
     *
     * @param jwtToken
     * @param httpServletResponse
     */
    @Override
    public void refreshToken(JwtToken jwtToken, HttpServletResponse httpServletResponse) {

    }

    /**
     * 退出
     *
     * @param request
     */
    @Override
    public void logout(HttpServletRequest request) {

    }

    /**
     * 获取用户角色
     *
     * @param id
     * @return
     */
    @Override
    public List<String> getUserRoles(Long id) {
        return null;
    }

    /**
     * 外部用户登录接口
     *
     * @param loginParam
     * @param response
     * @return
     */
    @Override
    public ApiResult extLogin(LoginParam loginParam, HttpServletResponse response) {
        return null;
    }
}
