package br.com.pdsars.notificationapi.api.spi.sender;

import java.util.List;
import br.com.pdsars.notificationapi.api.model.Account;
import br.com.pdsars.notificationapi.api.sender.NotificationMessage;
import jakarta.annotation.Nonnull;

/**
 * NotificationSenderProvider
 */
public interface NotificationSenderProvider {
    void send(
        @Nonnull final NotificationMessage messages,
        @Nonnull final List<Account> accounts
    );
}
