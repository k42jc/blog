package pub.lxd.blog.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import pub.lxd.blog.entity.SysData;
@Repository
public interface ISysDataDao extends IBaseDao<SysData> {

	List<SysData> selectByType(String type);
}