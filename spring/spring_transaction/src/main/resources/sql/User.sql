-- auto-generated definition
create table user
(
    id   bigint auto_increment
        primary key,
    name varchar(32) null,
    age  tinyint(1)  null
)
    comment '用户表';

