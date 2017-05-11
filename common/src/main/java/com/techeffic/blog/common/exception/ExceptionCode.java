package com.techeffic.blog.common.exception;

/**
 * 异常信息
 * Created by liaoxudong on 2017/5/8.
 */
public enum ExceptionCode {

    SYS_ERROR("sys_error","系统错误"),
    JSON_PARSE_ERROR("json_parse_error","Json转换错误"),
    ;

    private String code;
    private String desc;

    ExceptionCode(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
