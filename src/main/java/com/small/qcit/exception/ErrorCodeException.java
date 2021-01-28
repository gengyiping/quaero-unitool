package com.small.qcit.exception;

import com.small.qcit.enums.inter.Code;

/**
 * 返回错误码
 *
 */
public class ErrorCodeException extends Exception {

    private Code code;
    private String message;
    public ErrorCodeException(Code code) {
        this.code = code;
    }
    public ErrorCodeException(Code code,String message) {
        this.code = code;
        this.message=message;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
}
