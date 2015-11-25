package pub.lxd.blog.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pub.lxd.blog.entity.UserDetail;
import pub.lxd.blog.service.BaseService;
import pub.lxd.blog.service.IUserService;
@Service
@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
public class UserService extends BaseService implements IUserService {

	public UserDetail selectUserDetailById(Long id) {
		return this.daoFactory.userDetailDao.selectByPrimaryKey(id);
	}

}
