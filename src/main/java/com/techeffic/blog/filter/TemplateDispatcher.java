package com.techeffic.blog.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.techeffic.blog.constants.Constants;
import com.techeffic.blog.entity.Article;
import com.techeffic.blog.entity.Template;
import com.techeffic.blog.util.TemplateUtil;

/**
 * 模板渲染分发控制器
 * @author k42jc
 *
 */
public class TemplateDispatcher extends BaseDispatcher{
	private static Logger logger = Logger.getLogger(TemplateDispatcher.class);

	@Override
	public void dispatcher() throws IOException {
		Map<String,Object> datas = new HashMap<String, Object>();
		try {
			//包含多级请求
			if(requestURI.lastIndexOf("/") != 0){
				//处理文章显示页面的URI请求
				if(requestURI.startsWith(Constants.REQUEST_URI_ARTICLE)){
					String articleId = requestURI.substring(requestURI.lastIndexOf("/")+1);
					//webCtx.getRequest().getSession().setAttribute("articleId", articleId);
					webCtx.getRequest().setAttribute("articleId", articleId);
					Article article = serviceFactory.getArticleService().findTitleKeywordsById(articleId);
					datas.put("title", article.getTitle());
					datas.put("keywords", article.getKeywords());
				}
				//处理文章列表显示页面URI请求
				if(requestURI.startsWith(Constants.REQUEST_URI_LIST)){
//					webCtx.getRequest().getSession().setAttribute("articleClazz", requestURI.substring(requestURI.lastIndexOf("/")+1));
					webCtx.getRequest().setAttribute("articleClazz", requestURI.substring(requestURI.lastIndexOf("/")+1));
				}
				//处理文章编辑页面URI请求
				if(requestURI.startsWith(Constants.REQUEST_URI_WRITE_MD)){
//					webCtx.getRequest().getSession().setAttribute("articleId", requestURI.substring(requestURI.lastIndexOf("/")+1));
					webCtx.getRequest().setAttribute("articleId", requestURI.substring(requestURI.lastIndexOf("/")+1));
				}
				//处理文章搜索页面URI请求
				if(requestURI.startsWith(Constants.REQUEST_URI_SEARCH)){
//					webCtx.getRequest().getSession().setAttribute("content", requestURI.substring(requestURI.lastIndexOf("/")+1));
					webCtx.getRequest().setAttribute("content", requestURI.substring(requestURI.lastIndexOf("/")+1));
				}
				requestURI = requestURI.substring(0,requestURI.lastIndexOf("/"));
				
			}
			//获取对应页面并渲染
			render(datas);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("页面渲染异常:"+this.getClass().getName(), e);
			//request.getRequestDispatcher("/404.html").forward(request, response);
			//request.getSession();
			//response.sendRedirect("/404.html");
		}
	}

	private void render(Map<String,Object> datas) throws IOException {
		// 获取对应请求模板数据
		Template template = serviceFactory.getTemplateService()
				.findTemplateByRequestURI(requestURI);
		if(template == null){
			response.sendRedirect("/404.html");//找不到页面
			return;
		}
		if (Constants.NEED_LOGIN.equals(template.getNeedLogin())) {
			//需要登录则查看当前用户是否已登录 
			if(!webCtx.getLoginState().isLogin()){
				//isFilter = true;
				//否则跳转到登录页面
				response.sendRedirect("/login?redirectURI="+requestURI);
			}
		}
		// 填充页面数据
//		Map<String, Object> datas = new HashMap<String, Object>();
		if(datas.isEmpty()){
			datas.put("title", template.getTitle());
			datas.put("keywords", template.getKeyWords());
		}
		datas.put("description", template.getDescription());
		/*//将请求中携带的参数放入模板数据
		request.getParameterMap().forEach((key,values) ->{
			if(values.length == 1){
				datas.put(key,values[0]);
			}else{
				datas.put(key, JSONUtils.toJSONString(values));
			}
		});*/
		// 直接渲染页面
		OutputStream outputStream =	TemplateUtil.render(response.getOutputStream(), template.getPath(), datas);
		outputStream.flush();
		outputStream.close();
	}

}
