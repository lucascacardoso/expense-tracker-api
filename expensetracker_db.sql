drop database expensetrackerdb;
drop user expensetracker;
create user expensetracker with password 'password';
create database expensetrackerdb with template=template0 owner=expensetracker;
\connect expensetrackerdb expensetracker;
alter default privileges grant all on tables to expensetracker;
alter default privileges grant all on sequences to expensetracker;

/*
 * 
 * To use with JDBCAuthentication
 * 
create table et_users(
	email varchar(30) not null primary key,
	first_name varchar(20) not null,
	last_name varchar(20) not null,
	password text not null,
	enabled boolean not null DEFAULT true
);

create table authorities (
	email varchar(20) not NULL,
	authority varchar(20) not NULL,
	constraint foreign_authorities_users_1 foreign key(email) references et_users(email)	
);

create unique index ix_auth_email on authorities (email,authority);

*/

/*
insert into et_users (email, first_name, last_name, password, enabled)
  values ('lucas@teste.com',
    'Lucas',
    'Camargo',
    '$2a$10$wBUOEWhUyvt0iYrM9.rDBunHbQQt.RwdzYL7Tz.D7mmXSQKM0GYf6',
    true);

insert into authorities (email, authority)
  values ('lucas@teste.com', 'ROLE_ADM');
*/