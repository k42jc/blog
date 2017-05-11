package com.techeffic.blog.common.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import java.io.Serializable;

/**
 * 将shiro session放入redis集群管理实现
 * TODO
 * Created by liaoxudong on 2017/5/11.
 */
public class SessionRedisDao extends EnterpriseCacheSessionDAO{

    @Override
    protected Serializable doCreate(Session session) {

        return super.doCreate(session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return super.doReadSession(sessionId);
    }

    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
    }

    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
    }
}
