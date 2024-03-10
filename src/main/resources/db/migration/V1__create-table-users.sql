CREATE TABLE users(
  id BIGINT NOT NULL AUTO_INCREMENT, 
  email VARCHAR(100) NOT NULL UNIQUE,
  username VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,

  PRIMARY KEY(id)
);