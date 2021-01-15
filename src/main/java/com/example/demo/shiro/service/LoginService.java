package com.example.demo.shiro.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.shiro.jwt.JwtToken;
import com.example.demo.shiro.model.ApiResult;
import com.example.demo.shiro.model.LoginParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 登录服务接口
 * </p>
 *
 * @author geekidea
 * @date 2019-05-23
 **/
public interface LoginService {

    /**
     * 登陆
     *
     * @param loginParam
     * @return
     */
    ApiResult login(LoginParam loginParam, HttpServletResponse response);

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
    void refreshToken(JwtToken jwtToken, HttpServletResponse httpServletResponse);

    /**
     * 退出
     *
     * @param request
     */
    void logout(HttpServletRequest request);

    /**
     * 获取用户角色
     *
     * @param id
     * @return
     */
    List<String> getUserRoles(Long id);

    /**
     * 外部用户登录接口
     * @param loginParam
     * @param response
     * @return
     */
	ApiResult extLogin(LoginParam loginParam, HttpServletResponse response);

    /**
     * 生成验证码，放入缓存，设置一分钟的有效期，并返回
     * 验证码为base64的图片
     * @return
     */
//	VerificationCodeVo getVerificationCode() throws Exception;

	/**
	 * 处理adfs回调接口
	 * adfs返回验证结果和相关属性
	 * @param request
	 * @param response
	 * @return
	 */
//	ApiResult adfsCallback(HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据token获取对应的用户信息
     * @param token
     * @return
     */
//    ApiResult getUserInfoByToken(String token);

    /**
     * 根据userMsg， 处理QES portal的登录操作
     * @param userInfo
     * @return
     */
//    ApiResult qesCallback(UserInfo userInfo, HttpServletResponse response) throws Exception;

    /**
     * 根据userMsg， 处理QES portal的登录操作
     * @param userInfo
     * @return
     */
//    ApiResult detailCallback(JSONObject userInfo, HttpServletResponse response) throws Exception;
}
