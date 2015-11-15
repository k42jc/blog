package pub.lxd.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pub.lxd.blog.service.impl.BlogService;

/**
 * service工厂
 * @author k42jc
 *
 */
@Component
public class ServiceFactory {
	@Autowired
	public BlogService blogService;
}
