package com.techeffic.blog.example.thread;

import java.util.Date;

public class InheritableThreadLocalExt extends InheritableThreadLocal<Object>{
	
	@Override
	protected Object initialValue() {
		return new Date().getTime();
	}
	
	@Override
	protected Object childValue(Object parentValue) {
		return parentValue + " : childValue";
	}
}
