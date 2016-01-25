package pub.lxd.blog.service;

import pub.lxd.blog.constants.WebContext;

/**
 * 微信公众号接口service
 * @author xudong_liao<br/>
 * @date 2016年1月5日<br/>
 */
public interface IWeChatService {
	
	/**
	 * 接入校验
	 * 2016年1月5日
	 * @param webCtx
	 * @return
	 *
	 */
	public String checkSignature(WebContext webCtx);
	
	/**
	 * 获取access_token
	 * 2016年1月5日
	 * @return
	 *
	 */
	public String getAccess_token();
	
	/**
	 * 获取服务器ip地址列表
	 * 2016年1月5日
	 * @param access_token
	 * @return
	 *
	 */
	public String getCallBackIp(String access_token);
}
