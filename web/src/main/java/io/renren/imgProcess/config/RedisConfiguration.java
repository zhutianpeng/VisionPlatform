package io.renren.imgProcess.config;


import io.renren.imgProcess.service.redisService.MatchingMessageListener;
import io.renren.imgProcess.service.redisService.Offline3DMessageListener;
import io.renren.imgProcess.service.redisService.RealTime3DMessageListener;
import io.renren.imgProcess.service.redisService.RealTime2DMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
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
             MessageListenerAdapter pose2DListenerAdapter,
             MessageListenerAdapter pose3DListenerAdapter,
             MessageListenerAdapter offline3DListenerAdapter,
             MessageListenerAdapter matchingResultListenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅通道，container 可以添加多个 messageListener
        container.addMessageListener(pose2DListenerAdapter, new PatternTopic("2DOutputChannel")); //在线2D pose处理结果通道
        container.addMessageListener(pose3DListenerAdapter, new PatternTopic("3DOutputChannel")); //在线3D pose处理结果通道
        container.addMessageListener(offline3DListenerAdapter, new PatternTopic("resultChannel")); //离线视频3D pose处理完成通道
        container.addMessageListener(matchingResultListenerAdapter, new PatternTopic("MatchingOutputChannel")); //DTW匹配结果通道
        return container;
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory){
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    MessageListenerAdapter pose2DListenerAdapter(RealTime2DMessageListener realTime2DMessageListener){
        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        //也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
        return new MessageListenerAdapter(realTime2DMessageListener, "onMessage");
    }

    @Bean
    MessageListenerAdapter pose3DListenerAdapter(RealTime3DMessageListener realTime3DMessageListener){
        return new MessageListenerAdapter(realTime3DMessageListener, "onMessage");
    }

    @Bean
    MessageListenerAdapter offline3DListenerAdapter(Offline3DMessageListener offline3DMessageListener){
        return new MessageListenerAdapter(offline3DMessageListener, "onMessage");
    }

    @Bean
    MessageListenerAdapter matchingResultListenerAdapter(MatchingMessageListener matchingMessageListener){
        return new MessageListenerAdapter(matchingMessageListener, "onMessage");
    }
}
