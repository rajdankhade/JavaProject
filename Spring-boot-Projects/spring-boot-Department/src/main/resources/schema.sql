create table book(
      id int not null primary key,
      name varchar_ignorecase(50) not null,
      author varchar_ignorecase(50) not null,
      price int);
      
create table department(
	ID int not null primary key,
	 NAME varchar_ignorecase(50) not null,
      SALARY_MIN_RANGE double,
      SALARY_MAX_RANGE double);
