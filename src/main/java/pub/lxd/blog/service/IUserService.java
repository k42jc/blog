package pub.lxd.blog.service;

import pub.lxd.blog.entity.UserDetail;

public interface IUserService {
	/**
	 * 根据用户id查找用户详情
	 * @param id
	 * @return
	 */
	UserDetail selectUserDetailById(Long id);
}
