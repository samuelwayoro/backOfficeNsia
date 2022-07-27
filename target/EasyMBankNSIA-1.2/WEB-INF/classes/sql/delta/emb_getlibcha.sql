CREATE OR REPLACE FUNCTION emb_getlibcha(chapitre IN BKCHAP.cha%TYPE) 
RETURN BKCHAP.lib%TYPE
IS

libelle BKCHAP.lib%TYPE;

BEGIN
SELECT lib INTO libelle FROM bkchap WHERE cha = chapitre;

RETURN libelle;
END emb_getlibcha;
