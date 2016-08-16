package com.techeffic.blog.service.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.techeffic.blog.constants.WechatConfig;
import com.techeffic.blog.context.WebContext;
import com.techeffic.blog.service.BaseService;
import com.techeffic.blog.service.IWechatPayService;
import com.techeffic.blog.util.HttpClientUtil;
import com.techeffic.blog.util.Log4jUtil;
import com.techeffic.blog.util.RandomUtil;
import com.techeffic.blog.util.SignUtil;
import com.techeffic.blog.util.XMLUtil;

/**
 * 微信支付service
 * @author k42jc
 *
 */
@Service
public class WechatPayService extends BaseService implements IWechatPayService{
	private final static String payUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	@Override
	public String pay(WebContext webCtx,Map<String, Object> orderInfo){
		Map<String, Object> paramMap = new HashMap<String, Object>();
//		IServiceRequest httpsRequest = new HttpsRequest();
		paramMap.put("appid", WechatConfig.appid); // appid
		paramMap.put("mch_id", WechatConfig.mch_id); // 商户号
		paramMap.put("device_info", WechatConfig.device_info); // 交易类型
		paramMap.put("nonce_str", RandomUtil.getRandomStringByLength(32)); // 随机数
		paramMap.put("body", "品善教育微信扫码支付测试"); // 描述
		paramMap.put("out_trade_no", RandomUtil.getRandomStringByLength(32)); // 商户后台的贸易单号
		paramMap.put("total_fee", 1); // 金额必须为整数 单位为分
		String ip = webCtx.getIP();
		//Log4jUtil.info("ip--"+ip);
		paramMap.put("spbill_create_ip", ip); // 本机的Ip
		paramMap.put("notify_url", "http://www.techeffic.com/wechatPay/callback.action"); // 支付成功后，回调地址
		paramMap.put("trade_type", WechatConfig.trade_type_native); // 交易类型
		paramMap.put("product_id", RandomUtil.getRandomStringByLength(32)); // 商品id NATIVE时必填
		//String mapXml = XMLUtil.mapToXml(paramMap);
		//Log4jUtil.info("mapXml--"+mapXml);
		String sign = SignUtil.getSign(paramMap, WechatConfig.key);
		//Log4jUtil.info("sign--"+sign);
		//Log4jUtil.info("key--"+WechatConfig.key);
		paramMap.put("sign", sign);// 根据微信签名规则，生成签名
		String paramXml = XMLUtil.mapToXml(paramMap);
		//Log4jUtil.info("paramXml--"+paramXml);
		String resXml = HttpClientUtil.postData(payUrl, paramXml);
//		String resXml = httpsRequest.sendPost(payUrl, XMLUtil.mapToXml(paramMap));
		//Log4jUtil.info(resXml);
		Document dd = null;
		String code_url=null;
		try {
		    dd = DocumentHelper.parseText(resXml);
		  } catch (DocumentException e) {
		       return ""; 
		}
		if (dd != null) {
		    Element root = dd.getRootElement();
		    if (root == null) {
		    return "";
		    }
		    Element codeUrl = root.element("code_url");
		    if (codeUrl == null) {
		    return "";
		    }  
		    code_url = codeUrl.getText();  //解析 xml 获得 code_url
		}
		return code_url;
	}

}
