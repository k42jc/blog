这几天在积极研究“架构”，真是头大，好好研究发现自己的水平还真是菜
每天都有写代码，但是没有每天都push，因为家里没网了，蹭的别人的网速慢得死
今天用手机开wifi才顺利传上来，也是心疼

######################################################
刚刚弄了一下网站备案，真是麻烦，蛋疼。
开始着手项目重构了，把数据库/持久层由mysql/mybatis改成使用spring-data-mongo的形式。
开始动手！
#######################################################
2016-3-19 推送
-------------
好好整理了一下git仓库的分支，之前版本放在了blog_1.1的branches下面，也许不会再修改了。
删除了原master分支，当前的默认分支为blog_2.0，也是当前主要的开发根据地，以后任何推送都会在这个分支上。
-------------
可能会有比较大的改动，数据库换成Mongodb，之前在工作上有接触过，再不熟悉一下就要忘掉了。
将系统好好“架构一下”，好好使用以下jetbrick的模板技术，貌似功能还比较强大，有很多东西值得研究研究。
java代码方面，也想着尝试一下java8的新特性，函数式接口、stream、lambda表达式等等，毕竟环境一直用的是java。
-------------
以前的域名还可以使用，地址为：http://www.lxd.pub，将来就在服务器上部署两套吧。
现在的页面差不多成型了，暂时部署了个demo，在Nginx上指定了个Server。
访问地址为：http://demo.lxd.pub。
新域名需要备案，暂时还不能使用，真是麻烦。

