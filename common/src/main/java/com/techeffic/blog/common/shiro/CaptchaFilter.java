package com.techeffic.blog.common.shiro;

import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Shiro验证码过滤器
 * TODO
 * Created by liaoxudong on 2017/5/11.
 */
public class CaptchaFilter  extends AccessControlFilter{
    // 是否启用验证码 默认为true
    private boolean captchaEbabled = true;

    // 验证失败后存储的属性名
    private String failureKeyAttribute = "shiroLoginFailure";

    public void setCaptchaEbabled(boolean captchaEbabled) {
        this.captchaEbabled = captchaEbabled;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
