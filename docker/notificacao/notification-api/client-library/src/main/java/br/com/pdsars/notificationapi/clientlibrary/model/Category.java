package br.com.pdsars.notificationapi.clientlibrary.model;

import java.util.Objects;

public final class Category {
    private final String id;
    private final String name;

    public Category(String id, String name) {
        this.id   = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        final Category that = (Category) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Category[" +
                "id=" + id + ", " +
                "name=" + name + ']';
    }
}
