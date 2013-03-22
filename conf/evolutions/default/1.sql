# --- First database schema





# --- !Ups

DROP TABLE IF EXISTS Person;

create table Person (
  id                        bigint not null auto_increment,
  name                      varchar(255),
  birthday                	timestamp,
  address                   varchar(255),
  street                   	varchar(255),
  cp                   		bigint,
  city                   	varchar(255),
  addressXML				TEXT,
  primary key (id))
;
