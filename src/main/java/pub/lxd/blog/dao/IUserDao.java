package pub.lxd.blog.dao;

import org.springframework.stereotype.Repository;

import pub.lxd.blog.entity.User;
import pub.lxd.blog.entity.UserDetail;
@Repository
public interface IUserDao extends IBaseDao<User> {

}