FROM php:8.3-cli

RUN apt-get update && \
    apt-get install --yes --force-yes \
    cron openssl

RUN curl -sSLf \
        -o /usr/local/bin/install-php-extensions \
        https://github.com/mlocati/docker-php-extension-installer/releases/latest/download/install-php-extensions && \
    chmod +x /usr/local/bin/install-php-extensions

RUN install-php-extensions  gettext iconv intl  tidy zip sockets
RUN install-php-extensions  pgsql mysqli
RUN install-php-extensions  pdo_mysql pdo_pgsql
RUN install-php-extensions @composer


EXPOSE 80

CMD ["tail", "-f", "/dev/null"]