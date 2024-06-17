package br.com.pdsars.notificationapi.api.service;

import br.com.pdsars.notificationapi.api.dto.CategoryDTO;
import br.com.pdsars.notificationapi.api.dto.ClientRegistrationDTO;
import br.com.pdsars.notificationapi.api.model.Category;
import br.com.pdsars.notificationapi.api.model.Channel;
import br.com.pdsars.notificationapi.api.model.Client;
import br.com.pdsars.notificationapi.api.repository.CategoryRepository;
import br.com.pdsars.notificationapi.api.repository.ChannelRepository;
import br.com.pdsars.notificationapi.api.repository.ClientRepository;
import br.com.pdsars.notificationapi.api.util.ServiceAcount;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
public class ClientService {
    private static final Logger LOG = LoggerFactory.getLogger(ChannelService.class);
    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;
    private final ChannelRepository channelRepository;

    public ClientService(
        ClientRepository clientRepository,
        CategoryRepository categoryRepository,
        ChannelRepository channelRepository
    ) {
        this.clientRepository = clientRepository;
        this.categoryRepository = categoryRepository;
        this.channelRepository = channelRepository;
    }

    public Client registerClient(ClientRegistrationDTO clientRegistration) {
        final var acc = ServiceAcount.fromSecurityContext();
        final Client client = this.ensureClient(acc);

        final var currentCategories = client.getCategories()
            .stream()
            .collect(Collectors.toUnmodifiableMap(Category::getExternalId, Function.identity()));

        final var categories = clientRegistration.getCategories()
            .stream()
            .map(CategoryDTO::toCategory)
            .peek(category -> {
                final var existingCategory = currentCategories.get(category.getExternalId());
                if (existingCategory != null) {
                    category.setId(existingCategory.getId());
                }
                category.setClient(client);
            })
            .collect(Collectors.toUnmodifiableMap(Category::getExternalId, Function.identity()));

        this.categoryRepository.saveAll(categories.values());

        final var currentChannels = client.getChannels()
            .stream()
            .collect(Collectors.toUnmodifiableMap(Channel::getExternalId, Function.identity()));
        final var channels = clientRegistration.getChannels()
            .stream()
            .map(channelDTO -> {
                final var chan = channelDTO.toChannel();

                final var existingChannel = currentChannels.get(chan.getExternalId());
                if (existingChannel != null) {
                    chan.setId(existingChannel.getId());
                }

                final var category = categories.get(channelDTO.getCategoryId());
                if (category == null) {
                    throw new IllegalArgumentException(
                        "Category with id " + channelDTO.getCategoryId() + " not found"
                    );
                }
                chan.setCategory(category);
                chan.setClient(client);
                return chan;
            })
            .toList();
        this.channelRepository.saveAll(channels);

        client.setCategories(new ArrayList<>(categories.values()));
        client.setChannels(channels);
        return client;
    }

    @Nonnull
    public Client ensureClient(@Nonnull ServiceAcount acc) {
        return this.clientRepository.findByOauthClientId(acc.clientId())
            .map(c -> {
                final UUID savedKeycloakId = c.getKeycloakId();
                if (!savedKeycloakId.equals(acc.keycloakId())) {
                    LOG.warn(
                        "Client {} changed keycloakId from {} to {}," +
                            " check if correct service account is being used",
                        acc.clientId(),
                        savedKeycloakId,
                        acc.keycloakId()
                    );
                }

                c.setLastSeen(Instant.now());
                return this.clientRepository.save(c);
            })
            .orElseGet(() -> {
                final var client = new Client();
                client.setOauthClientId(acc.clientId());
                client.setKeycloakId(acc.keycloakId());
                client.setLastSeen(Instant.now());
                return this.clientRepository.save(client);
            });
    }
}
