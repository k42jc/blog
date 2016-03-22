package com.techeffic.blog.template;

import java.io.StringWriter;
import java.util.Map;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import jetbrick.template.runtime.InterpretContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class PageTemplateEngine{
//	@Value("#{setting['tempPath']}")
	public String tempPath =  "";
	private static Logger logger = Logger.getLogger(PageTemplateEngine.class);
	
	private JetEngine jetEngine;
	
	public JetEngine getJetEngine() {
		return jetEngine;
	}
	public void setJetEngine(JetEngine jetEngine) {
		this.jetEngine = jetEngine;
	}

	/**
	 * 获取模板
	 * @param tempName
	 * @param data
	 * @return
	 */
	public String render(String tempName,Map<String,Object> data){
		JetTemplate jetTemplate = getTemplate("/WEB-INF/views/template/"+tempName);
		StringWriter writer = new StringWriter();
		jetTemplate.render(data, writer);
		return writer.toString();
	}
	
	public String render(JetTemplate template,Map<String,Object> data){
		StringWriter writer = new StringWriter();
		template.render(data, writer);
		return writer.toString();
	}
	
	public JetTemplate getTemplate(String tempName){
		jetEngine.getConfig().getTemplateLoaders();
		jetEngine.getConfig().getTemplateSuffix();
		jetEngine.getGlobalContext();
		jetEngine.getGlobalResolver();
		if(!jetEngine.checkTemplate(tempName)){
			logger.error("模板不存在！");
			return null;
		}
		return jetEngine.getTemplate(tempName);
	}

}
