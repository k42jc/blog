package com.techeffic.blog.service.impl;

import java.io.IOException;
import java.util.Map;

import jetbrick.template.JetTemplate;
import jetbrick.template.web.JetWebContext;
import jetbrick.template.web.JetWebEngine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.techeffic.blog.constants.WebContext;
import com.techeffic.blog.dao.mongodb.BaseMongoDao;
import com.techeffic.blog.entity.Template;
import com.techeffic.blog.service.ITemplateService;

@Service
public class TemplateService implements ITemplateService{
	/*@Value("#{settings['metaPagePath']}")
	public String metaPagePath = "";
	
	private JetWebEngine jetWebEngine;
	
	
	public JetWebEngine getJetWebEngine() {
		return jetWebEngine;
	}
//	@Autowired
	public void setJetWebEngine(JetWebEngine jetWebEngine) {
		this.jetWebEngine = jetWebEngine;
	}

	@Override
	public Map<String, Object> findPageData(String string) {
		return null;
	}

	@Override
	public JetTemplate getMetaPageTemplate() {
		return JetWebEngine.getEngine().getTemplate(metaPagePath);
	}*/

}
