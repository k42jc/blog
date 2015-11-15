package pub.lxd.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pub.lxd.blog.dao.DaoFactory;
@Service
public class BaseService {
	@Autowired
	protected DaoFactory daoFactory;
}
