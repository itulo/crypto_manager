package crypto.manager.services;

import crypto.manager.domain.CoinBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqReceiver implements RabbitListenerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);

    private final CoinBalanceService coinBalanceService;

    public RabbitMqReceiver(CoinBalanceService coinBalanceService) {
        this.coinBalanceService = coinBalanceService;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
    }

    @RabbitListener(queues = "${spring.rabbitmq.delete-queue}")
    public void receivedDeleteQueue(long id) {
        logger.info("deleting coin balance with id {}", id);

        this.coinBalanceService.deleteById(id);
    }

    @RabbitListener(queues = "${spring.rabbitmq.update-queue}")
    public void receivedUpdateQueue(CoinBalance coinBalance) {
        logger.info("updating coin balance with id {}", coinBalance.getId());

        //do nothing...
        // I just wanted to play around with rabbitmq and have a direct exchange with two routing keys
    }
}
