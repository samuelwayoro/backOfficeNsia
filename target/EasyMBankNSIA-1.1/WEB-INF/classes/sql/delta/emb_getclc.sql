CREATE OR REPLACE FUNCTION emb_getclc(agence IN BKCOM.age%TYPE,compte IN BKCOM.ncp%TYPE,devise IN BKCOM.dev%TYPE) 
RETURN BKCOM.clc%TYPE
IS

cleinterne BKCOM.clc%TYPE;

BEGIN
SELECT clc INTO cleinterne FROM bkcom WHERE age = agence AND ncp = compte
AND dev = devise AND suf = ' ';

RETURN (cleinterne);

END emb_getclc;
/
