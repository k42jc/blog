package com.techeffic.blog.example.jetty;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

/**
 * 多连接器服务器配置
 * 此示例可以同时接收http/https
 *
 * http://localhost:8010 会自动使用http connector
 * https://localhost:8443 会使用https的connector
 *
 * 另外，keystore使用JDK的keytool生成，步骤如下：
 * 1. 生成keystore：keytool -keystore keystore -alias jetty -genkey -keyalg RSA
 * 第一步在按提示输入一些个人/工作信息后，会有两次输入密码的过程，输入的密码分别对应jetty SslContextFactory的keystorePassword与keyManagerPassword
 * 2. 导出crt证书：keytool -export -alias jetty -file jetty.crt -keystore keystore
 * 3. 将密码明文使用jetty.util的Password生成OBF密文串(需要将jetty.util复制到当前目录)：java -cp jetty-util-<version>.jar org.eclipse.jetty.util.security.Password <密码>
 * 这一步是把密码加密过程，加密两次则执行两次，生成的密码在SslContextFactory.setKeystorePassword()与setKeyManagerPassword()中使用(也可以直接使用明文，但不安全)
 *
 * 在代码中密码配置错误则启动过程会失败
 *
 * Created by liaoxudong on 2017/5/15.
 */
public class MultipleConnectorSrver {
    private static final Logger logger = LogManager.getLogger(MultipleConnectorSrver.class);
    public static void main(String[] args) {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        logger.info("应用加载路径："+path);

        String keyStorePath = path + "WEB-INF/jetty/keystore";
        if(!new File(keyStorePath).exists()){
            logger.error("没有对应的keystore文件!");
            return;
        }
        logger.info("keystore路径："+keyStorePath);

        Server server = new Server();

        // HTTP 配置
        // HttpConfiguration是可以同时配置http与https请求集合
        // 使用"http"参数配置http类型 "https"参数配置安全类型协议
        HttpConfiguration httpConfiguration = new HttpConfiguration();
        httpConfiguration.setSecureScheme("https");
        httpConfiguration.setSecurePort(8443);
        httpConfiguration.setOutputBufferSize(32768);

        // HTTP connector 用于处理http请求
        ServerConnector http = new ServerConnector(server,new HttpConnectionFactory(httpConfiguration));
        http.setPort(8010);
        http.setIdleTimeout(30000);

        // SSL Context factory用于HTTPS请求
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(keyStorePath);
        sslContextFactory.setTrustStorePath(keyStorePath);
        //在使用jdk自带工具生成keystore时指定的第一个密码 使用org.eclipse.jetty.util.security.Password生成
        //详细过程见：http://www.techeffic.com/
//        sslContextFactory.setKeyStorePassword("123456");
        sslContextFactory.setKeyStorePassword("OBF:19iy19j019j219j419j619j8");
        //指定的第二个密码
//        sslContextFactory.setKeyManagerPassword("654321");
        sslContextFactory.setKeyManagerPassword("OBF:19j819j619j419j219j019iy");

        //HTTPS配置 与HTTP配置的区别是增加了一个 SecureRequestCustomizer
        HttpConfiguration https_config = new HttpConfiguration(httpConfiguration);
        SecureRequestCustomizer secureRequestCustomizer = new SecureRequestCustomizer();
        secureRequestCustomizer.setStsMaxAge(2000);
        secureRequestCustomizer.setStsIncludeSubDomains(true);
        https_config.addCustomizer(secureRequestCustomizer);
        //HTTPS connector配置 多了SslConnectionFactory
        ServerConnector https = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()), new HttpConnectionFactory(https_config));
        https.setPort(8443);
        https.setIdleTimeout(500000);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setResourceBase(path+"WEB-INF/web");
//        webAppContext.setContextPath();
        server.setConnectors(new Connector[]{http,https});
        server.setHandler(webAppContext);
        try {
            server.start();
            logger.info("jetty服务启动完成，同时支持http与https，http：8010，https：8443");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
