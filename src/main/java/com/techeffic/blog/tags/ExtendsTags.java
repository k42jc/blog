package com.techeffic.blog.tags;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jetbrick.template.JetAnnotations;
import jetbrick.template.JetTemplate;
import jetbrick.template.runtime.JetTagContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.techeffic.blog.constants.WebContext;
import com.techeffic.blog.context.SpringContextHolder;
import com.techeffic.blog.dao.DaoFactory;
import com.techeffic.blog.entity.Component;
import com.techeffic.blog.service.component.IComponentService;

/**
 * 模板标签扩展
 * @author k42jc
 *
 */
@JetAnnotations.Tags
public class ExtendsTags extends DaoFactory {
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
		StringBuilder introduce = new StringBuilder();
		if (resource.endsWith(".css")) {
			introduce.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""
					+ resource + "\"/>");
		} else if (resource.endsWith(".js")) {
			introduce.append("<script type=\"text/javascript\" src=\"" + resource
					+ "\"></script>");
		}
		//输出到模板
		ctx.getWriter().print(introduce.toString());
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
		//获取当前请求的request/response
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		WebContext webCtx = new WebContext(request, null);
		// 获取当前组件配置
		Component component = ((DaoFactory) SpringContextHolder
				.getBean("daoFactory")).getComponentMongoDao()
				.findComponentByKey(key);
		// 获取当前模板
		JetTemplate template = ctx.getEngine().getTemplate(component.getPath());
		// 获取模板内嵌数据
		Map<String, Object> datas = ((IComponentService) SpringContextHolder
				.getBean(component.getClassName())).getData(webCtx);
		// 模板渲染
		StringWriter writer = new StringWriter();
		template.render(datas, writer);
		// 输出
		ctx.getWriter().print(writer.getBuffer().toString());
		writer.flush();
		writer.close();
	}

}
