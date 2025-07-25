DROP TABLE IF EXISTS addresses;
CREATE TABLE addresses (
    address_id UUID PRIMARY KEY,
    country VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    street VARCHAR NOT NULL,
    house VARCHAR NOT NULL,
    flat VARCHAR NOT NULL
);

DROP TYPE IF EXISTS delivery_state CASCADE;
CREATE TYPE delivery_state AS ENUM ('CREATED', 'IN_PROGRESS', 'DELIVERED', 'FAILED', 'CANCELLED');

DROP TABLE IF EXISTS deliveries CASCADE;
CREATE TABLE deliveries (
    delivery_id UUID PRIMARY KEY,
    from_address_id UUID NOT NULL REFERENCES addresses (address_id) ON DELETE CASCADE,
    to_address_id UUID NOT NULL REFERENCES addresses (address_id) ON DELETE CASCADE,
    state delivery_state NOT NULL,
    order_id UUID NOT NULL
);