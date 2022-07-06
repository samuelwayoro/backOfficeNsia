CREATE OR REPLACE FUNCTION emb_getgescli(client IN BKCLI.cli%TYPE) 
RETURN BKCLI.ges%TYPE
IS

gestionnaire BKCLI.ges%TYPE;

BEGIN
SELECT ges INTO gestionnaire FROM bkcli WHERE cli = client;

RETURN gestionnaire;

END emb_getgescli;
/