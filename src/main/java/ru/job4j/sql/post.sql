CREATE TABLE post (
    id      serial primary key,
    name    varchar(2000),
    text    varchar(10000),
    link    varchar(2000),
    created timestamp,
    CONSTRAINT post_unique UNIQUE (name)
)