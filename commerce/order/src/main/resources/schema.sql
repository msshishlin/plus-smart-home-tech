DROP TYPE IF EXISTS order_state CASCADE;
CREATE TYPE order_state AS ENUM ('NEW', 'ON_PAYMENT', 'ON_DELIVERY', 'DONE', 'DELIVERED', 'ASSEMBLED', 'PAID', 'COMPLETED', 'DELIVERY_FAILED', 'ASSEMBLY_FAILED', 'PAYMENT_FAILED', 'PRODUCT_RETURNED', 'CANCELED');

DROP TABLE IF EXISTS orders CASCADE;
CREATE TABLE orders (
    order_id UUID PRIMARY KEY,
    shopping_cart_id UUID NOT NULL,
    payment_id UUID NOT NULL,
    delivery_id UUID NOT NULL,
    state order_state NOT NULL,
    delivery_weight REAL NOT NULL,
    delivery_volume REAL NOT NULL,
    fragile BOOLEAN NOT NULL DEFAULT FALSE,
    total_price REAL NOT NULL,
    delivery_price REAL NOT NULL,
    product_price REAL NOT NULL
);

DROP TABLE IF EXISTS products;
CREATE TABLE products (
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL,
    order_id UUID NOT NULL REFERENCES orders (order_id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, order_id)
);
