package br.com.pdsars.notificationapi.clientlibrary.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public final class Channel {
    private final String id;
    private final String name;
    private final Importance importance;
    private final String description;
    private final String categoryId;
    private final Map<String, String> meta;

    Channel(
        final String id,
        final String name,
        final Importance importance,
        final String description,
        final String categoryId,
        final Map<String, String> meta
    ) {
        this.id = id;
        this.name = name;
        this.importance = importance;
        this.description = description;
        this.categoryId = categoryId;
        this.meta = meta;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Importance getImportance() {
        return this.importance;
    }

    public String getDescription() {
        return this.description;
    }

    public Map<String, String> getMeta() {
        return this.meta;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public enum Importance {
        LOW, DEFAULT, HIGH
    }

    public static class Builder {
        private String id;
        private String name;
        private Channel.Importance importance = Channel.Importance.DEFAULT;
        private String description            = null;
        private String categoryId;
        private Map<String, String> meta = new HashMap<>();

        /**
         * @param id unique id of the channel,
         *           this parameter cannot be null or empty
         *
         * @return the same instance of the builder, so it can be chained
         */
        @Nonnull
        public Builder id(@Nonnull final String id) {
            this.id = id;
            return this;
        }

        /**
         * @param name user facing name of the channel,
         *             this parameter cannot be null or empty
         * @return the same instance of the builder, so it can be chained
         */
        @Nonnull
        public Builder name(@Nonnull final String name) {
            this.name = name;
            return this;
        }

        /**
         * @param importance The level of importance of the channel,
         *                   can not be null,
         *                   default value is {@link Channel.Importance#DEFAULT}
         *
         * @return the same instance of the builder, so it can be chained
         */
        public Builder importance(@Nonnull final Channel.Importance importance) {
            this.importance = Objects.requireNonNull(
                    importance,
                    "Importance of the channel cannot be null");
            return this;
        }

        /**
         * @param description User facing description of the channel
         *                    can be null.
         *
         * @return the same instance of the builder, so it can be chained
         */
        @Nonnull
        public Builder description(@Nullable final String description) {
            this.description = description;
            return this;
        }

        @Nonnull
        public Builder category(@Nonnull final Category category) {
            this.categoryId = category.getId();
            return this;
        }

        @Nonnull
        public Builder meta(@Nullable Map<String, String> meta) {
            if (meta != null && !meta.isEmpty()) {
                this.meta = new HashMap<>(meta);
            }
            return this;
        }

        @Nonnull
        public Channel build() {
            return new Channel(id, name, importance, description, categoryId, meta);
        }
    }
}
