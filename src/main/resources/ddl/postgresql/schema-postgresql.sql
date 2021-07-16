-- schema.sql
drop table  if exists cars ;

CREATE TABLE cars(id serial primary key ,
    name VARCHAR(255), price INT);

