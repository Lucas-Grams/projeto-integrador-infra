FROM postgres:13

RUN apt update
RUN apt install postgis postgresql-13-postgis-3-scripts -y
COPY initdb.sh /docker-entrypoint-initdb.d/
#COPY dummy_dump.sql.gz /docker-entrypoint-initdb.d/
