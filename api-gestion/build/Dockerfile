FROM postgres:15

ENV POSTGRES_DB=gestion \
    POSTGRES_USER=root \
    POSTGRES_PASSWORD=root

COPY ./initdb /docker-entrypoint-initdb.d/

EXPOSE 5432
