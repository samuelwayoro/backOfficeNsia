CREATE OR REPLACE FUNCTION emb_getchacpt(agence IN BKCOM.age%TYPE,compte IN BKCOM.ncp%TYPE,devise IN BKCOM.dev%TYPE)  
RETURN BKCOM.cha%TYPE
IS

chapitre BKCOM.cha%TYPE;

BEGIN
SELECT cha INTO chapitre FROM bkcom
WHERE age = agence AND ncp = compte AND dev = devise
AND suf = ' ';

RETURN chapitre;

END emb_getchacpt;
/
