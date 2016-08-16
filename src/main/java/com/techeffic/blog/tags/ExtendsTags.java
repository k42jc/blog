package com.techeffic.blog.tags;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jetbrick.template.JetAnnotations;
import jetbrick.template.JetTemplate;
import jetbrick.template.runtime.JetTagContext;
import jetbrick.util.JSONUtils;

import com.techeffic.blog.constants.Constants;
import com.techeffic.blog.constants.WebResponse;
import com.techeffic.blog.context.SpringContextHolder;
import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.entity.Component;
import com.techeffic.blog.service.component.IDataModelService;

/**
 * 模板标签扩展
 * @author k42jc
 *
 */
@JetAnnotations.Tags
public class ExtendsTags extends BaseTags {
	
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
		ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		WebContext webCtx =WebContext.init(attributes.getRequest(),attributes.getResponse());
		// 获取当前组件配置
		Component component = serviceFactory.getComponentService()
				.findComponentByKey(key);
		// 获取当前模板
		JetTemplate template = ctx.getEngine().getTemplate(component.getPath());
		// 获取模板内嵌数据
		Map<String, Object> datas = new HashMap<String, Object>();
		//将当前URL携带的请求参数放入模板预置数据
		//if(webCtx != null)//避免容器启动加载模板时出现java.lang.ClassFormatError: Unknown tag value for constant pool entry错误
		/*webCtx.getRequest().getParameterMap().forEach((k,values) ->{
			if(values.length == 1){
				datas.put(k,values[0]);
			}else{
				datas.put(k, JSONUtils.toJSONString(values));
			}
		});*/
		//datas.putAll(webCtx.getRequest().getParameterMap());
		
		if(!"".equals(component.getClassName())){
			datas.putAll(((IDataModelService) SpringContextHolder
					.getBean(component.getClassName())).getData(webCtx));
		}
		// 模板渲染
		StringWriter writer = new StringWriter();
		template.render(datas, writer);
		// 输出
		ctx.getWriter().print(writer.getBuffer().toString());
		writer.flush();
		writer.close();
	}
	
	public static void description(JetTagContext ctx,String title,String ketwords,String description){
		StringBuilder desc = new StringBuilder();
		ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		//非文章显示页面
		if(request.getRequestURI().indexOf(Constants.REQUEST_URI_ARTICLE) < 0){
			desc.append("<title>").append(title).append("</title>");
			desc.append("<meta name=\"keywords\" content=\"").append(ketwords).append("\" />");
		}else{
			Integer articleId = Integer.parseInt(request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1));
			Article article = serviceFactory.getArticleService().findTitleKeywordsByOrder(articleId);
			desc.append("<title>").append(article.getTitle()).append("</title>");
			desc.append("<meta name=\"keywords\" content=\"").append(article.getKeywords()).append("\" />");
		}
		desc.append("<meta name=\"description\" content=\"").append(description).append("\" />");
	}

}
