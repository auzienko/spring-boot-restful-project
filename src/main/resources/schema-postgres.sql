drop  schema if exists  rstf cascade;
create schema if not exists rstf;

create table if not exists rstf.tbl_users
(
    id              BIGSERIAL PRIMARY KEY,
    first_name      varchar,
    last_name       varchar,
    role            smallint,
    password        varchar,
    login           varchar
);

create table if not exists rstf.tbl_courses
(
    id              BIGSERIAL PRIMARY KEY,
    start_time      timestamp,
    end_date        timestamp,
    name            varchar,
    description     varchar,
    login           VARCHAR
);

create table if not exists rstf.tbl_lessons
(
    id              BIGSERIAL PRIMARY KEY,
    start_time      timestamp,
    end_time        timestamp,
    day_of_week     smallint,
    teacher         varchar
);

create table if not exists rstf.tbl_course_lessons(
    course_id       bigint not null,
    lesson_id       bigint not null,
    foreign key (course_id) references rstf.tbl_courses(id),
    foreign key (lesson_id) references rstf.tbl_lessons(id),
    primary key (course_id, lesson_id)
);

create table if not exists rstf.tbl_course_users(
    course_id       bigint not null,
    user_id       bigint not null,
    foreign key (course_id) references rstf.tbl_courses(id),
    foreign key (user_id) references rstf.tbl_users(id),
    primary key (course_id, user_id)
);


