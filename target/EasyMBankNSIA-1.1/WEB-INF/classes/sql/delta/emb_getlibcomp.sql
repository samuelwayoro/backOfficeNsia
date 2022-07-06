CREATE OR REPLACE FUNCTION emb_getlibcomp(v_iden CHAR,v_noseq INTEGER,v_dag DATE)
RETURN CHAR
IS

v_lib CHAR(60);
v_lib1 CHAR(120);
compteur SMALLINT;
w_nbe SMALLINT;

BEGIN
v_lib1 := ' ';
v_lib := ' ';
SELECT count(ord) INTO w_nbe FROM bkhisl WHERE iden = v_iden AND noseq = v_noseq 
AND dag = v_dag;

FOR compteur IN 1..w_nbe
LOOP
SELECT lib INTO v_lib FROM bkhisl WHERE iden = v_iden AND noseq = v_noseq 
AND dag = v_dag AND ord = compteur;

v_lib1 := TRIM(v_lib1)||' '|| TRIM(v_lib);

END LOOP;

RETURN TRIM(v_lib1);

END emb_getlibcomp;
/
