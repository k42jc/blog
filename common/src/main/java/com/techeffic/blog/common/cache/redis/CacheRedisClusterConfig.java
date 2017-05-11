package com.techeffic.blog.common.cache.redis;

import com.techeffic.blog.common.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;

/**
 * redis集群配置
 * Created by liaoxudong on 2017/5/9.
 */
public class CacheRedisClusterConfig extends RedisClusterConfiguration{

    private static final Logger logger = LogManager.getLogger(CacheRedisClusterConfig.class);

    public CacheRedisClusterConfig(String nodes){
        if(StringUtil.isEmpty(nodes)){
            logger.error("redis cluster节点为空，集群配置失败");
            return;
        }
        try {
            String[] hostPorts = nodes.split(",");
            for(String hostPort:hostPorts){
                String[] hp = hostPort.split(":");
                //加入集群节点
                addClusterNode(new RedisNode(hp[0],Integer.valueOf(hp[1])));
            }
            logger.info("加入redis cluster节点："+nodes);
        } catch (Exception e) {
            logger.error("redis cluster节点配置错误，集群配置失败",e);
            e.printStackTrace();
        }
    }
}
