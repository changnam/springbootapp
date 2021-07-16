-- schema.sql
drop table cars;
CREATE TABLE cars(id int primary key ,
    name VARCHAR(255), price INT);
    
drop sequence cars_seq;
CREATE SEQUENCE cars_seq START WITH 1 INCREMENT BY 1 ;



