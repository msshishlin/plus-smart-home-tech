DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products (
    product_id UUID PRIMARY KEY,
    quantity INTEGER NOT NULL DEFAULT 0,
    fragile BOOLEAN NOT NULL DEFAULT FALSE,
    width REAL NOT NULL,
    height REAL NOT NULL,
    depth REAL NOT NULL,
    weight REAL NOT NULL
);