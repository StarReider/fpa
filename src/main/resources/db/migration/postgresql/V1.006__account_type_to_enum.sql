CREATE TYPE account_type AS ENUM ('CHECKING', 'SAVINGS', 'CRYPTO');

ALTER TABLE Accounts
RENAME COLUMN account_type TO type;

ALTER TABLE Accounts
ALTER COLUMN type SET DATA TYPE account_type USING type::account_type,
ALTER COLUMN type SET DEFAULT 'CHECKING';