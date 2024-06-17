package br.com.pdsars.notificationapi.clientlibrary;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Assert {

    private Assert() { }

    @Nonnull
    public static String nonBlank(
        @Nullable String s,
        @Nonnull String msg
    ) throws IllegalArgumentException {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException(msg);
        }
        return s;
    }

    @Nonnull
    public static <E, T extends Collection<E>> T nonEmpty(
        @Nullable T collection,
        @Nonnull String msg
    ) throws IllegalArgumentException {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(msg);
        }
        return collection;
    }

    @Nonnull
    public static <K, V, T extends Map<K, V>> T nonEmpty(
        @Nullable T map,
        @Nonnull String msg
    ) throws IllegalArgumentException {
        nonEmpty(map.entrySet(), msg);
        return map;
    }

}
