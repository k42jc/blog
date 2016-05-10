package com.techeffic.blog.constants;
/**
 * 常量类
 * @author k42jc
 *
 */
public class Constants {
	//true
	public static final int SUCCESS = 1;
	//false
	public static final int FAIL = 0;
	
	/**系统数据类型--博客分类：原创/转载*/
	public static final String SYSDATA_TYPE_BLOG_TYPE = "blogType";
	/**系统数据类型--博客类别*/
	public static final String SYSDATA_TYPE_BLOG_CLAZZ = "blogClazz";
	/**系统数据类型--导航栏标题*/
	public static final String SYSDATA_TYPE_NAVBAR_TITLE = "navbar_title";
	/**系统数据类型--座右铭*/
	public static final String SYSDATA_TYPE_MOTO = "moto";
	/**系统数据类型--关于*/
	public static final String SYSDATA_TYPE_ABOUT = "about";
	
	
	/**微信公众号--access_token*/
	public static final String ACCESS_TOKEN = "access_token";
	/**微信公众号--expires_in*/
	public static final String EXPIRES_IN = "expires_in";
	/**微信公众号--ip_list*/
	public static final String IP_LIST = "ip_list";
	
	//当前页面需要登录
	public static final String NEED_LOGIN = "1";
	
	/**文章的文本类型 --- html**/
	public static final String CONTENT_TYPE_HTML ="H";
	/**文章的文本类型 --- markdown**/
	public static final String CONTENT_TYPE_MARKDOWN = "M";
	
	/**文章请求URI*/
	public static final String REQUEST_URI_ARTICLE = "/article"; 
	/**文章列表请求URI*/
	public static final String REQUEST_URI_LIST = "/list"; 
	/**文章编辑请求URI*/
	public static final String REQUEST_URI_WRITE_MD = "/write_md"; 
	/**文章新增**/
	public static final String EDIT_TYPE_EDIT = "edit";
	/**文章编辑**/
	public static final String EDIT_TYPE_ADD = "add"; 
}
