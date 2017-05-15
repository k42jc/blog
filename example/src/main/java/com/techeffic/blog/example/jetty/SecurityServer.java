package com.techeffic.blog.example.jetty;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.Collections;

/**
 * 使用security handler包装普通handler
 * 当访问普通handler时，需要通过security handler的安全验证
 * Created by liaoxudong on 2017/5/15.
 */
public class SecurityServer {

    public static void main(String[] args) {
        Server server = new Server(8020);

        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        LoginService loginService = new HashLoginService("MyRealm", path+"prop/jetty-realm.properties");
        server.addBean(loginService);

        //security会被第一个调用，通过验证后才会继续调用之后的handler
        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        server.setHandler(security);

        // 设置需要身份验证，且指定一组角色
        Constraint constraint = new Constraint();
        constraint.setName("auth");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{"user", "admin"});

        // 将设定的约束应用到请求url 此处表示验证对所有请求有效
        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/");
        mapping.setConstraint(constraint);

        security.setConstraintMappings(Collections.singletonList(mapping));
        security.setAuthenticator(new BasicAuthenticator());
        security.setLoginService(loginService);


        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setResourceBase(path+"WEB-INF/web");

        server.setHandler(webAppContext);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
