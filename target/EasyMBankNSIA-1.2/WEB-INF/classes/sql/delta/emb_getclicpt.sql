CREATE OR REPLACE FUNCTION emb_getclicpt(agence IN BKCOM.age%TYPE,compte IN BKCOM.ncp%TYPE,devise IN BKCOM.dev%TYPE) 
RETURN BKCOM.cli%TYPE
IS

client BKCOM.cli%TYPE;

BEGIN
SELECT cli INTO client FROM bkcom WHERE age = agence AND ncp = compte
AND dev = devise AND suf = ' ';

RETURN client;

END emb_getclicpt;
