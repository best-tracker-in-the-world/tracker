CREATE TABLE foods (
  id bigint NOT NULL,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  name VARCHAR(100) NOT NULL,
  prot float NOT NULL,
  carb float NOT NULL,
  fat float NOT NULL,
  calorage float NOT NULL,
  default_weight bigint NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_foods_name (name)
);

