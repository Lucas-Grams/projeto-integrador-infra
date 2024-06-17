package br.com.pdsars.notificationapi.clientlibrary.model;

import javax.annotation.*;
import br.com.pdsars.notificationapi.clientlibrary.Assert;
import java.util.*;

public final class Notification {
    private final String       title;
    private final String       body;
    private final List<String> users;
    private final Map<String, String> meta;

    private Notification(
            String title,
            String body,
            List<String> users,
            Map<String, String> meta
    ) {
        this.title = title;
        this.body  = body;
        this.users = users;
        this.meta  = meta;
    }

    public List<String> getUsers() {
        return Collections.unmodifiableList(this.users);
    }

    @Nonnull
    public Map<String, String> getMeta() {
        return Collections.unmodifiableMap(this.meta);
    }

    @Nonnull
    public String getTitle() {
        return this.title;
    }

    @Nonnull
    public String getBody() {
        return this.body;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Notification that = (Notification) obj;
        return Objects.equals(this.title, that.title) &&
                Objects.equals(this.body, that.body) &&
                Objects.equals(this.users, that.users) &&
                Objects.equals(this.meta, that.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, users, meta);
    }

    @Override
    public String toString() {
        return "Notification[" +
                "title=" + title + ", " +
                "text=" + body + ", " +
                "users=" + users + ", " +
                "meta=" + meta + ']';
    }

    public static class Builder {
        private String              title;
        private String              body;
        private List<String>        users;
        private Map<String, String> meta = new HashMap<>();

        @Nonnull
        public Builder title(@Nonnull String title) {
            this.title = Assert.nonBlank(title, "Parameter `title` cannot be null or blank");
            return this;
        }

        @Nonnull
        public Builder body(@Nonnull String body) {
            this.body = Assert.nonBlank(body, "Parameter `body` cannot be null or blank");
            return this;
        }

        @Nonnull
        public Builder users(@Nonnull List<String> users) {
            this.users = new ArrayList<>(
                    Assert.nonEmpty(users, "`users` cannot be null or empty")
            );
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
        public Notification build() {
            return new Notification(title, body, users, meta);
        }
    }
}
