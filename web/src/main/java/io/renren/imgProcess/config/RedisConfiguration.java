package io.renren.imgProcess.config;

import io.renren.imgProcess.service.redisService.FileMessageListener;
import io.renren.imgProcess.service.redisService.Pose3DMessageListener;
import io.renren.imgProcess.service.redisService.RedisMessageListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
@ComponentScan("io.renren.imgProcess")
public class RedisConfiguration extends CachingConfigurerSupport {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;


    @Bean
    public JedisPool jedisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);

        return jedisPool;
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("redis_topic");
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
             MessageListenerAdapter redisListenerAdapter,
             MessageListenerAdapter fileListenerAdapter,
             MessageListenerAdapter pose3DListenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅通道，container 可以添加多个 messageListener
        container.addMessageListener(redisListenerAdapter, new PatternTopic("yanchuangChannel"));
        container.addMessageListener(pose3DListenerAdapter, new PatternTopic("resultChannel"));
//        container.addMessageListener(fileListenerAdapter, new PatternTopic("resultChannel"));

        return container;
    }

    @Bean
    MessageListenerAdapter redisListenerAdapter(RedisMessageListener redisMessageListener){
        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        //也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
        return new MessageListenerAdapter(redisMessageListener, "onMessage");
    }

    @Bean
    MessageListenerAdapter fileListenerAdapter(FileMessageListener fileMessageListener){
        return new MessageListenerAdapter(fileMessageListener, "onMessage");
    }

    @Bean
    MessageListenerAdapter pose3DListenerAdapter(Pose3DMessageListener pose3DMessageListener){
        return new MessageListenerAdapter(pose3DMessageListener, "onMessage");
    }
}
