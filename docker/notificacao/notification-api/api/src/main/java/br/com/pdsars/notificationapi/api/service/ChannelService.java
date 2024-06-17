package br.com.pdsars.notificationapi.api.service;

import br.com.pdsars.notificationapi.api.dto.ChannelDTO;
import br.com.pdsars.notificationapi.api.dto.NotificationDTO;
import br.com.pdsars.notificationapi.api.repository.ChannelRepository;
import br.com.pdsars.notificationapi.api.repository.ClientRepository;
import br.com.pdsars.notificationapi.api.sender.MailNotificationSender;
import br.com.pdsars.notificationapi.api.util.ServiceAcount;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static net.logstash.logback.argument.StructuredArguments.*;

@Service
public class ChannelService {
    private final ChannelRepository channelRepository;

    private final MailNotificationSender mailNotificationSender;

    // private final NotificationDispatcher dispatcher;

    private static final Logger LOG = LoggerFactory.getLogger(ChannelService.class);

    public ChannelService(
        final ChannelRepository channelRepository,
        final ClientRepository clientRepository,
        final MailNotificationSender mailNotificationSender
        // final RabbitMQNotificationDispatcherProviderFactory rabbitMQNotificationDispatcherProviderFactory
    ) {
        this.channelRepository = channelRepository;
        this.mailNotificationSender = mailNotificationSender;
        // this.dispatcher = rabbitMQNotificationDispatcherProviderFactory.create();
    }

    @Nonnull
    public List<ChannelDTO> listChannels(@Nonnull String oauthClientId) {
        return this.channelRepository.findAllForClient(oauthClientId)
            .stream()
            .map(ChannelDTO::new)
            .toList();
    }

    @Nonnull
    public Map<String, UUID> sendNotification(
        @Nonnull String channelId,
        @Nonnull NotificationDTO notificationDTO) {
        final var acc = ServiceAcount.fromSecurityContext();

        LOG.info(
            "Sending notification from client `{}`, through channel `{}`, to users `{}`",
            value("clientId", acc.clientId()),
            value("channel", channelId),
            value("users", notificationDTO.getUsers())
        );

        // save on db the notification log
        // send through the dispatcher
        // return the UUIDs of the requests
        final var reqs = new HashMap<String, UUID>();
        notificationDTO.getUsers().forEach(email -> {
            reqs.put(email, UUID.randomUUID());
            CompletableFuture.runAsync(() -> {
                try {
                    this.mailNotificationSender.send(
                        notificationDTO.getTitle(),
                        notificationDTO.getBody(),
                        email
                    ).get();
                } catch (InterruptedException | ExecutionException e) {
                    LOG.error("Error dispatching notification " + notificationDTO, e);
                }
            });
        });
        return reqs;
    }

}
