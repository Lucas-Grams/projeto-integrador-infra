CREATE TABLE IF NOT EXISTS client
(
    id              BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    keycloak_id     UUID   NOT NULL UNIQUE,
    oauth_client_id TEXT   NOT NULL UNIQUE CHECK ( TRIM(oauth_client_id) <> '' ),
    last_seen       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS category
(
    id          BIGINT    NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    external_id TEXT      NOT NULL UNIQUE CHECK ( TRIM(external_id) <> '' ),
    name        TEXT      NOT NULL UNIQUE CHECK ( TRIM(name) <> '' ),
    client_id   BIGINT    NOT NULL REFERENCES client (id),
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS channel
(
    id          BIGINT    NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    external_id TEXT      NOT NULL CHECK ( TRIM(external_id) <> '' ),
    name        TEXT      NOT NULL CHECK ( TRIM(name) <> '' ),
    description TEXT      NOT NULL CHECK ( TRIM(description) <> '' ),
    importance  TEXT      NOT NULL CHECK ( TRIM(importance) <> '' ),
    client_id   BIGINT    NOT NULL REFERENCES client (id),
    category_id BIGINT    NOT NULL REFERENCES category (id),
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted     BOOLEAN   NOT NULL DEFAULT FALSE,
    UNIQUE (external_id, client_id)
);

CREATE TABLE IF NOT EXISTS account
(
    id         BIGINT                   NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    email      TEXT                     NOT NULL UNIQUE CHECK ( TRIM(email) <> '' ),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS account_disabled_channel
(
    id         BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    account_id BIGINT NOT NULL REFERENCES account (id),
    channel_id BIGINT NOT NULL REFERENCES channel (id),
    UNIQUE (account_id, channel_id)
);

CREATE TABLE IF NOT EXISTS notification
(
    id         BIGINT                   NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title      TEXT                     NOT NULL CHECK ( TRIM(title) <> '' ),
    body       TEXT                     NOT NULL CHECK ( TRIM(body) <> '' ),
    meta       JSONB                    NOT NULL DEFAULT '{}'::JSONB,
    channel_id BIGINT                   NOT NULL REFERENCES channel (id),
    account_id BIGINT                   NOT NULL REFERENCES account (id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
)

