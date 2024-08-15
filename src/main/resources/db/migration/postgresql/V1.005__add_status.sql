CREATE TYPE transaction_status AS ENUM ('PENDING', 'COMPLETED', 'FAILED', 'CANCELED');

ALTER TABLE transactions
ADD COLUMN status transaction_status NOT NULL DEFAULT 'PENDING';