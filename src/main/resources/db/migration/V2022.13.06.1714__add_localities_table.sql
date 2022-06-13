create table localities
(
    id        bigserial not null
        constraint localities_pkey
            primary key,
    name      varchar(255),
    parent_id bigint
);