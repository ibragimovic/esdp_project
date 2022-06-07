create table favorites
(
    id         bigserial not null
        constraint favorites_pkey
            primary key,
    product_id bigint    not null
        constraint fk6sgu5npe8ug4o42bf9j71x20c
            references products,
    user_id    bigint    not null
        constraint fkk7du8b8ewipawnnpg76d55fus
            references users
);