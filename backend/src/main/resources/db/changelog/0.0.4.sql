CREATE TABLE favorite_foods (
  id bigint NOT NULL AUTO_INCREMENT,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  user_id bigint NOT NULL,
  food_id bigint NOT NULL,
  name VARCHAR(100) NOT NULL,
  override_prot float DEFAULT NULL,
  override_carb float DEFAULT NULL,
  override_fat float DEFAULT NULL,
  override_calorage float DEFAULT NULL,
  PRIMARY KEY (id)
);