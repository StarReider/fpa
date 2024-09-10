CREATE OR REPLACE PROCEDURE public.delete_fiat()
LANGUAGE plpgsql
AS $BODY$
BEGIN
	CREATE TEMP TABLE non_fiat_currencies AS
	SELECT * FROM currencies WHERE type != 'FIAT';
	
	TRUNCATE TABLE currencies CASCADE;
	
	INSERT INTO currencies(currency_code, currency_name, exchange_rate, base_currency)
	SELECT * FROM non_fiat_currencies;

	COMMIT;
END;
$BODY$;