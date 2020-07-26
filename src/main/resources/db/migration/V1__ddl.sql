create sequence public.hibernate_sequence;
create table user_table(
    id BIGINT NOT NULL PRIMARY KEY,
    disabled boolean,
    login varchar NOT NULL
);

create table blog_post(
    id BIGINT NOT NULL PRIMARY KEY,
    body varchar NOT NULL,
    title varchar,
    user_id BIGINT,
    CONSTRAINT FK_BLOGPOST_USER FOREIGN KEY (user_id) REFERENCES user_table
);
