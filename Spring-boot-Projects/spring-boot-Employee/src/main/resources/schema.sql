  
create table department(
	ID int not null primary key,
	 NAME varchar_ignorecase(50) not null,
      SALARY_MIN_RANGE double,
      SALARY_MAX_RANGE double);
     
create table employee(
	ID int not null primary key,
	 NAME varchar_ignorecase(50) not null,
      MANAGER_NAME varchar_ignorecase(50) not null,
      DEPARTMENT varchar_ignorecase(50) not null,
      SALARY double);
