package io.renren.imgProcess.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;


@Configuration
@EnableJms
public class ActiveMQConfiguration {
    @Value("${spring.activemq.user}")
    private String userName;

    @Value("${spring.activemq.password}")
    private  String password;

    @Value("${spring.activemq.broker-url}")
    private  String brokerUrl;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        return new ActiveMQConnectionFactory(userName, password, brokerUrl);
    }

    /**
     * queue模式的ListenerContainer
     *
     *
     *
     */
    @Bean(name = "queueListenerFactory")
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory());
        return factory;
    }

    /**
     * topic模式的ListenerContainer
     *
     *
     *
     */
    @Bean(name = "topicListenerFactory")
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setConnectionFactory(activeMQConnectionFactory());
        return factory;
    }

}
