package com.techeffic.blog.listener.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.techeffic.blog.dao.DaoFactory;
import com.techeffic.blog.entity.SysData;
import com.techeffic.blog.entity.Template;

/**
 * 用于容器初启动完成后执行操作
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
		
	}

	private void init(){
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
		template5.setKeyWords("使用markdown编辑器写博客，技术博客，个人博客，技术文章");
		template5.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		
		Template template6 = new Template();
		template6.setId(UUID.randomUUID().toString());
		template6.setRequestURI("/article");
		template6.setPath("/article.html");
		template6.setNeedLogin("0");
		template6.setTitle("${title}-技术之路-廖旭东个人博客");
		template6.setKeyWords("${keywords}，技术博客，个人博客，技术文章");
		template6.setDescription("本站用于记录我的技术成长之路，学习生活中的快乐与悲伤，主要是技术类文章，偶尔在这发发小牢骚");
		List<Template> list = new ArrayList<Template>();
		list.add(template);
		list.add(template2);
		list.add(template3);
		list.add(template4);
		list.add(template5);
		list.add(template6);
		
		list.forEach(t -> {
			this.daoFactory.getTemplateMongoDao().saveOrUpdate(t);
		});
		
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
		
		List<com.techeffic.blog.entity.Component> componentList = new ArrayList<com.techeffic.blog.entity.Component>();
		componentList.add(component);
		componentList.add(component2);
		componentList.add(component3);
		componentList.add(component4);
		componentList.add(component5);
		componentList.add(component6);
		componentList.add(component7);
		componentList.add(component8);
		componentList.forEach(c -> {
			this.daoFactory.getComponentMongoDao().saveOrUpdate(c);
		});
		
		//初始化系统类型
		SysData blogClazz = new SysData();
		blogClazz.setId("");
	}
}
