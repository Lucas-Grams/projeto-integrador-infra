package br.com.pdsars.notificationapi.clientlibrary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.pdsars.notificationapi.clientlibrary.model.Category;
import br.com.pdsars.notificationapi.clientlibrary.model.Channel;
import br.com.pdsars.notificationapi.clientlibrary.model.Notification;

public class MockedNotificationClient implements NotificationClient {

    private static final Logger LOG = LoggerFactory.getLogger(MockedNotificationClient.class);

    private MockedNotificationClient() { }

	@Override
    @Nonnull
	public Map<String, UUID> send(@Nonnull String channelId, @Nonnull Notification notification) {
        LOG.info("[MOCK] Sending notification request {}", notification);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		final HashMap<String, UUID> out = new HashMap<>();
		notification.getUsers().forEach(u -> out.put(u, UUID.randomUUID()));
		return out;
    }

    public static class Builder implements NotificationClient.Builder {

		public Builder() { }

		@Override
		public Builder withChannels(@Nonnull List<Channel> channels) {
			return this;
		}

		@Override
		public Builder withCategories(@Nonnull List<Category> categories) {
			return this;
		}

        @Nonnull
		@Override
		public NotificationClient build() {
			LOG.trace("Created mocked notifcation client");
			return new MockedNotificationClient();
		}
    }

}
