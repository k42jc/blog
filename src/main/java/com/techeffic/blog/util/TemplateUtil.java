package com.techeffic.blog.util;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import jetbrick.template.JetTemplate;
import jetbrick.template.web.JetWebEngine;

import com.techeffic.blog.constants.Constants;
import com.techeffic.blog.constants.WebContext;
import com.techeffic.blog.context.SpringContextHolder;
import com.techeffic.blog.dao.DaoFactory;
import com.techeffic.blog.entity.Component;
import com.techeffic.blog.entity.Template;
import com.techeffic.blog.exception.ServiceException;
import com.techeffic.blog.service.ServiceFactory;
import com.techeffic.blog.service.component.IComponentService;

public class TemplateUtil {

	public static void render(WebContext webCtx) throws Exception {
		String requestURI = webCtx.getRequest().getRequestURI();
		// 获取基础模板
		JetTemplate metaPageTemplate = JetWebEngine.getEngine().getTemplate(
				"/metaPage/metaTemplate.html");
		// 根据uri查询出模板配置
		Template template = ((ServiceFactory) SpringContextHolder
				.getBean("serviceFactory")).getTemplateService()
				.findTemplateByRequestURI(requestURI);
		if (template == null) {
			throw new ServiceException("页面不存在！");
		}
		// 先渲染当前页面组件
		Component currentComponent = ((DaoFactory) SpringContextHolder
				.getBean("daoFactory")).getComponentMongoDao()
				.findComponentByKey(
						requestURI.substring(requestURI.indexOf("/") + 1,
								requestURI.length()));
		StringWriter writer = new StringWriter();// StringWriter用于获取当前组件渲染后的数据流
		JetTemplate currentTemplate = JetWebEngine.getEngine().getTemplate(
				currentComponent.getPath());
		Map<String, Object> currentTemplateDatas =  ((IComponentService) SpringContextHolder
					.getBean(currentComponent.getClassName())).getData(webCtx);
		currentTemplate.render(currentTemplateDatas, writer);
		// 填充页面数据
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("title", template.getTitle());
		resultMap.put("keywords", template.getKeyWords());
		resultMap.put("description", template.getDescription());
		resultMap.put("body", writer.getBuffer().toString());// 以预置数据的形式填充
		writer.flush();
		writer.close();
		// 如果当前页面不需要登录
		if (!Constants.NEED_LOGIN.equals(template.getNeedLogin())) {
			// 直接渲染页面
			metaPageTemplate.render(resultMap, webCtx.getResponse()
					.getOutputStream());
			webCtx.getResponse().getOutputStream().flush();
			webCtx.getResponse().getOutputStream().close();
			// chain.doFilter(request, response);
		} else {

		}
	}

}
