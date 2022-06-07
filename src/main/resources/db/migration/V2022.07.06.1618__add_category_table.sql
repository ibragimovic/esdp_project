create table category
(
    id        bigserial not null
        constraint category_pkey
            primary key,
    name      varchar(255),
    parent_id bigint
        constraint fk2y94svpmqttx80mshyny85wqr
            references category
);