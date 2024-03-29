package com.ginkgocap.ywxt.video.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Autowired
    private RedisConn redisConn;

    @Bean
   // @ConfigurationProperties(prefix="spring.redis")
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisConn.getMaxIdle());
        config.setMinIdle(redisConn.getMinIdle());
        return config;
    }

    @Bean
    //@ConfigurationProperties(prefix="spring.redis")
    public JedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        factory.setDatabase(redisConn.getDatabase());
        factory.setHostName(redisConn.getHostName());
        factory.setPort(redisConn.getPort());
        factory.setTimeout(redisConn.getTimeout());
        logger.info("JedisConnectionFactory bean init success.");
        return factory;
    }


    @Bean
    public RedisTemplate<?, ?> getRedisTemplate(){
        RedisTemplate<?,?> template = new StringRedisTemplate(getConnectionFactory());
        return template;
    }
}
