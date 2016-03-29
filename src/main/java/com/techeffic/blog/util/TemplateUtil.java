package com.techeffic.blog.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

import jetbrick.template.JetTemplate;
import jetbrick.template.web.JetWebEngine;

/**
 * 模板工具类 用于模板渲染
 * @author k42jc
 *
 */
public class TemplateUtil {
	
	/**
	 * 模板渲染方法
	 * @param outputStream java.io.OutputStream
	 * @param templatePath 模板位置
	 * @param datas 需要渲染的数据
	 * @return
	 * @throws IOException
	 */
	public static OutputStream render(OutputStream outputStream,String templatePath,Map<String,Object> datas) throws IOException{
		JetTemplate template = JetWebEngine.getEngine().getTemplate(templatePath);
		template.render(datas, outputStream);
		return outputStream;
	}
	
	/**
	 * 模板渲染方法
	 * @param writer java.io.Writer
	 * @param templatePath 模板位置
	 * @param datas 需要渲染的数据
	 * @return
	 * @throws IOException
	 */
	public static Writer render(Writer writer,String templatePath,Map<String,Object> datas) throws IOException{
		JetTemplate template = JetWebEngine.getEngine().getTemplate(templatePath);
		template.render(datas, writer);
		return writer;
	}

}
