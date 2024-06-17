package br.com.pdsars.notificationapi.api.spi.dispacther;

import jakarta.annotation.Nonnull;

/**
 * NotificationDispatcherProvider
 */
public interface NotificationDispatcherProviderFactory<T extends NotificationDispatcher> {
    @Nonnull
    T create();
}
