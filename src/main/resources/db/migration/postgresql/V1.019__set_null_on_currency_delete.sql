ALTER TABLE Accounts
DROP CONSTRAINT accounts_currency_fkey,
ADD CONSTRAINT accounts_currency_fkey 
    FOREIGN KEY (currency) REFERENCES Currencies(currency_code) DEFERRABLE 
    INITIALLY DEFERRED;
	
ALTER TABLE Accounts 
ALTER currency DROP NOT NULL;
	
CREATE OR REPLACE PROCEDURE public.delete_fiat()
LANGUAGE plpgsql
AS $BODY$
BEGIN
	CREATE TEMP TABLE non_fiat_currencies ON COMMIT DROP AS
	SELECT * FROM currencies WHERE type != 'FIAT';
	
	DELETE FROM currencies;
	
	INSERT INTO currencies(currency_code, currency_name, exchange_rate, base_currency, created_at, type)
	SELECT * FROM non_fiat_currencies;
END;
$BODY$;