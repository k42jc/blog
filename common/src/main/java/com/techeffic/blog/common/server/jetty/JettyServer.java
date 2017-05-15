package com.techeffic.blog.common.server.jetty;


import com.techeffic.blog.common.util.ConfigUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

import java.lang.management.ManagementFactory;

/**
 * Jetty容器相关
 * Created by liaoxudong on 2017/5/8.
 */
public class JettyServer {
    private static final Logger logger = LogManager.getLogger(JettyServer.class);

    private BootModel model;
    private ServerConnector connector;

    public JettyServer(BootModel model){
        this.model = model;
    }

    private final static String JETTY_PORT = "jetty.port";//端口
    private final static String JETTY_ACCEPTORS = "jetty.acceptors";//等待请求连接的线程数
    private final static String JETTY_ACCEPTQUEUESIZE = "jetty.AcceptQueueSize";//等待处理的连接队列大小
    private final static String JETTY_MAX_IDLETIME = "jetty.maxIdleTime";//最大等待时间
    private final static String JETTY_DIRECTORY = "jetty.directory";
    private final static String JETTY_CONTEXTPATH = "jetty.contextPath";
    private final static String JETTY_WEBDESCRIPTOR = "jetty.webDescriptor";
    private final static String JETTY_MAXTHREAD = "jetty.maxThreads";

    /*public void init(){
        try {
            logger.info("jetty web 【{}】 开始启动...", model.getDesc());
            String prefix = model.getCode() + ".";
            Integer port = ConfigUtil.PROP.getProperty(prefix + JETTY_PORT, "8080", Integer.class);

            logger.info("web 端口："+port);
            connector = new SelectChannelConnector();
            connector.setPort(port);
            //等待请求连接的线程数，同时在监听read事件的线程数 *默认值是1 *典型范围：1~(处理器内核数+1) *对于NIO来说 设置为(处理器内核数+1)比较合适
            connector.setAcceptors(ConfigUtil.PROP.getProperty(JETTY_ACCEPTORS, "2", Integer.class));

            //等待处理的连接队列大小
            connector.setAcceptQueueSize(ConfigUtil.PROP.getProperty(JETTY_ACCEPTQUEUESIZE,"100",Integer.class));
            //最大等待时间
            connector.setMaxIdleTime(ConfigUtil.PROP.getProperty(JETTY_MAX_IDLETIME,"300000",Integer.class));
            //
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String derectory = ConfigUtil.PROP.getProperty(JETTY_DIRECTORY);
            String contextPath = ConfigUtil.PROP.getProperty(JETTY_CONTEXTPATH);
            String webDescriptor = ConfigUtil.PROP.getProperty(JETTY_WEBDESCRIPTOR);

            logger.info("访问路径："+derectory+contextPath);
            logger.info("webPath："+path+webDescriptor);

            webAppContext = new WebAppContext(path+derectory,contextPath);
            webAppContext.setDescriptor(webDescriptor);
            //以内嵌容器运行

            Server server = new Server();
            //最大线程数
            int maxThreads = ConfigUtil.PROP.getProperty(JETTY_MAXTHREAD,"100",Integer.class);
            //默认实现方式
            QueuedThreadPool queuedThreadPool = new QueuedThreadPool(maxThreads);
            server.setThreadPool(queuedThreadPool);
            server.addConnector(connector);
            server.setHandler(webAppContext);
            server.start();
            logger.info("jetty web {} 启动完成",model.getDesc());
        } catch (Exception e) {
            logger.error("jetty web {} 启动失败",model.getDesc(),e);
        }
    }*/
    public void init(){
        logger.info("jetty web 【{}】 开始启动...", model.getDesc());
        String prefix = model.getCode() + ".";
        Integer port = ConfigUtil.PROP.getProperty(prefix + JETTY_PORT, "8080", Integer.class);

        logger.info("web 端口："+port);
        Server server = new Server();

        // 实现https
        /*HttpConfiguration https_config = new HttpConfiguration();
        https_config.setSecureScheme("https");
        https_config.setSecurePort(8443);
        https_config.setOutputBufferSize(32768);
        https_config.addCustomizer(new SecureRequestCustomizer());

        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath("keystore");
        sslContextFactory.setKeyStorePassword("OBF:1xtb1uo71wg41y0q1y7z1y101wfu1unr1xu7");
        sslContextFactory.setKeyManagerPassword("OBF:1xtb1uo71wg41y0q1y7z1y101wfu1unr1xu7");*/
        // Setup JMX
        MBeanContainer mbContainer = new MBeanContainer(
                ManagementFactory.getPlatformMBeanServer());
        server.addBean(mbContainer);
        //连接器
        connector = new ServerConnector(server/*,new SslConnectionFactory(sslContextFactory,"http/1.1"),
                new HttpConnectionFactory(https_config)*/);
        //等待请求连接的线程数，同时在监听read事件的线程数 *默认值是1 *典型范围：1~(处理器内核数+1) *对于NIO来说 设置为(处理器内核数+1)比较合适
        connector.setAcceptorPriorityDelta(ConfigUtil.PROP.getProperty(JETTY_ACCEPTORS, "2", Integer.class));
        //等待处理的连接队列大小
        connector.setAcceptQueueSize(ConfigUtil.PROP.getProperty(JETTY_ACCEPTQUEUESIZE,"100",Integer.class));
        connector.setPort(port);
        connector.setIdleTimeout(ConfigUtil.PROP.getProperty(JETTY_MAX_IDLETIME,"300000",Integer.class));
        server.addConnector(connector);

        //web应用参数配置
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String derectory = ConfigUtil.PROP.getProperty(JETTY_DIRECTORY);
        String contextPath = ConfigUtil.PROP.getProperty(JETTY_CONTEXTPATH);
        String webDescriptor = ConfigUtil.PROP.getProperty(JETTY_WEBDESCRIPTOR);

        WebAppContext webAppContext = new WebAppContext();
        logger.info("web path："+path+derectory);
        webAppContext.setContextPath(contextPath);
        webAppContext.setDescriptor(path+webDescriptor);
        webAppContext.setResourceBase(path+derectory);
        webAppContext.setWelcomeFiles(new String[]{"index.html"});
        //禁用jetty的默认的查看服务器文件路径
        webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
        server.setHandler(webAppContext);

        try {
            server.start();
            logger.info("jetty web {} 启动完成",model.getDesc());
            server.join();
        } catch (Exception e) {
            logger.error("jetty web {} 启动失败",model.getDesc(),e);
        }
    }

    public void destroy(){
        try {
            if(connector != null){
                connector.close();
                connector = null;
            }
        } catch (Exception e) {
            logger.error("jetty连接关闭失败",e);
        }
    }
}
