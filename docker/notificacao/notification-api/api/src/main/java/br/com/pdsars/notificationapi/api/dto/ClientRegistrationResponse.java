package br.com.pdsars.notificationapi.api.dto;

import br.com.pdsars.notificationapi.api.model.Client;

import java.time.Instant;
import java.util.List;

public record ClientRegistrationResponse(
    Long id,
    String oauthClientId,

    List<CategoryDTO> categories,
    List<ChannelDTO> channels,
    Instant createdAt
) {
    public static ClientRegistrationResponse fromClient(final Client c) {
        return new ClientRegistrationResponse(
            c.getId(),
            c.getOauthClientId(),
            c.getCategories().stream().map(CategoryDTO::new).toList(),
            c.getChannels().stream().map(ChannelDTO::new).toList(),
            c.getCreatedAt()
        );
    }
}
