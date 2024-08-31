ALTER TABLE Accounts
ADD COLUMN interest_account_id INT REFERENCES Accounts(account_id);