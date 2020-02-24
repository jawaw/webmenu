package org.mcrest.properties;

public interface NetworkResponse {

	final String successMessage = "请求成功";

	final Integer OK = 0;// 请求成功

	final Integer FAIL = -1;// 失败

	final Integer NOT_AUTHENTICATED = -10000; // 未认证

	final Integer BAD_CREDENTIAL = -10001; // 认证错误

	final Integer CREDENTIAL_INVALID = -10002; // 证书已失效

	final Integer NO_AUTHORITY = -10003; // 无权限

	final Integer BAD_OPERATION = -10004; // 非法操作

	final Integer SERVER_ERROR = -10005; // 服务器错误

	final Integer CREDENTIAL_EXPIRED = -10006; // 证书已过期

	final Integer BAD_USER = 10000; // 用户不存在

	final Integer USER_REGISTERED = 10001; // 用户已注册

	final Integer BAD_DATA_TYPE = 10002; // 错误的数据类型

	final Integer NO_DATA = 10003; // 没有相关资源

	final Integer NO_PLAYER = 10004; // 玩家不存在

	final Integer LOGIN_WRONG = 10005; // 帐号或者密码错误

}
