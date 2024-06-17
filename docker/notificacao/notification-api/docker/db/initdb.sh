#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER keycloak WITH PASSWORD 'keycloak';
    CREATE DATABASE keycloak WITH OWNER keycloak;
    GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak;

    CREATE USER api WITH PASSWORD 'api';
    CREATE DATABASE api WITH OWNER api;
    GRANT ALL PRIVILEGES ON DATABASE api TO api;
EOSQL
