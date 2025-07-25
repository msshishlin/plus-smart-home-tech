DROP TABLE IF EXISTS shopping_carts CASCADE;
CREATE TABLE shopping_carts (
    shopping_cart_id UUID PRIMARY KEY,
    username VARCHAR NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

DROP TABLE IF EXISTS products;
CREATE TABLE products (
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL,
    shopping_cart_id UUID NOT NULL REFERENCES shopping_carts (shopping_cart_id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, shopping_cart_id)
);
