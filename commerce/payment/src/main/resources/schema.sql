DROP TYPE IF EXISTS payment_state CASCADE;
CREATE TYPE payment_state AS ENUM ('PENDING', 'SUCCESS', 'FAILED');

DROP TABLE IF EXISTS payments CASCADE;
CREATE TABLE payments (
    payment_id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    total_payment REAL NOT NULL,
    delivery_payment REAL NOT NULL,
    fee_payment REAL NOT NULL,
    state payment_state NOT NULL
);