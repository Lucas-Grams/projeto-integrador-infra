package br.com.pdsars.notificationapi.api.repository;

import br.com.pdsars.notificationapi.api.model.Client;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Nonnull
    Optional<Client> findByOauthClientId(@Nonnull String oauthClientId);
}
