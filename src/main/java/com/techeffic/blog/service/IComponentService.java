package com.techeffic.blog.service;

import com.techeffic.blog.entity.Component;

/**
 * 组件操作接口
 * @author k42jc
 *
 */
public interface IComponentService {
	Component findComponentByKey(String key);
}
