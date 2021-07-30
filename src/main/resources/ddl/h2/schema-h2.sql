-- schema.sql
drop table if exists cars;
drop table if exists authorities; -- 이것 먼저 drop 해야 함. constraint 때문
drop table if exists users;


CREATE TABLE cars(id bigint auto_increment primary key,
    name VARCHAR(255), price INT);
    


create table users(
	username varchar(64) not null primary key,
	password varchar(512) not null,
	enabled boolean not null
);



create table authorities (
	username varchar(64) not null,
	authority varchar(64) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);
    