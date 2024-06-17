package br.com.pdsars.notificationapi.clientlibrary;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nonnull;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import br.com.pdsars.notificationapi.clientlibrary.model.Category;
import br.com.pdsars.notificationapi.clientlibrary.model.Channel;
import br.com.pdsars.notificationapi.clientlibrary.model.ClientRegistration;
import br.com.pdsars.notificationapi.clientlibrary.model.Notification;

public class OAuth2NotificationClient implements NotificationClient {

    private static final Logger LOG = LoggerFactory.getLogger(OAuth2NotificationClient.class);
    private final String apiUrl;
    private final Set<String> channelId;

    private final OAuth20Service oAuth20Service;

    private final ObjectMapper objectMapper;

    private OAuth2NotificationClient(
        String apiUrl,
        List<Channel> channels,
        OAuth20Service oAuth20Service,
        ObjectMapper objectMapper
    ) {
        this.apiUrl = apiUrl;
        this.oAuth20Service = oAuth20Service;
        this.objectMapper = objectMapper;

        final Set<String> channelIds = new HashSet<>();
        channels.forEach(c -> channelIds.add(c.getId()));
        this.channelId = Collections.unmodifiableSet(channelIds);
    }

    private Header[] defaultHeaders() {
        final Header[] headers = new Header[2];
        headers[0] = new BasicHeader("Content-Type", "application/json");

        try {
            LOG.debug("Getting access token using client credentials grant");
            final OAuth2AccessToken token = this.oAuth20Service.getAccessTokenClientCredentialsGrant();
            headers[1] = new BasicHeader("Authorization", "Bearer " + token.getAccessToken());
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error while getting access token", e);
        }

        return headers;
    }

    private void register(@Nonnull ClientRegistration clientRegistration) {
        LOG.trace("Regestering client");

        final Header[] defaultHeaders = this.defaultHeaders();

        try {
            final Response response = Request.post(apiUrl + "/v1/clients/me")
                .bodyString(this.objectMapper.writeValueAsString(clientRegistration),
                    ContentType.APPLICATION_JSON)
                .setHeaders(defaultHeaders)
                .execute();

            final int status = response.returnResponse().getCode();

            if (status != 201) {
                throw new RuntimeException("Error registering client, expected 200 status code, got: " + status);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error registering client", e);
        }
    }


    @Override
    @Nonnull
    public Map<String, UUID> send(@Nonnull String channelId, @Nonnull Notification notification) {
        if (!this.channelId.contains(channelId)) {
            throw new IllegalArgumentException("Channel " + channelId + " not found");
        }

        LOG.trace("Sending notification request {}", notification);

        final Header[] defaultHeaders = this.defaultHeaders();

        try {
            final Response response = Request.post(apiUrl + "/v1/channels/" + channelId)
                .bodyString(this.objectMapper.writeValueAsString(notification), ContentType.APPLICATION_JSON)
                .setHeaders(defaultHeaders)
                .execute();
            final String content = response.returnContent().asString();

            return this.objectMapper.readValue(content, new TypeReference<Map<String, UUID>>() { });
        } catch (IOException e) {
            throw new RuntimeException("Error sending notification: " + notification, e);
        }
    }

    public static class Builder implements NotificationClient.Builder {

        private final String clientId;
        private final String secret;
        private final String apiUrl;
        private final String authorizationServerUrl;

        private List<Category> categories = Collections.emptyList();
        private List<Channel> channels = Collections.emptyList();
        private ObjectMapper objectMapper = new ObjectMapper();

        public Builder(
            @Nonnull String apiUrl,
            @Nonnull String clientId,
            @Nonnull String secret,
            @Nonnull String authorizationServerUrl
        ) {
            this.clientId = clientId;
            this.secret = secret;
            this.apiUrl = apiUrl;
            this.authorizationServerUrl = authorizationServerUrl;
        }


        @Nonnull
        @Override
        public Builder withChannels(@Nonnull List<Channel> channels) {
            this.channels = channels;
            return this;
        }

        @Nonnull
        @Override
        public Builder withCategories(@Nonnull List<Category> categories) {
            this.categories = categories;
            return this;
        }

        public Builder withObjectMapper(@Nonnull ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
            return this;
        }

        @Nonnull
        @Override
        public NotificationClient build() {
            final OAuth20Service oAuth20Service = new ServiceBuilder(this.clientId)
                .apiSecret(this.secret)
                .build(new NonContextedKeycloakApi(this.authorizationServerUrl));

            final OAuth2NotificationClient client = new OAuth2NotificationClient(apiUrl, this.channels, oAuth20Service, this.objectMapper);

            final ClientRegistration clientRegistration = new ClientRegistration(this.categories, this.channels);
            client.register(clientRegistration);

            return client;
        }
    }

}
