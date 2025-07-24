DROP TABLE IF EXISTS products;
CREATE TABLE products (
    product_id UUID PRIMARY KEY,
    quantity INTEGER NOT NULL,
    fragile BOOLEAN NOT NULL,
    width REAL NOT NULL,
    height REAL NOT NULL,
    depth REAL NOT NULL,
    weight REAL NOT NULL
);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    order_id UUID NOT NULL PRIMARY KEY,
    delivery_id UUID,
    delivery_weight REAL NOT NULL,
    delivery_volume REAL NOT NULL,
    fragile BOOLEAN NOT NULL DEFAULT FALSE
);

DROP TABLE IF EXISTS booked_products;
CREATE TABLE booked_products (
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL,
    PRIMARY KEY (order_id, product_id)
)