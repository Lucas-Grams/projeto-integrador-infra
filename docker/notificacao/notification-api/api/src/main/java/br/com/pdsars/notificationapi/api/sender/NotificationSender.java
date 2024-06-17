package br.com.pdsars.notificationapi.api.sender;

import br.com.pdsars.notificationapi.api.model.Account;

import java.util.List;

public interface NotificationSender {
    void send(final NotificationMessage msg, final List<Account> us);
}
