create table items(
    id serial primary key,
    description text not null,
    created timestamp not null,
    done boolean not null
);