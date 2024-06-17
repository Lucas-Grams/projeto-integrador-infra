package br.com.pdsars.notificationapi.api.spi.dispacther.impl;

import java.util.concurrent.CompletableFuture;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import br.com.pdsars.notificationapi.api.dto.NotificationDTO;
import br.com.pdsars.notificationapi.api.spi.dispacther.NotificationDispatcher;
import jakarta.annotation.Nonnull;

/**
 * RabitMQNotificationDispatcherProvider
 */
public class RabitMQNotificationDispatcher implements NotificationDispatcher {
    private final RabbitTemplate rabbitTemplate;

    RabitMQNotificationDispatcher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


	@Nonnull
    @Override
	public CompletableFuture<Void> dispatch(@Nonnull String channelId, @Nonnull NotificationDTO notificationDTO) {
        final var dispatchEvent = notificationDTO;

        return CompletableFuture.runAsync(() -> {
            this.rabbitTemplate.convertAndSend(
                RabbitMQConfiguration.QUEUE_NAME,
                dispatchEvent
            );
        });
	}
}
