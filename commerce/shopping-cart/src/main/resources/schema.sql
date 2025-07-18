DROP TABLE IF EXISTS cart CASCADE;
CREATE TABLE cart (
    cart_id UUID PRIMARY KEY,
    username VARCHAR NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

DROP TABLE IF EXISTS products;
CREATE TABLE products (
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL,
    cart_id UUID NOT NULL REFERENCES cart ON DELETE CASCADE,
    PRIMARY KEY (product_id, cart_id)
);
