package pub.lxd.blog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pub.lxd.blog.entity.SysData;
import pub.lxd.blog.service.BaseService;
import pub.lxd.blog.service.ISysDataService;
@Service
@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
public class SysDataService extends BaseService  implements ISysDataService {

	public List<SysData> selectSysDatByType(String type) {
		return this.daoFactory.sysDataDao.selectByType(type);
	}

}
