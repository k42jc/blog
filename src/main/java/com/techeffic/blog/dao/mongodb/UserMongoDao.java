package com.techeffic.blog.dao.mongodb;

import org.springframework.stereotype.Repository;

import com.techeffic.blog.dao.IUserDao;
import com.techeffic.blog.entity.User;
@Repository
public class UserMongoDao extends BaseMongoDao<User> implements IUserDao {
	
}
