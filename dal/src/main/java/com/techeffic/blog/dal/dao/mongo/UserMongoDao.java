package com.techeffic.blog.dal.dao.mongo;

import com.techeffic.blog.dal.dao.IUserDao;
import com.techeffic.blog.dal.entity.mongo.User;
import org.springframework.stereotype.Repository;
@Repository
public class UserMongoDao extends BaseMongoDao<User> implements IUserDao {
	
}
