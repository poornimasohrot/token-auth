
CREATE TABLE User (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  password VARCHAR(20) NOT NULL,
  user_name VARCHAR(250) NOT NULL UNIQUE,
  home_phone_no VARCHAR(50) DEFAULT NULL,
  office_phone_no VARCHAR(50) DEFAULT NULL,
  fax_no VARCHAR(50) DEFAULT NULL
);

CREATE TABLE Address (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  street_name VARCHAR(250) NOT NULL,
  city VARCHAR(30) NOT NULL,
  country VARCHAR(30) NOT NULL,
  postal_code VARCHAR(50) NOT NULL,
  user_id INT NOT NULL,
  foreign key (user_id) references User(ID)
  
);

CREATE TABLE Token (
  token_id INT AUTO_INCREMENT  PRIMARY KEY,
  token VARCHAR(250) NOT NULL,
  issued_date_time TIMESTAMP NOT NULL,
  expired_date_time TIMESTAMP NOT NULL,
  user_id INT NOT NULL,
  foreign key (user_id) references User(ID)
);
