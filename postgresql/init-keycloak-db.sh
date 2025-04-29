#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER keycloak_user WITH PASSWORD 'keycloak_pass';
    CREATE DATABASE keycloak_db;
    GRANT ALL PRIVILEGES ON DATABASE keycloak_db TO keycloak_user;

    -- Connect to keycloak_db and grant permissions
    \c keycloak_db
    GRANT ALL ON SCHEMA public TO keycloak_user;
    GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO keycloak_user;
    GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO keycloak_user;
EOSQL