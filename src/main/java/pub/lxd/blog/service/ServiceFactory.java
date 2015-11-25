package pub.lxd.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * service工厂
 * @author k42jc
 *
 */
@Component
public class ServiceFactory {
	@Autowired
	public IBlogService blogService;
	@Autowired
	public ISysDataService sysDataService;
	@Autowired
	public IUserService userService;
}
