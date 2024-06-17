package br.com.pdsars.notificationapi.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @OneToMany
    @JoinTable(
        name = "account_disabled_channel",
        joinColumns = @JoinColumn(name = "account_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "channel_id", nullable = false)
    )
    @ToString.Exclude
    private Set<Channel> disabledChannels = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
