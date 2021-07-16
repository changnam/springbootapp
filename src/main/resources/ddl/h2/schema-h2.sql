-- schema.sql
drop table  if exists cars;

CREATE TABLE cars(id bigint auto_increment primary key,
    name VARCHAR(255), price INT);