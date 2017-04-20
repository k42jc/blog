###简介
个人博客，提供后台管理+常规博客功能+微信公众号。上班族忙起来也没什么时间，现阶段实现个人博客前端功能，包括：
* 简单登录及权限控制
* 写博客(markdown编辑器or富文本编辑器+文件/图片上传)
* 文章分类
* 文章浏览排行
* 文章评论.留言

使用的h5，所以pc手机都是同一套页面，显示效果还行，不过没有专业前端及作图人员支持，到底还是有些粗糙。

环境准备：
jdk版本:java8
数据库:mysql5.6.17(现已切换为mongodb)
服务器:nginx1.9.6+tomcat8.0.15
模板引擎:github地址-[国产开源软件Jetbrick Template]()
markdown编辑器：github地址-[editor.md]()

**预览地址：[技术之路-廖旭东个人博客](http://www.techeffic.com)**


###部署准备
>原来只是自己写着玩，使用到现在近两年时间，平时或是写点，或是转载，或是翻译，虽然没有特别有技术含量的东西，也写了不少。很多时候忙起来都会忽略对遇到的问题、接触到的新技术的记录，看来这个好习惯还是比较难以养成的。

*本人是部署在centOS下，使用Nginx做反向代理，转发给tomcat*

**前提：java8、maven、tomcat7+、mongodb**
####IDE/windows

修改`resources/mongodb.properties`到指定数据库连接
**IDE**：下载或clone项目到本地，导入IDE，等待maven下载完成，将项目加入到IDE的TOMCAT启动即可
**windows**：使用本地maven打包项目，放入本地tomcat下的webapp包或者修改server.xml到项目指定位置

####linux

关于Linux的java、数据库、nginx等环境准备，建议参照我的记录：[linux环境准备](http://www.techeffic.com/article/0000000056a6114c0156ad3a076e0003)
nginx代理设置部分：
```conf
    *略*
    listen       80;
    server_name  localhost;
    index /index;

    #charset koi8-r;
    #access_log  /var/log/nginx/log/host.access.log  main;

    location = /{
        index /index;
        proxy_pass http://localhost:8080/index;
    }

    location / #因为是restful风格请求 所以将其它所有请求将转往tomcat处理 
    {
        index index;
        proxy_pass http://localhost:8080;#转向tomcat处理
    }    
    *略*
    location ^~ /upload/ {#指定文件上传的访问目录
        root /home/liaoxudong/webapps/blog/;
    }
    location ~ .*\.(ico)$ #设定浏览器默认请求的favicon路径
    {
        expires      1h;
        root   /home/liaoxudong/webapps/blog/WEB-INF/static/img;
    }
    location ^~ /lib/ #markdown编辑器codemirror配置
    {
        expires      1h;
        root   /home/liaoxudong/webapps/blog/WEB-INF/static/editor.md;
    }
    location ^~ /plugins/ #markdown编辑器编辑器插件配置
    {
        expires      1h;
        root   /home/liaoxudong/webapps/blog/WEB-INF/static/editor.md;
    }
    location ~ .*\.(js|css)?$
    {
        expires      1h;
        root   /home/liaoxudong/webapps/blog/WEB-INF/static/;
    }
    #location ~ .*\.html$
    #{
    #   expires      1h;
    #    root   E:/work_kingdee/blog/src/main/webapp/WEB-INF/static;
    #}
    location ^~ /static/ #处理restful风格的静态文件
    {
        expires      1h;
        root /home/liaoxudong/webapps/blog/WEB-INF;
    }
    *略*
```
tomcat通过server.xml指定运行项目位置配置：
```xml
      <Host name="localhost"  appBase="webapps"
            unpackWARs="true" autoDeploy="true">

        <!-- SingleSignOn valve, share authentication between web applications
             Documentation at: /docs/config/valve.html -->
        <!--
        <Valve className="org.apache.catalina.authenticator.SingleSignOn" />
        -->

        <!-- Access log processes all example.
             Documentation at: /docs/config/valve.html
             Note: The pattern used is equivalent to using pattern="common" -->
        <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"
               prefix="localhost_access_log" suffix=".txt"
               pattern="%h %l %u %t &quot;%r&quot; %s %b" />
        <Context   path="/"   docBase="/home/liaoxudong/webapps/blog"   debug="0"   privileged="true" > </Context >

      </Host>
```

