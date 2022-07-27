CREATE OR REPLACE FUNCTION EMB_GETMINISTMT(P_NCP CHAR, P_NBL INTEGER)
RETURN CLOB
IS
v_Statut  CHAR(1);
v_StatutMsg CHAR(100);
v_StatutData CHAR(100);
CH1 CHAR(1000);
CH2 CHAR(500);
CH3 CHAR(2000);
CH5 CHAR(7);
CH6 CHAR(30);
CH7 CHAR(20);
CH8 CHAR(15);
CH9 CHAR(3);
v_tel CHAR(120);
v_cli BKCLI.cli%TYPE;
v_nomrest BKCLI.nomrest%TYPE;
v_agec BKCLI.age%TYPE;
v_ges BKCLI.ges%TYPE;
v_email BKEMACLI.email%TYPE;
v_langue CHAR(3);
v_Resultat CLOB;
v_nbe INTEGER;


CURSOR cur_com IS
select * from 
(select ncp,pie,TO_CHAR (CAST (dco AS TIMESTAMP ( 3 )),'yyyy-mm-dd hh24:mi:ss') dco,ope,trim(emb_getlib2nom('005',dev))dev,sen,mon,(trim(lib)||' '||trim(emb_getlibcomp(iden,noseq,dag))) libelle 
from bkhis 
where ncp= substr(P_NCP,6,11)
and emb_getcfecpt(substr(P_NCP,1,5),substr(P_NCP,6,11),'952')='N' 
and suf=' ' order by dco desc)
where rownum<=P_NBL;

BEGIN
CH3:='';
v_langue:='F';
CH5:=']}]}]}';
v_statut:=0;
CH6:='{"RESULTSET":[{"Statut":"';
CH7:='","StatutMsg":"';
CH8:='","StatutData":';
CH9:='}]}';
 -------------------Vérifier l'existence du compte---------------------------------------------------------
 
	select count(*) into v_nbe from bkcom where age=substr(P_NCP,1,5) and ncp= substr(P_NCP,6,11) and dev='952';
	IF  (v_nbe=0) THEN
		v_Statut:=1;	
		v_StatutMsg:='COMPTE INEXISTANT';
		v_StatutData:='null';
		v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
		
	ELSE
	
    --------------Recupérer les ecritures du client-----------------------------------------------
	    v_Statut:=0;
		select cli,nomrest,decode(tcli,'1','PAR','2','ENT','3','ENT'),emb_gettelcli(cli),emb_getemailcli(cli,lpad(tcli,3,0)) 
		into v_cli,v_nomrest,v_agec,v_tel,v_email
		from bkcli
		where cli=emb_getclicpt(substr(P_NCP,1,5),substr(P_NCP,6,11),'952');
		
		CH1:='{"RESULTSET":[{"Statut":"'||trim(v_Statut)||'","StatutMsg":"","StatutData":
		[{"Client":"'||trim(v_cli)||'","Nom":"'||trim(v_nomrest)||'","Agec":"'||trim(v_agec)||'","Tel":"'||trim(v_tel)||'",
		"Langue":"'||trim(v_langue)||'","Email":"'||trim(v_email)||'","mvts":[';
		FOR rec_com IN cur_com
		LOOP
			CH2:=',{"Compte":"'||trim(rec_com.ncp)||'","tranrefno":"'||trim(rec_com.pie)||'","trandate":"'||trim(rec_com.dco)||'","trantype":"'||trim(rec_com.ope)||'","ccy":"'||trim(rec_com.dev)||'","crdr":"'||trim(rec_com.sen)||'"
			,"amount":"'||trim(rec_com.mon)||'","narration":"'||trim(rec_com.libelle)||'"}';
			CH3:= trim(CH3)||trim(CH2);
		END LOOP;
		v_Resultat:= trim(CH1)||trim(CH3)||trim(CH5);
		v_Resultat:= replace(v_Resultat,'"mvts":[,','"mvts":[');
	  
	END IF;
	
    return trim(v_Resultat);

END EMB_GETMINISTMT;

/
