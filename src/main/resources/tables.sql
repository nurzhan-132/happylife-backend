create table users (
                       id NUMBER GENERATED ALWAYS AS IDENTITY START WITH 1,
                       PRIMARY KEY (id),
                       username VARCHAR(20) NOT NULL UNIQUE,
                       email VARCHAR(50) NOT NULL UNIQUE,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       password VARCHAR(120) NOT NULL,
                       job_title VARCHAR(255) NOT NULL,
                       zip_code NUMBER(3) NOT NULL
--created DATE DEFAULT(sysdate),
--updated DATE DEFAULT(sysdate),
--status VARCHAR2(25) NOT NULL
);

create table role (
                      id NUMBER GENERATED ALWAYS AS IDENTITY START WITH 1,
                      PRIMARY KEY (id),
                      name VARCHAR(20)
--created DATE DEFAULT(sysdate),
--updated DATE DEFAULT(sysdate),
--status VARCHAR2(25) NOT NULL
);

create table user_roles (
                            user_id NUMBER ,
                            role_id NUMBER,
                            CONSTRAINT fk_user_roles_user FOREIGN KEY(user_id)
                                REFERENCES users(id),
                            CONSTRAINT fk_user_roles_role FOREIGN KEY(role_id)
                                REFERENCES role(id)
);

create table periodical (
                            id NUMBER GENERATED ALWAYS AS IDENTITY START WITH 1,
                            PRIMARY KEY (id),
                            publisher varchar2(255),
                            name varchar2(255),
                            description varchar2(4000),
                            available number(1) default(1) not null,
                            image blob
);

create table orders (
                        id number generated always as IDENTITY(start with 1 increment by 1),
                        primary key (id),
                        employee_id NUMBER NOT NULL,
                        periodical_id NUMBER NOT NULL,
                        order_date DATE,
                        order_status VARCHAR(32) DEFAULT('waiting'),
                        constraint emp_fk foreign key(employee_id)
                            references users(id),
                        constraint per_fk foreign key(periodical_id)
                            references periodical(id)
);
