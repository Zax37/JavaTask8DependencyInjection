package uj.jwzp.w2.e3.logic.beans;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;
import java.util.Optional;

@Configuration
@EnableJms
public class ConnectionFactoryConfig {
    @Value("${broker:#{null}}")
    String brokerUrl;

    @Value("${ACTIVEMQ_ADMIN_LOGIN}")
    String userName;

    @Value("${ACTIVEMQ_ADMIN_PASSWORD}")
    String password;

    @Bean
    public String destination(
            @Value("${topic}") Optional<String> topic,
            @Value("${queue}") Optional<String> queue
    ) {
        if (topic.isPresent() && queue.isPresent())
            return queue.get() + "/" + topic.get();
        else if (topic.isPresent())
            return topic.get();
        else if (queue.isPresent())
            return queue.get();
        else return null;
    }

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        if (brokerUrl == null) return null;
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(userName);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        if (brokerUrl == null) return null;
        JmsTemplate template = new JmsTemplate();
        template.setMessageConverter(jacksonJmsMessageConverter());
        template.setConnectionFactory(connectionFactory());
        template.setPubSubDomain(true);
        return template;
    }
}
