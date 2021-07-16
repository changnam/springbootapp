-- schema.sql
drop table  if exists cars ;

CREATE TABLE cars(id int primary key auto_increment,
    name VARCHAR(255), price INT);


drop table if exists beans ;

CREATE TABLE beans(beanName varchar(512), description varchar(2048));