package com.example.demo.shiro.constant;

/**
 * 常量
 * @author geekidea
 * @date 2018-11-08
 */
public interface CommonConstant {

    /**
     * 默认页码为1
     */
    Integer DEFAULT_PAGE_INDEX = 1;

    /**
     * 默认页大小为10
     */
    Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 登录用户
     */
    String LOGIN_USER = "loginUser";

    /**
     * 登陆token
     */
    String JWT_DEFAULT_TOKEN_NAME = "token";

    /**
     * JWT用户名
     */
    String JWT_USERNAME = "username";

    /**
     * JWT刷新新token响应状态码
     */
    int JWT_REFRESH_TOKEN_CODE = 460;

    /**
     * JWT刷新新token响应状态码，
     * Redis中不存在，但jwt未过期，不生成新的token，返回361状态码
     */
    int JWT_INVALID_TOKEN_CODE = 461;

    /**
     * JWT Token默认密钥
     */
    String JWT_DEFAULT_SECRET = "LENOVO@QIT^ABC$$";

    /**
     * JWT 默认过期时间，3600L，单位秒
     */
    Long JWT_DEFAULT_EXPIRE_SECOND = 3600L;

    /**
     * 初始密码
     */
    String INIT_PWD = "123456";

    /**
     * 默认头像
     */
    String DEFAULT_HEAD_URL = "";

    /**
     * 默认角色名称
     */
    String DEFAULT_ROLE_NAME = "Regular User";

    /**
     * CQM角色名称
     */
    String FFRP_CQM_ROLE_NAME = "FFRP CQM User";

    /**
     * ffrp admin角色名称
     */
    String FFRP_ADMIN_ROLE_NAME = "System Administrator";

    /**
     * mfal odm users角色名称
     */
    String MFAL_ODM_USERS_ROLE_NAME = "MFAL External User";

    /**
     * QIT IT Maintenance运维角色名称
     */
    String QIT_IT_ROLE_NAME = "IT Maintenance";

    /**
     * 一级菜单
     */
    Integer MENU_TYPE_0 = 0;

	/**
	 * ffrp主页路由
	 */
	String FFRP_MAIN_AREA_URL= "ffrp";

	/**
	 * MFAL主页路由
	 */
	String MFAL_MAIN_AREA_URL= "mfal";

    /**
     * mfal系统中MFAL User角色
     */
    String MFALUser = "MFAL User";

    /**
     * mfal系统中MFAL Leader角色
     */
    String MFALLeader = "MFAL Leader";

    /**
     * mfal系统中admin角色
     */
    String MFALAdmin = "MFAL Admin";

    /**
     * Unit中Close状态
     */
    String UnitCloseState = "Closed";

    /**
     * issue 中close状态之一
     */
    String SolutionImplemented = "Solution Implemented";

    /**
     * issue 中close状态之一
     */
    String SolutionStandardized = "Solution Standardized";
}
