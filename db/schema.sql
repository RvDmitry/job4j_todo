create table items(
    id serial primary key,
    number int not null,
    description text not null,
    created timestamp not null,
    done boolean not null,
    user_id int references users(id) not null
);

create table users(
    id serial primary key,
    name text not null,
    email text unique not null,
    password text not null
);

create table categories(
    id serial primary key,
    name text unique not null,
    user_id int references users(id) not null
);