package br.com.pdsars.notificationapi.api.spi.dispacther.impl;

import br.com.pdsars.notificationapi.api.dto.NotificationDTO;
import br.com.pdsars.notificationapi.api.spi.dispacther.NotificationDispatcherProviderFactory;
import jakarta.annotation.Nonnull;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQNotificationDispatcherProviderFactory implements NotificationDispatcherProviderFactory<RabitMQNotificationDispatcher> {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQNotificationDispatcherProviderFactory(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_NAME)
    public void receiveMessage(NotificationDTO notificationDTO) {
        System.out.println("Received <" + notificationDTO + ">");
    }


    @Override
    @Nonnull
    public RabitMQNotificationDispatcher create() {
        return new RabitMQNotificationDispatcher(rabbitTemplate);
    }
}
