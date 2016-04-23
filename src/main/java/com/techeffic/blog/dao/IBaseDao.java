package com.techeffic.blog.dao;

import java.util.List;

/**
 * 基础dao接口
 * 定义基础增删查该
 * @author k42jc
 *
 * @param <E> 实体类泛型
 */
public interface IBaseDao<E> {
	/**
	 * 新增或修改记录
	 * 数据存在时更新，不存在时新增
	 * @param e 实体对象
	 */
	void saveOrUpdate(E e);
	
	/**
	 * 删除记录
	 * @param e 待删除的对象
	 */
	void remove(E e);
	
	/**
	 * 根据id查询实体对象
	 * @param clazz 实体对应的类型
	 * @param id 主键
	 * @return 查询结果
	 */
	E findById(Class<E> clazz,String id);
	
	/**
	 * 查询所有结果集
	 * @param clazz 实体对应的类型
	 * @return 实体结果集
	 */
	List<E> findAll(Class<E> clazz);
	
	/**
	 * 保存
	 * @param e 实体
	 * @param name 表名
	 */
	void save(E e,String name);
	
	/**
	 * 清空e集合
	 * @param e
	 */
	void clear(Class<E> e);

}
