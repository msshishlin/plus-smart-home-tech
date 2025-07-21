DROP TYPE IF EXISTS product_category CASCADE;
CREATE TYPE product_category AS ENUM ('LIGHTING', 'CONTROL', 'SENSORS');

DROP TYPE IF EXISTS product_state CASCADE;
CREATE TYPE product_state AS ENUM ('ACTIVE', 'DEACTIVATE');

DROP TYPE IF EXISTS quantity_state CASCADE;
CREATE TYPE quantity_state AS ENUM ('ENDED', 'FEW', 'ENOUGH', 'MANY');

DROP TABLE IF EXISTS products;
CREATE TABLE products (
  product_id UUID PRIMARY KEY,
  product_name VARCHAR NOT NULL,
  description VARCHAR NOT NULL,
  image_src VARCHAR,
  quantity_state quantity_state NOT NULL,
  product_state product_state NOT NULL,
  product_category product_category,
  price REAL NOT NULL
);