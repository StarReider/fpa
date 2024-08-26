CREATE TYPE currency_type AS ENUM ('FIAT', 'CRYPTO');

ALTER TABLE Currencies
ADD COLUMN type currency_type DEFAULT 'FIAT';