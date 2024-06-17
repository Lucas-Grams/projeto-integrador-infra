package br.com.pdsars.notificationapi.clientlibrary;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import br.com.pdsars.notificationapi.clientlibrary.model.Category;
import br.com.pdsars.notificationapi.clientlibrary.model.Channel;
import br.com.pdsars.notificationapi.clientlibrary.model.Notification;

public interface NotificationClient {
    @Nonnull
    Map<String, UUID> send(@Nonnull String channelId, @Nonnull Notification notification);

    interface Builder {
        @Nonnull
        Builder withChannels(@Nonnull List<Channel> channels);

        @Nonnull
        Builder withCategories(@Nonnull List<Category> categories);

        @Nonnull
        NotificationClient build();
    }
}
