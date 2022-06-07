create table products
(
    id          bigserial not null
        constraint products_pkey
            primary key,
    description varchar(255),
    name        varchar(255),
    price       integer   not null
        constraint products_price_check
            check (price >= 1),
    category_id bigint    not null
        constraint fk1cf90etcu98x1e6n9aks3tel3
            references category,
    user_id     bigint    not null
        constraint fkdb050tk37qryv15hd932626th
            references users
);