drop table if exists books CASCADE;

create table books
(
    id    bigint generated by default as identity,
    title varchar(255) not null,
    primary key (id)
);
--select 1;
