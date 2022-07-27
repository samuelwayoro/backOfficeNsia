CREATE OR REPLACE FUNCTION emb_getnomcli(client IN BKCLI.cli%TYPE) 
RETURN BKCLI.nomrest%TYPE
IS 

nomcli BKCLI.nom%TYPE;
precli BKCLI.pre%TYPE;
nompre BKCLI.nomrest%TYPE;

BEGIN
SELECT nom,pre INTO nomcli,precli FROM
bkcli WHERE cli = client;

nompre := TRIM(nomcli)||' '||TRIM(precli);

RETURN trim(nompre);

END emb_getnomcli;
/
