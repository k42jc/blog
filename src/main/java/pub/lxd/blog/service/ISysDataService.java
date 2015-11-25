package pub.lxd.blog.service;

import java.util.List;

import pub.lxd.blog.entity.SysData;

public interface ISysDataService {
	/**
	 * 根据数据类型查找基础数据集
	 * @param type
	 * @return
	 */
	List<SysData> selectSysDatByType(String type);
}
