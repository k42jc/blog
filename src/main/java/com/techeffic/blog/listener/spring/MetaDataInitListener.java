package com.techeffic.blog.listener.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.techeffic.blog.constants.Constants;
import com.techeffic.blog.dao.DaoFactory;
import com.techeffic.blog.entity.SysData;
import com.techeffic.blog.entity.Template;
import com.techeffic.blog.util.KeyUtil;

/**
 * 用于容器初启动完成后执行操作
 * 用于初始化基础数据 需要的时候可放开clear与init方法的
 * @author k42jc
 *
 */
@Component
public class MetaDataInitListener extends BaseSpringListener<ContextRefreshedEvent>{
	@Autowired
	private DaoFactory daoFactory;
	
	@Override
	protected void loadCompleteExecute(ContextRefreshedEvent event) {
		super.loadCompleteExecute(event);
		if(isRootLoad){
			//先清空集合
			clear();
			//初始化数据
			init();
		}
	}
	
	private void clear() {
		daoFactory.getTemplateMongoDao().clear(Template.class);
		daoFactory.getComponentMongoDao().clear(com.techeffic.blog.entity.Component.class);
		daoFactory.getSysDataMongoDao().clear(SysData.class);
		
	}

	private void init(){
		//模板
		initTemplate();
		
		//组件
		initComponent();
		
		
		//初始化系统类型
		initSysData();
		
	}

	private void initSysData() {
		SysData blogClazz = new SysData();
		blogClazz.setId(KeyUtil.generate());
		blogClazz.setCreateDate(new Date());
		blogClazz.setOrders(1);
		blogClazz.setType(Constants.SYSDATA_TYPE_BLOG_TYPE);
		blogClazz.setKey("y");
		blogClazz.setValue("原创");
		blogClazz.setRemark("原创类型文章");
		
		SysData blogClazz2 = new SysData();
		blogClazz2.setId(KeyUtil.generate());
		blogClazz2.setCreateDate(new Date());
		blogClazz2.setOrders(2);
		blogClazz2.setType(Constants.SYSDATA_TYPE_BLOG_TYPE);
		blogClazz2.setKey("z");
		blogClazz2.setValue("转载");
		blogClazz2.setRemark("转载类型文章");
		
		SysData blogType = new SysData();
		blogType.setId(KeyUtil.generate());
		blogType.setCreateDate(new Date());
		blogType.setOrders(1);
		blogType.setType(Constants.SYSDATA_TYPE_BLOG_CLAZZ);
		blogType.setKey("java");
		blogType.setValue("java");
		blogType.setRemark("记录java语言方面的技术，有简短的小感想，也有针对某些技术的专题.");
		
		SysData blogType2 = new SysData();
		blogType2.setId(KeyUtil.generate());
		blogType2.setCreateDate(new Date());
		blogType2.setOrders(2);
		blogType2.setType(Constants.SYSDATA_TYPE_BLOG_CLAZZ);
		blogType2.setKey("db");
		blogType2.setValue("数据库");
		blogType2.setRemark("数据库方面的知识，现在对象型数据库渐渐流行，作为程序员，要有对新技术的追求.");
		
		SysData blogType3 = new SysData();
		blogType3.setId(KeyUtil.generate());
		blogType3.setCreateDate(new Date());
		blogType3.setOrders(3);
		blogType3.setType(Constants.SYSDATA_TYPE_BLOG_CLAZZ);
		blogType3.setKey("linux");
		blogType3.setValue("linux");
		blogType3.setRemark("在校学的就是linux，需要懂一些，博客服务器就是自己搭的环境.");
		
		SysData blogType4 = new SysData();
		blogType4.setId(KeyUtil.generate());
		blogType4.setCreateDate(new Date());
		blogType4.setOrders(4);
		blogType4.setType(Constants.SYSDATA_TYPE_BLOG_CLAZZ);
		blogType4.setKey("js");
		blogType4.setValue("javascript");
		blogType4.setRemark("前端知识也不能少，何况现在Node、Angular这么火，也是要学习的.");
		
		SysData blogType5 = new SysData();
		blogType5.setId(KeyUtil.generate());
		blogType5.setCreateDate(new Date());
		blogType5.setOrders(5);
		blogType5.setType(Constants.SYSDATA_TYPE_BLOG_CLAZZ);
		blogType5.setKey("os");
		blogType5.setValue("操作系统相关");
		blogType5.setRemark("Windows、Linux、Mac常用操作.");
		
		SysData blogType6 = new SysData();
		blogType6.setId(KeyUtil.generate());
		blogType6.setCreateDate(new Date());
		blogType6.setOrders(6);
		blogType6.setType(Constants.SYSDATA_TYPE_BLOG_CLAZZ);
		blogType6.setKey("dp");
		blogType6.setValue("设计模式");
		blogType6.setRemark("对常用设计模式的记录与见解.");
		
		SysData blogType7 = new SysData();
		blogType7.setId(KeyUtil.generate());
		blogType7.setCreateDate(new Date());
		blogType7.setOrders(7);
		blogType7.setType(Constants.SYSDATA_TYPE_BLOG_CLAZZ);
		blogType7.setKey("framework");
		blogType7.setValue("开源框架");
		blogType7.setRemark("Spring、Ibatis/MyBatis、Struts、Hibernate、Spark、Hadoop等流行开源框架的学习.");
		
		SysData blogType8 = new SysData();
		blogType8.setId(KeyUtil.generate());
		blogType8.setCreateDate(new Date());
		blogType8.setOrders(8);
		blogType8.setType(Constants.SYSDATA_TYPE_BLOG_CLAZZ);
		blogType8.setKey("log");
		blogType8.setValue("项目日志");
		blogType8.setRemark("陈列一下开发任务，也不定期记录一下开发进度.");
		
		SysData blogType9 = new SysData();
		blogType9.setId(KeyUtil.generate());
		blogType9.setCreateDate(new Date());
		blogType9.setOrders(9);
		blogType9.setType(Constants.SYSDATA_TYPE_BLOG_CLAZZ);
		blogType9.setKey("python");
		blogType9.setValue("Python");
		blogType9.setRemark("这一天突然想给自己的博客做一下测试，了解了一下python的自动化测试，鼓捣了一上午，及时记录");
		
		List<SysData> sysDataList = new ArrayList<SysData>();
		
		sysDataList.add(blogClazz);
		sysDataList.add(blogClazz2);
		sysDataList.add(blogType);
		sysDataList.add(blogType2);
		sysDataList.add(blogType3);
		sysDataList.add(blogType4);
		sysDataList.add(blogType5);
		sysDataList.add(blogType6);
		sysDataList.add(blogType7);
		sysDataList.add(blogType8);
		sysDataList.add(blogType9);
		
		sysDataList.forEach(sysData -> {
			this.daoFactory.getSysDataMongoDao().saveOrUpdate(sysData);
		});
	}

	private void initComponent() {
		com.techeffic.blog.entity.Component component = new com.techeffic.blog.entity.Component();
		component.setId(UUID.randomUUID().toString());
		component.setKey("header");
		component.setPath("/component/header.html");
		component.setClassName("headerDataModelService");
		
		com.techeffic.blog.entity.Component component2 = new com.techeffic.blog.entity.Component();
		component2.setId(UUID.randomUUID().toString());
		component2.setKey("footer");
		component2.setPath("/component/footer.html");
		component2.setClassName("footerDataModelService");
		
		com.techeffic.blog.entity.Component component3 = new com.techeffic.blog.entity.Component();
		component3.setId(UUID.randomUUID().toString());
		component3.setKey("swiper");
		component3.setPath("/component/index/swiper.html");
		component3.setClassName("swiperDataModelService");
		
		com.techeffic.blog.entity.Component component4 = new com.techeffic.blog.entity.Component();
		component4.setId(UUID.randomUUID().toString());
		component4.setKey("recommend");
		component4.setPath("/component/index/recommend.html");
		component4.setClassName("recommendDataModelService");
		
		com.techeffic.blog.entity.Component component5 = new com.techeffic.blog.entity.Component();
		component5.setId(UUID.randomUUID().toString());
		component5.setKey("kd_body");
		component5.setPath("/component/write/kindEditor/body.html");
		component5.setClassName("kdDataModelService");
		
		com.techeffic.blog.entity.Component component6 = new com.techeffic.blog.entity.Component();
		component6.setId(UUID.randomUUID().toString());
		component6.setKey("login_body");
		component6.setPath("/component/login/body.html");
		component6.setClassName("");
		
		com.techeffic.blog.entity.Component component7 = new com.techeffic.blog.entity.Component();
		component7.setId(UUID.randomUUID().toString());
		component7.setKey("md_body");
		component7.setPath("/component/write/markdown/body.html");
		component7.setClassName("mdDataModelService");
		
		com.techeffic.blog.entity.Component component8 = new com.techeffic.blog.entity.Component();
		component8.setId(UUID.randomUUID().toString());
		component8.setKey("article_body");
		component8.setPath("/component/article/body.html");
		component8.setClassName("articleDataModelService");
		
		com.techeffic.blog.entity.Component component9 = new com.techeffic.blog.entity.Component();
		component9.setId(UUID.randomUUID().toString());
		component9.setKey("aboutMe");
		component9.setPath("/component/about/body.html");
		component9.setClassName("aboutDataModelService");
		
		com.techeffic.blog.entity.Component component10 = new com.techeffic.blog.entity.Component();
		component10.setId(UUID.randomUUID().toString());
		component10.setKey("classBody");
		component10.setPath("/component/class/body.html");
		component10.setClassName("articleClassDataModelService");
		
		com.techeffic.blog.entity.Component component11 = new com.techeffic.blog.entity.Component();
		component11.setId(UUID.randomUUID().toString());
		component11.setKey("listBody");
		component11.setPath("/component/list/body.html");
		component11.setClassName("listDataModelService");
		
		com.techeffic.blog.entity.Component component12 = new com.techeffic.blog.entity.Component();
		component12.setId(UUID.randomUUID().toString());
		component12.setKey("searchBody");
		component12.setPath("/component/search/body.html");
		component12.setClassName("searchDataModelService");
		
		
		List<com.techeffic.blog.entity.Component> componentList = new ArrayList<com.techeffic.blog.entity.Component>();
		componentList.add(component);
		componentList.add(component2);
		componentList.add(component3);
		componentList.add(component4);
		componentList.add(component5);
		componentList.add(component6);
		componentList.add(component7);
		componentList.add(component8);
		componentList.add(component9);
		componentList.add(component10);
		componentList.add(component11);
		componentList.add(component12);
		componentList.forEach(c -> {
			this.daoFactory.getComponentMongoDao().saveOrUpdate(c);
		});
	}

	private void initTemplate() {
		Template template = new Template();
		template.setId(UUID.randomUUID().toString());
		template.setRequestURI("/index");
		template.setPath("/index.html");
		template.setNeedLogin("0");
		template.setTitle("首页-技术之路-廖旭东个人博客");
		template.setKeyWords("技术博客，个人博客，技术文章");
		template.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		
		Template template2 = new Template();
		template2.setId(UUID.randomUUID().toString());
		template2.setRequestURI("/about");
		template2.setPath("/about.html");
		template2.setNeedLogin("0");
		template2.setTitle("个人简介-技术之路-廖旭东个人博客");
		template2.setKeyWords("廖旭东个人简介，技术博客，个人博客，技术文章");
		template2.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		
		Template template3 = new Template();
		template3.setId(UUID.randomUUID().toString());
		template3.setRequestURI("/login");
		template3.setPath("/login.html");
		template3.setNeedLogin("0");
		template3.setTitle("登录-技术之路-廖旭东个人博客");
		template3.setKeyWords("登录页面，技术博客，个人博客，技术文章");
		template3.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		
		Template template4 = new Template();
		template4.setId(UUID.randomUUID().toString());
		template4.setRequestURI("/write_kd");
		template4.setPath("/write_kd.html");
		template4.setNeedLogin("1");
		template4.setTitle("写博客(普通模式)-技术之路-廖旭东个人博客");
		template4.setKeyWords("使用富文本编辑器写博客，技术博客，个人博客，技术文章");
		template4.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		
		Template template5 = new Template();
		template5.setId(UUID.randomUUID().toString());
		template5.setRequestURI("/write_md");
		template5.setPath("/write_md.html");
		template5.setNeedLogin("1");
		template5.setTitle("写博客(markdown编辑器)-技术之路-廖旭东个人博客");
		template5.setKeyWords("在线markdown编辑器，技术博客，个人博客，技术文章");
		template5.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		
		Template template6 = new Template();
		template6.setId(UUID.randomUUID().toString());
		template6.setRequestURI("/article");
		template6.setPath("/article.html");
		template6.setNeedLogin("0");
		template6.setTitle("${title}-技术之路-廖旭东个人博客");
		template6.setKeyWords("${keywords}，技术博客，个人博客，技术文章");
		template6.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		
		Template template7 = new Template();
		template7.setId(UUID.randomUUID().toString());
		template7.setRequestURI("/class");
		template7.setPath("/class.html");
		template7.setNeedLogin("0");
		template7.setTitle("文章类别-技术之路-廖旭东个人博客");
		template7.setKeyWords("技术博客，个人博客，技术文章");
		template7.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		
		
		Template template8 = new Template();
		template8.setId(UUID.randomUUID().toString());
		template8.setRequestURI("/list");
		template8.setPath("/list.html");
		template8.setNeedLogin("0");
		template8.setTitle("文章列表-技术之路-廖旭东个人博客");
		template8.setKeyWords("技术博客，个人博客，技术文章");
		template8.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		
		Template template9 = new Template();
		template9.setId(UUID.randomUUID().toString());
		template9.setRequestURI("/search");
		template9.setPath("/search.html");
		template9.setNeedLogin("0");
		template9.setTitle("搜索列表-技术之路-廖旭东个人博客");
		template9.setKeyWords("技术博客，个人博客，技术文章");
		template9.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		
		
		List<Template> list = new ArrayList<Template>();
		list.add(template);
		list.add(template2);
		list.add(template3);
		list.add(template4);
		list.add(template5);
		list.add(template6);
		list.add(template7);
		list.add(template8);
		list.add(template9);
		
		list.forEach(t -> {
			this.daoFactory.getTemplateMongoDao().saveOrUpdate(t);
		});
	}
}
