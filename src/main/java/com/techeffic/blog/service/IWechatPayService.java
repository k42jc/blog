package com.techeffic.blog.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

import com.techeffic.blog.context.WebContext;

/**
 * 微信支付接口
 * @author k42jc
 *
 */
public interface IWechatPayService {
	
	/**
	 * 支付方法
	 * @param orderInfo 订单信息
	 * @return 返回二维码code_url
	 * @throws IOException 
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * @throws UnrecoverableKeyException 
	 */
	public String pay(WebContext webCtx,Map<String,Object> orderInfo);
}
