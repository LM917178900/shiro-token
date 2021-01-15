package com.example.demo.shiro.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.shiro.util.IpUtil;
import com.example.demo.shiro.util.JwtUtil;
import lombok.Data;
import lombok.experimental.Accessors;
//import org.apache.shiro.authc.HostAuthenticationToken;

import java.util.Date;

/**
 * Shiro JwtToken对象
 *
 * @author geekidea
 * @date 2019-09-27
 * @since 1.3.0.RELEASE
 **/
@Data
@Accessors(chain = true)
public class JwtToken
//        implements HostAuthenticationToken
{
    /**
     * 登陆ip
     */
    private String host;
    /**
     * 登陆用户名称
     */
    private String username;
    /**
     * 登陆盐值
     */
    private String salt;
    /**
     * 登陆token
     */
    private String token;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 多长时间过期，默认一小时
     */
    private long expireSecond;
    /**
     * 过期日期
     */
    private Date expireDate;

    private String principal;

    private String credentials;

//    @Override
    public Object getPrincipal() {
        return token;
    }

//    @Override
    public Object getCredentials() {
        return token;
    }

    public static JwtToken build(String token, String username, String salt, long expireSecond) {
        DecodedJWT decodedJWT = JwtUtil.getJwtInfo(token);
        Date createDate = decodedJWT.getIssuedAt();
        Date expireDate = decodedJWT.getExpiresAt();
        return new JwtToken()
                .setUsername(username)
                .setToken(token)
                .setHost(IpUtil.getRequestIp())
                .setSalt(salt)
                .setCreateDate(createDate)
                .setExpireSecond(expireSecond)
                .setExpireDate(expireDate);

    }

}
