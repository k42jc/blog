package pub.lxd.blog.dao;

import org.springframework.stereotype.Repository;

import pub.lxd.blog.entity.User;
@Repository
public interface IUserDao extends IBaseDao<User> {
}