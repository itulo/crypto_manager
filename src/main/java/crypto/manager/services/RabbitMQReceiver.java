package crypto.manager.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReceiver implements RabbitListenerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQReceiver.class);

    private final CoinBalanceService coinBalanceService;

    public RabbitMQReceiver(CoinBalanceService coinBalanceService) {
        this.coinBalanceService = coinBalanceService;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(long id) {
        logger.info("deleting coin balance with id {}", id);

        this.coinBalanceService.deleteById(id);
    }
}
