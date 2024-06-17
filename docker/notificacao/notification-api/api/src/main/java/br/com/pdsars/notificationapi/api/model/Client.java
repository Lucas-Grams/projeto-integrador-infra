package br.com.pdsars.notificationapi.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;

import java.time.Instant;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String oauthClientId;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID keycloakId;

    @Column(nullable = false)
    private Instant lastSeen = Instant.now();

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<Channel> channels = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<Category> categories = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Client client = (Client) o;
        return id != null && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
