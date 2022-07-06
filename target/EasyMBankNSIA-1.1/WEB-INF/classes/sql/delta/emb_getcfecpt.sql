CREATE OR REPLACE FUNCTION emb_getcfecpt(agence CHAR,compte CHAR,devise CHAR) 
RETURN CHAR
IS

w_cfe CHAR(1);

BEGIN
SELECT cfe INTO w_cfe FROM bkcom WHERE age = agence AND ncp = compte
AND dev = devise AND suf = ' ';

RETURN w_cfe;

END emb_getcfecpt;	
/
