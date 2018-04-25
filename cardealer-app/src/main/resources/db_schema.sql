CREATE DATABASE carDealer;
CREATE TABLE cars (
	id BIGINT NOT NULL auto_increment,
	make VARCHAR(200),
	model VARCHAR(200),
	year INT,
	price DOUBLE,
	PRIMARY KEY (id)
);