create table if not exists person (
person_id int auto_increment  primary key,
  first_name varchar(100) not null,
  last_name varchar(100) not null,
  age tinyint,
  favorite_color varchar(50)
);

create table if not exists person_hobby (
  id int auto_increment  primary key,
  person_id int,
  hobby varchar(100) not null,
foreign key (person_id) references person(person_id)
);