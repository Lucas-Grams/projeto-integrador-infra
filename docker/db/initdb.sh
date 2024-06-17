#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER keycloak WITH PASSWORD 'keycloak';
    CREATE DATABASE keycloak WITH OWNER keycloak;
    GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak;
    CREATE DATABASE notificacao WITH OWNER postgres;
    GRANT ALL PRIVILEGES ON DATABASE notificacao TO postgres;

    insert into endereco (id, rua, cep, numero, complemento, bairro, cidade, uf, latitude, longitude)
    values (DEFAULT, 'Av. Roraima', '97105-900', '1000', null, 'Camobi', 'Santa Maria', 'RS', null, null);

    insert into usuario (id, cpf, nome, email, senha, data_cadastro, ultimo_acesso, id_endereco, ativo, foto, uuid, ultima_atualizacao_cadastro)
    values (DEFAULT, '05354634059','ADMIN 2','admin@admin2', '', CURRENT_DATE, null, 1, true,
            null, '920ac960-6389-4f93-8f36-c86c2c9e7091', null);

    insert into usuario (id, cpf, nome, email, senha, data_cadastro, ultimo_acesso, id_endereco, ativo, foto, uuid, ultima_atualizacao_cadastro)
    values (DEFAULT, '53934848010', 'Usuario DIP 2' ,'dip@dip2', '', CURRENT_DATE, null, 1,
            true, null, '7d473de7-361e-498c-818d-e93067542ae2', null);

EOSQL


echo dump finalizado
psql -v ON_ERROR_STOP=1 --username "postgres" --dbname "postgres" -c "CREATE EXTENSION postgis;"
