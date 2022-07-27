create or replace FUNCTION emb_getemailcli(client IN BKEMACLI.cli%TYPE,types IN BKEMACLI.typ%TYPE)
RETURN BKEMACLI.email%TYPE
IS

emailcli BKEMACLI.email%TYPE;

BEGIN
SELECT trim(email) INTO emailcli FROM bkemacli
WHERE cli=client AND typ=types;
IF (emailcli is null or emailcli='') THEN
   emailcli:='  ';
END IF;
RETURN emailcli;
END emb_getemailcli;

/