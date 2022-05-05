package crypto.manager.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.delete-routingkey}")
    private String deleteRoutingKey;
    @Value("${spring.rabbitmq.delete-queue}")
    private String deleteQueue;
    @Value("${spring.rabbitmq.update-routingkey}")
    private String updateRoutingKey;
    @Value("${spring.rabbitmq.update-queue}")
    private String updateQueue;


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean
    Exchange myExchange() {
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }

    @Bean
    Queue deleteQueue() {
        return new Queue(deleteQueue, false);
    }

    @Bean
    Binding deleteBinding() {
        return BindingBuilder
                .bind(deleteQueue())
                .to(myExchange())
                .with(deleteRoutingKey)
                .noargs();
    }

    @Bean
    Queue updateQueue() {
        return new Queue(updateQueue, false);
    }

    @Bean
    Binding updateBinding() {
        return BindingBuilder
                .bind(updateQueue())
                .to(myExchange())
                .with(updateRoutingKey)
                .noargs();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}