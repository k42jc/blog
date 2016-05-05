package com.techeffic.blog.constants;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 定义页面公共返回数据格式
 * @author k42jc
 *
 */
public class WebResponse extends HashMap<String, Object>{
	

	private static final long serialVersionUID = 1L;
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	private String msg;
	private String type;
	
	public void setType(String type) {
		this.put("type", type);
	}

	/**
	 * 设置初始容量 hashMap默认初始容量为16 手动设置更节省内存
	 * @param length 初始容量
	 */
	public WebResponse(int length) {
		super(length, DEFAULT_LOAD_FACTOR);
		this.put("success", Constants.SUCCESS);
	}
	
	public WebResponse() {
		super();
		this.put("success", Constants.SUCCESS);
		this.put("type", "info");
	}
	
	public WebResponse(String type,String msg) {
		super();
		this.msg = msg ;
		this.type = type;
		this.put("success", Constants.SUCCESS);
		this.put("msg", this.msg);
		this.put("type", this.type);
	}
	
	public void setMsg(String msg){
		this.put("msg", msg);
	}
	
	public void setSuccess(int success){
		this.put("success", success);
	}
	@SuppressWarnings("all")
	public static String getError(String message){
		JSONObject obj = new JSONObject();
		try {
			obj.put("error", 1);
			obj.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}

	public void init() {
		this.clear();
		this.put("success", Constants.SUCCESS);
	}
	
}
