package pub.lxd.blog.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * dao工厂
 * @author k42jc
 *
 */
@Component
public class DaoFactory {
	@Autowired
	public IBlogDao blogDao;
	@Autowired
	public ISysDataDao sysDataDao;
	@Autowired
	public IUserDao userDao;
	@Autowired
	public IUserDetailDao userDetailDao;
	
}
