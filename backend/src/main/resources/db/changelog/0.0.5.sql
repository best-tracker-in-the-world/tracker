CREATE TABLE food_log (
  id bigint NOT NULL AUTO_INCREMENT,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  user_id bigint NOT NULL,
  name VARCHAR(100) NOT NULL,
  prot float DEFAULT NULL,
  carb float DEFAULT NULL,
  fat float DEFAULT NULL,
  calorage float NOT NULL,
  weight float NOT NULL,
  food_id bigint DEFAULT NULL,
  favorite_food_id bigint DEFAULT NULL,
  PRIMARY KEY (id)
);