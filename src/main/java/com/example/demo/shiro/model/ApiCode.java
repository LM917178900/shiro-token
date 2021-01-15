package com.example.demo.shiro.model;

/**
 * <p>
 * REST API 响应码
 * </p>
 *
 * @author geekidea
 * @since 2018-11-08
 */
public enum ApiCode {

    SUCCESS(200, "Successful operation"),

    UNAUTHORIZED(401, "Unauthorized access"),

    NOT_ROLE(402, "Role denied"),

    NOT_PERMISSION(403, "Permission denied"),

    NOT_FOUND(404, "The resource you requested does not exist"),

    FAIL(500, "operation failed"),

    LOGIN_EXCEPTION(4000, "Username or password is incorrect"),

    SYSTEM_EXCEPTION(5000,"The system is abnormal!"),

    PARAMETER_EXCEPTION(5001,"Request parameter check exception"),

    PARAMETER_PARSE_EXCEPTION(5002,"Request parameter parsing exception"),

    HTTP_MEDIA_TYPE_EXCEPTION(5003,"HTTP Media type exception"),


    ;

    private final int code;
    private final String msg;

    ApiCode(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiCode getApiCode(int code) {
        ApiCode[] ecs = ApiCode.values();
        for (ApiCode ec : ecs) {
            if (ec.getCode() == code) {
                return ec;
            }
        }
        return SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
