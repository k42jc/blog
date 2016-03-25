package com.techeffic.blog.tags;

import java.io.IOException;

import jetbrick.template.JetAnnotations;
import jetbrick.template.runtime.JetTagContext;

@JetAnnotations.Tags
public class ExtendsTags {
	
	/**
	 * 引入静态文件tag
	 * @param ctx
	 * @param resource 资源路径
	 * @throws IOException
	 */
	public static void introduce(JetTagContext ctx,String resource) throws IOException{
		String introduce = "";
		if(resource.endsWith(".css")){
			introduce = "<link rel=\"stylesheet\" type=\"text/css\" href=\""+resource+"\"/>";
		}else if(resource.endsWith(".js")){
			introduce = "<script type=\"text/javascript\" src=\""+resource+"\"></script>";
		}
		ctx.getWriter().print(introduce);
	}
}
