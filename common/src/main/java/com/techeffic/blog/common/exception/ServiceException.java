package com.techeffic.blog.common.exception;

/**
 * 自定义异常
 */
public class ServiceException extends RuntimeException{


	/**
	 * 
	 */
	private static final long serialVersionUID = -7307148408747507363L;

	private String errorCode;
	private String errorMsg;

	public ServiceException(ExceptionCode code){
		super(code.getCode()+","+code.getDesc());
		this.errorCode = code.getCode();
		this.errorMsg = code.getDesc();
	}

	public ServiceException(String errorCode,String errorMsg){
	    super(errorCode+","+errorMsg);
	    this.errorCode = errorCode;
	    this.errorMsg = errorMsg;
    }

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
