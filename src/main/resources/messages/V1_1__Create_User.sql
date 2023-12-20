create table poke.user
(
    id         bigint auto_increment comment '유저 ID'
        primary key,
    name       varchar(30) not null comment '유저명',
    email      varchar(30) not null comment '이메일',
    password   varchar(30) not null comment '비밀번호',
    birth_date date        not null comment '생일',
    gender     varchar(5)  not null comment '성별',
    created_at datetime    not null comment '생성일',
    updated_at datetime    null comment '수정일',
    deleted_at datetime    null comment '삭제일',
    constraint EMAIL
        unique (email)
);

