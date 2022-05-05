package crypto.manager.services;

import crypto.manager.domain.CoinBalance;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;
    @Value("${spring.rabbitmq.delete-routingkey}")
    private String deleteRoutingkey;
    @Value("${spring.rabbitmq.update-routingkey}")
    private String updateRoutingkey;

    public void sendCoinBalanceIdForDeletion(long id){
        rabbitTemplate.convertAndSend(exchange, deleteRoutingkey, id);
    }

    public void sendCoinBalanceIdForUpdate(CoinBalance coinBalance){
        rabbitTemplate.convertAndSend(exchange, updateRoutingkey, coinBalance);
    }
}
