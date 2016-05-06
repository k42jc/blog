package com.techeffic.blog.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.techeffic.blog.constants.Constants;
import com.techeffic.blog.entity.Template;
import com.techeffic.blog.util.TemplateUtil;

/**
 * 模板渲染分发控制器
 * @author k42jc
 *
 */
public class TemplateDispatcher extends BaseDispatcher{

	@Override
	public void dispatcher() throws IOException {

		//包含多级请求
		if(requestURI.lastIndexOf("/") != 0){
			//处理文章显示页面的URI请求
			if(requestURI.startsWith(Constants.REQUEST_URI_ARTICLE)){
				webCtx.getRequest().setAttribute("articleId", requestURI.substring(requestURI.lastIndexOf("/")+1));
			}
			//处理文章列表显示页面URI请求
			if(requestURI.startsWith(Constants.REQUEST_URI_LIST)){
				webCtx.getRequest().setAttribute("articleClazz", requestURI.substring(requestURI.lastIndexOf("/")+1));
			}
			requestURI = requestURI.substring(0,requestURI.lastIndexOf("/"));
			
		}
		try {
			//获取对应页面并渲染
			render();
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/404.html");
		}
	}

	private void render() throws IOException {
		// 获取对应请求模板数据
		Template template = serviceFactory.getTemplateService()
				.findTemplateByRequestURI(requestURI);
		if (Constants.NEED_LOGIN.equals(template.getNeedLogin())) {
			//需要登录则查看当前用户是否已登录 
			if(!webCtx.getLoginState().isLogin()){
				//isFilter = true;
				//否则跳转到登录页面
				response.sendRedirect("/login?redirectURI="+requestURI);
			}
		}
		// 填充页面数据
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("title", template.getTitle());
		datas.put("keywords", template.getKeyWords());
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
