CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_sku VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    price NUMERIC(10,2),
    status VARCHAR(50)
);
