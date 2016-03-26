package com.techeffic.blog.tags;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;

import jetbrick.template.JetAnnotations;
import jetbrick.template.JetTemplate;
import jetbrick.template.runtime.JetTagContext;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.techeffic.blog.context.SpringContextHolder;
import com.techeffic.blog.dao.DaoFactory;
import com.techeffic.blog.entity.Component;
import com.techeffic.blog.service.component.IComponentService;

@JetAnnotations.Tags
public class ExtendsTags extends DaoFactory {
	private DaoFactory daoFactory;

	// private static FileInputStream fileInputStream;

	public DaoFactory getDaoFactory() {
		return daoFactory;
	}

	@Autowired
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	/**
	 * 引入静态文件tag
	 * 
	 * @param ctx
	 * @param resource
	 *            指定资源路径
	 * @throws IOException
	 */
	public static void introduce(JetTagContext ctx, String resource)
			throws IOException {
		String introduce = "";
		if (resource.endsWith(".css")) {
			introduce = "<link rel=\"stylesheet\" type=\"text/css\" href=\""
					+ resource + "\"/>";
		} else if (resource.endsWith(".js")) {
			introduce = "<script type=\"text/javascript\" src=\"" + resource
					+ "\"></script>";
		}
		ctx.getWriter().print(introduce);
	}

	/**
	 * 页面组件
	 * 
	 * @param ctx
	 * @param key
	 *            组件key
	 * @throws IOException
	 */
	public static void component(JetTagContext ctx, String key)
			throws IOException {
		if (key == null || "".equals(key))
			return;
		// 获取当前组件配置
		Component component = ((DaoFactory) SpringContextHolder
				.getBean("daoFactory")).getComponentMongoDao()
				.findComponentByKey(key);
		// 获取当前模板
		// URL teamplatePath =
		// Thread.currentThread().getContextClassLoader().getResource("../views/");
		JetTemplate template = ctx.getEngine().getTemplate(component.getPath());
		// 获取模板内嵌数据
		Map<String, Object> datas = ((IComponentService) SpringContextHolder
				.getBean(component.getClassName())).getData();
		// 模板渲染
		StringWriter writer = new StringWriter();
		template.render(datas, writer);
		// 输出
		ctx.getWriter().print(writer.getBuffer().toString());
		writer.flush();
		writer.close();
	}

}
