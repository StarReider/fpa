CREATE TYPE interest_frequency AS ENUM ('DAILY', 'MONTHLY', 'YEARLY');

ALTER TABLE Accounts
ADD COLUMN interest_frequency interest_frequency DEFAULT 'MONTHLY';