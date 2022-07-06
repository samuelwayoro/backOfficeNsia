CREATE OR REPLACE FUNCTION emb_getlib2nom(code CHAR,cle CHAR) 
RETURN CHAR
IS

libelle CHAR(30);

BEGIN
SELECT lib2 INTO libelle FROM bknom
WHERE ctab=code AND cacc=cle;

RETURN trim(libelle);
END emb_getlib2nom;
/
