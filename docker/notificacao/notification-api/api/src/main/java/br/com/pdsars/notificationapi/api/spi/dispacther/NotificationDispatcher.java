package br.com.pdsars.notificationapi.api.spi.dispacther;

import java.util.concurrent.CompletableFuture;
import br.com.pdsars.notificationapi.api.dto.NotificationDTO;
import jakarta.annotation.Nonnull;

/**
 * NotificationDispatcherProvider
 */
public interface NotificationDispatcher {
    @Nonnull
    CompletableFuture<Void> dispatch(
        @Nonnull final String channelId,
        @Nonnull final NotificationDTO notificationDTO
    );
}
