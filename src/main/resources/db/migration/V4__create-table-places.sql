CREATE TABLE places(
  id BIGINT NOT NULL AUTO_INCREMENT, 
  name VARCHAR(100) NOT NULL,
  description VARCHAR(260) NOT NULL,

  trip_id BIGINT NOT NULL,

  CONSTRAINT fk_trip_id FOREIGN KEY (trip_id) REFERENCES trips(id),
  PRIMARY KEY(id)
);