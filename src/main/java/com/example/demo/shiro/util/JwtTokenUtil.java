package com.example.demo.shiro.util;

import com.example.demo.shiro.jwt.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * JwtToken工具类
 *
 * @author geekidea
 * @date 2019-10-03
 * @since 1.3.0.RELEASE
 **/
@Slf4j
@Component
public class JwtTokenUtil {

    private static String tokenName;

    public JwtTokenUtil(JwtProperties jwtProperties) {
        tokenName = jwtProperties.getTokenName();
        log.debug("tokenName:{}", tokenName);
    }

    /**
     * 获取token名称
     *
     * @return
     */
    public static String getTokenName() {
        return tokenName;
    }

    /**
     * 从请求头或者请求参数中
     *
     * @return
     */
    public static String getToken() {
        return getToken(HttpServletRequestUtil.getRequest());
    }

    /**
     * 从请求头或者请求参数中
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request不能为空");
        }
        // 从请求头中获取token
        String token = request.getHeader(tokenName);
        if (StringUtils.isBlank(token)) {
            // 从请求参数中获取token
            token = request.getParameter(tokenName);
        }
        return token;
    }
}
