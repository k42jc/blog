package com.techeffic.blog.common.server.jetty;

/**
 * 服务启动模式
 * Created by liaoxudong on 2017/5/8.
 */
public enum BootModel {
    WEB_FRONT("front","前台服务"),
    WEB_MANAGER("manager","后台服务")
    ;


    private String code;
    private String desc;
    BootModel(String code,String desc){
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
