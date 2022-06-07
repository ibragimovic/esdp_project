create table images
(
    id         bigserial not null
        constraint images_pkey
            primary key,
    path       varchar(255),
    product_id bigint    not null
        constraint fkghwsjbjo7mg3iufxruvq6iu3q
            references products
);