package br.com.pdsars.notificationapi.api.repository;

import br.com.pdsars.notificationapi.api.model.Channel;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    @Query("""
        SELECT ch
            FROM Client cli
                 JOIN cli.channels ch
            WHERE cli.id = :clientId
              AND ch.externalId = :externalId
            """)
    @Nonnull
    Optional<Channel> findByExternalIdForClient(@Nonnull Long clientId,
                                                @Nonnull String externalId);

    @Query("SELECT ch" + " FROM Client cli" + "      JOIN cli.channels ch"
        + " WHERE cli.oauthClientId = :oauthClientId"
        + " ORDER BY ch.name, ch.externalId")
    @Nonnull
    List<Channel> findAllForClient(@Nonnull String oauthClientId);
}
