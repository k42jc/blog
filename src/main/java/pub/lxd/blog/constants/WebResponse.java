package pub.lxd.blog.constants;

import java.util.HashMap;

/**
 * 定义页面公共返回数据格式
 * @author k42jc
 *
 */
public class WebResponse extends HashMap<String, Object>{
	

	private static final long serialVersionUID = 1L;
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	private String msg;
	
	/**
	 * 设置初始容量 hashMap默认初始容量为16 手动设置更节省内存
	 * @param length 初始容量
	 */
	public WebResponse(int length) {
		super(length, DEFAULT_LOAD_FACTOR);
		this.put("success", Constants.SUCCESS);
		this.put("msg", this.msg);
	}
	
	public WebResponse() {
		this.put("success", Constants.SUCCESS);
		this.put("msg", this.msg);
	}
	
	public WebResponse(String msg) {
		this.msg = msg ;
		this.put("success", Constants.SUCCESS);
		this.put("msg", this.msg);
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}
	
	public void setSuccess(int success){
		this.put("success", success);
	}

}
