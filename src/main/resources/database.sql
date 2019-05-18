create database if not exists `poetry`;
use `poetry`;
create table if not exists `poetry_info`(
title varchar(64) not null ,
dynasty varchar(32) not null,
author varchar(12) not null,
content varchar(1024) not null
);

select title,dynasty,author,content from poetry_info limit 2;

select count(*) as count,author  from poetry_info group by author;