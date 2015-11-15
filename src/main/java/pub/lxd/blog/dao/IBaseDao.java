package pub.lxd.blog.dao;

import java.util.List;

/**
 * 基础dao接口
 * @author k42jc
 *
 * @param <E> 对应的实体类对象
 */
public interface IBaseDao<E> {
	/**
	 * 根据主键删除数据
	 * @param id 主键
	 * @return 删除的行数
	 */
	int deleteByPrimaryKey(Long id);
	
	/**
	 * 新增数据
	 * @param e 实体类对象
	 * @return 对应的数据库主键
	 */
    long insertSelective(E e);
    
    /**
     * 根据主键查找一条记录
     * @param id 主键
     * @return 单个实体类对象
     */
    E selectByPrimaryKey(Long id);
    
    /**
     * 更新数据
     * @param e 实体类对象
     * @return 更新的行数
     */
    int updateByPrimaryKeySelective(E e);
    
    /**
     * 查询所有表记录
     * @return 所有表记录
     */
    List<E> selectAll();
}
