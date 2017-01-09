package com.techeffic.blog.example.thread;

/**
 * ThreadLocal初始值为null 要想实现默认值效果 需要手动覆盖initialValue()方法
 * @author liaoxudong
 *
 */
public class ThreadLocalExt extends ThreadLocal<Object>{
	@Override
	protected Object initialValue() {
		return "我是默认值";
	}
}
