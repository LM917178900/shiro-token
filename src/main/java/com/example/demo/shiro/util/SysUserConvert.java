package com.example.demo.shiro.util;

import com.example.demo.shiro.model.LoginUserRedisVo;
import com.example.demo.shiro.model.LoginUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author geekidea
 * @date 2019-10-05
 **/
@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    /**
     * 系统用户实体对象转换成登陆用户VO对象
     * @param user
     * @return
     */
//    LoginUserVo userToLoginUserVo(UserInfo user);

    /**
     * LoginSysUserVo对象转换成LoginSysUserRedisVo
     *
     * @param loginUserVo
     * @return
     */
    LoginUserRedisVo loginUserVoToLoginUserRedisVo(LoginUserVo loginUserVo);

}
