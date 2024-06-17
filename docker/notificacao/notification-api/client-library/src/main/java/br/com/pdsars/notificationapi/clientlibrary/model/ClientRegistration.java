package br.com.pdsars.notificationapi.clientlibrary.model;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;

public class ClientRegistration {
    private final List<Category> categories;

    private final List<Channel> channels;

    public ClientRegistration(
        @Nonnull List<Category> categories,
        @Nonnull List<Channel> channels
    ) {
        this.categories = Collections.unmodifiableList(categories);
        this.channels = Collections.unmodifiableList(channels);
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public List<Channel> getChannels() {
        return this.channels;
    }
}
