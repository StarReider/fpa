ALTER TABLE Transactions
ADD COLUMN target_account_id INT REFERENCES Accounts(account_id);


ALTER TABLE Transactions
RENAME COLUMN account_id TO source_account_id;