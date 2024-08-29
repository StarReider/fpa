CREATE OR REPLACE PROCEDURE public.delete_fiat()
LANGUAGE 'sql'
AS $BODY$
	ALTER TABLE currencies DISABLE TRIGGER ALL;
	delete from currencies where type = 'FIAT';
	ALTER TABLE currencies ENABLE TRIGGER ALL;
$BODY$;