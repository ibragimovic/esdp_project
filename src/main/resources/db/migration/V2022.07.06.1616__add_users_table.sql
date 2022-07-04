create table users
(
    id       bigserial not null
        constraint users_pk
            primary key,
    name     varchar   not null,
    lastname    varchar   not null,
    tel_number    varchar,
    login    varchar   not null,
    email    varchar   not null,
    password varchar,
    enabled boolean NOT NULL default true,
    role varchar(16) NOT NULL default 'USER',
    activation_code varchar
);
