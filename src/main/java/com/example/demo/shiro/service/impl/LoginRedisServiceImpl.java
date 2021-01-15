package com.example.demo.shiro.service.impl;

import com.example.demo.shiro.constant.CommonRedisKey;
import com.example.demo.shiro.jwt.JwtProperties;
import com.example.demo.shiro.jwt.JwtToken;
import com.example.demo.shiro.model.JwtTokenRedisVo;
import com.example.demo.shiro.model.LoginUserRedisVo;
import com.example.demo.shiro.model.LoginUserVo;
import com.example.demo.shiro.service.LoginRedisService;
import com.example.demo.shiro.util.ShiroMapstructConvert;
import com.example.demo.shiro.util.SysUserConvert;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * 登陆信息Redis缓存服务类
 *
 * @author geekidea
 * @date 2019-09-30
 * @since 1.3.0.RELEASE
 **/
@Service
public class LoginRedisServiceImpl implements LoginRedisService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * key-value: 有过期时间-->token过期时间
     * 1. tokenMd5:jwtTokenRedisVo
     * 2. username:loginUserRedisVo
     * 3. username:salt
     * hash: 没有过期时间，统计在线的用户信息
     * username:num
     */
    @Override
    public void cacheLoginInfo(JwtToken jwtToken, LoginUserVo loginUserVo, boolean generate) {
        if (jwtToken == null) {
            throw new IllegalArgumentException("jwtToken不能为空");
        }
        if (loginUserVo == null) {
            throw new IllegalArgumentException("loginUserVo不能为空");
        }
        // token
        String token = jwtToken.getToken();
        // 盐值
        String salt = jwtToken.getSalt();
        // 登陆用户名称
        String username = jwtToken.getUsername();
        // token md5值
        String tokenMd5 = DigestUtils.md5Hex(token);

        // Redis缓存JWT Token信息
        JwtTokenRedisVo jwtTokenRedisVo = ShiroMapstructConvert.INSTANCE.jwtTokenToJwtTokenRedisVo(jwtToken);

        // 用户客户端信息{集成adfs之后，回调地址不支持获取客户端信息，故屏蔽掉}
        //ClientInfo clientInfo = ClientInfoUtil.get(HttpServletRequestUtil.getRequest());

        // Redis缓存登陆用户信息
        // 将LoginUserVo对象复制到LoginUserRedisVo，使用mapstruct进行对象属性复制
        LoginUserRedisVo loginUserRedisVo = SysUserConvert.INSTANCE.loginUserVoToLoginUserRedisVo(loginUserVo);
        //loginUserRedisVo.setClientInfo(clientInfo);

        // Redis过期时间与JwtToken过期时间一致
        Duration expireDuration = Duration.ofSeconds(jwtToken.getExpireSecond());

        // 判断是否启用单个用户登陆，如果是，这每个用户只有一个有效token
        boolean singleLogin = jwtProperties.isSingleLogin();
        if (singleLogin) {
        	//fixme 修改为deleteLoginInfo方法，避免deleteUserAllCache方法中redis keys * 的问题，ics平台禁用了 *
	        //fixme 屏蔽单点登录配置，否则会导致因为删除缓存，而让部分接口因为获取不到缓存，返回401的bug
            //deleteLoginInfo(token, username);
        }

        // 1. tokenMd5:jwtTokenRedisVo
        String loginTokenRedisKey = String.format(CommonRedisKey.LOGIN_TOKEN, tokenMd5);
        redisTemplate.opsForValue().set(loginTokenRedisKey, jwtTokenRedisVo, expireDuration);
        // 2. username:loginUserRedisVo
        redisTemplate.opsForValue().set(String.format(CommonRedisKey.LOGIN_USER, username), loginUserRedisVo, expireDuration);
        // 3. salt hash,方便获取盐值鉴权
        redisTemplate.opsForValue().set(String.format(CommonRedisKey.LOGIN_SALT, username), salt, expireDuration);
        // 4. login user token
        redisTemplate.opsForValue().set(String.format(CommonRedisKey.LOGIN_USER_TOKEN, username, tokenMd5), loginTokenRedisKey, expireDuration);
    }

	@Override
	public void refreshLoginInfo(String oldToken, String username, JwtToken newJwtToken) {
		// 获取缓存的登陆用户信息
		LoginUserRedisVo loginUserRedisVo = getLoginUserRedisVo(username);
		// 删除之前的token信息
		deleteLoginInfo(oldToken, username);
		// 缓存登陆信息
		cacheLoginInfo(newJwtToken, loginUserRedisVo, false);
	}

	@Override
    public LoginUserRedisVo getLoginUserRedisVo(String username) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("username不能为空");
        }
        return (LoginUserRedisVo) redisTemplate.opsForValue().get(String.format(CommonRedisKey.LOGIN_USER, username));
    }

    @Override
    public LoginUserVo getLoginUserVo(String username) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("username不能为空");
        }
        LoginUserRedisVo userRedisVo = getLoginUserRedisVo(username);
        return userRedisVo;
    }

    @Override
    public String getSalt(String username) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("username不能为空");
        }
        String salt = (String) redisTemplate.opsForValue().get(String.format(CommonRedisKey.LOGIN_SALT, username));
        return salt;
    }

    @Override
    public void deleteLoginInfo(String token, String username) {
        if (token == null) {
            throw new IllegalArgumentException("token不能为空");
        }
        if (username == null) {
            throw new IllegalArgumentException("username不能为空");
        }
        String tokenMd5 = DigestUtils.md5Hex(token);
        // 1. delete tokenMd5
        redisTemplate.delete(String.format(CommonRedisKey.LOGIN_TOKEN, tokenMd5));
        // 2. delete username
        redisTemplate.delete(String.format(CommonRedisKey.LOGIN_USER, username));
        // 3. delete salt
        redisTemplate.delete(String.format(CommonRedisKey.LOGIN_SALT, username));
        // 4. delete user token
        redisTemplate.delete(String.format(CommonRedisKey.LOGIN_USER_TOKEN, username, tokenMd5));
    }

    @Override
    public boolean exists(String token) {
        if (token == null) {
            throw new IllegalArgumentException("token不能为空");
        }
        String tokenMd5 = DigestUtils.md5Hex(token);
        Object object = redisTemplate.opsForValue().get(String.format(CommonRedisKey.LOGIN_TOKEN, tokenMd5));
        return object != null;
    }

    @Override
    public void deleteUserAllCache(String username) {
        Set<String> userTokenMd5Set = redisTemplate.keys(String.format(CommonRedisKey.LOGIN_USER_TOKEN, username, "*"));
        if (CollectionUtils.isEmpty(userTokenMd5Set)) {
            return;
        }
        // 1. 删除登陆用户的所有token信息
        List<String> tokenMd5List = redisTemplate.opsForValue().multiGet(userTokenMd5Set);
        redisTemplate.delete(tokenMd5List);
        // 2. 删除登陆用户的所有user:token信息
        redisTemplate.delete(userTokenMd5Set);
        // 3. 删除登陆用户信息
        redisTemplate.delete(String.format(CommonRedisKey.LOGIN_USER, username));
        // 4. 删除登陆用户盐值信息
        redisTemplate.delete(String.format(CommonRedisKey.LOGIN_SALT, username));
    }

}
