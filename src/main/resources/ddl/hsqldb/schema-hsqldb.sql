-- schema.sql
drop table cars ;

CREATE TABLE cars(id INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    name VARCHAR(255), price INT);


drop table beans ;

CREATE TABLE beans(beanName varchar(512), description varchar(2048));