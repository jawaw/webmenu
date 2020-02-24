package org.mcrest.vo;

import org.mcrest.properties.NetworkResponse;

/**
 * @author Lee
 * @date 2018年10月10日 上午10:54:06
 */
public class ResponseVO implements NetworkResponse{

	// 状态码
	private Integer code;
	// 是否成功
	private Boolean success;
	// 返回信息
	private String message;
	// 返回实体结果
	private Object data;
	
	protected ResponseVO(Integer code, Boolean success, String message, Object data) {
		super();
		this.code = code;
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public static ResponseVO success() {
		return new ResponseVO(OK, true, successMessage, null);
	}

	public static ResponseVO success(Object data) {
		return new ResponseVO(OK, true, successMessage, data);
	}
	
	public static ResponseVO success(String successMessage, Object data) {
		return new ResponseVO(OK, true, successMessage, data);
	}

	public static ResponseVO failure(Integer code) {
		return new ResponseVO(code, false, "处理失败", null);
	}

	public static ResponseVO failure(Integer code, String message) {
		return new ResponseVO(code, false, message, null);
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

}
