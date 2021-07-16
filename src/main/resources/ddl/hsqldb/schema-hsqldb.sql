-- schema.sql
drop table cars ;

CREATE TABLE cars(id INT IDENTITY ,
    name VARCHAR(255), price INT);


drop table beans ;

CREATE TABLE beans(beanName varchar(512), description varchar(2048));