CREATE OR REPLACE FUNCTION EMB_GETSIGNALETIC(P_CLI CHAR)
RETURN CHAR
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
v_agec CHAR(3);
v_nom CHAR(65);
v_dna CHAR(10);
v_cli BKCLI.cli%TYPE;
v_nomrest BKCLI.nomrest%TYPE;
v_sext BKCLI.sext%TYPE;
v_pre BKCLI.pre%TYPE;
v_did BKCLI.did%TYPE;
v_nid BKCLI.nid%TYPE;
v_vid BKCLI.nomrest%TYPE;
v_ges BKCLI.ges%TYPE;
v_tel CHAR(500);
v_email BKEMACLI.email%TYPE;
v_ncp BKCOM.ncp%TYPE;
v_age BKCOM.age%TYPE;
v_inti BKCOM.inti%TYPE;
v_cha  BKCOM.cha%TYPE;
v_libcha  BKCHAP.lib%TYPE;
v_coddci CHAR(5);
v_langue CHAR(3);
v_Resultat CHAR(3500);
v_nbe INTEGER;


CURSOR cur_com IS
select a.ges,b.ncp,b.age,b.inti,b.cha,emb_getlibcha(b.cha) libcha 
		from bkcli a,bkcom b where a.cli=b.cli
		and b.suf=' ' and a.cli= P_CLI and cfe='N'
		and dev='952'
		and (cha like '251%' or cha like '253%');

BEGIN
CH3:='';
v_coddci:=' ';
v_langue:='F';
CH5:=']}]}]}';
v_statut:=0;
CH6:='{"RESULTSET":[{"Statut":"';
CH7:='","StatutMsg":"';
CH8:='","StatutData":';
CH9:='}]}';
 -------------------Vérifier l'existence du client---------------------------------------------------------
 
	select count(*) into v_nbe from bkcli a,bkcom b 
	where a.cli=b.cli
    and b.suf=' ' 
	and a.cli= P_CLI 
	and b.cfe='N';
	IF  (v_nbe=0) THEN
		v_Statut:=1;	
		v_StatutMsg:='CLIENT INEXISTANT OU COMPTE FERME';
		v_StatutData:='null';
		v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
	ELSE
	
    --------------Recupérer les infos signalétiques du client-----------------------------------------------
	    v_Statut:=0;
		select cli,sext,nomrest,(case when tcli='1' then nom else rso end),pre,to_char(dna,'DD/MM/YYYY'),decode(tcli,'1','PAR','2','ENT','3','ENT'),emb_gettelcli(cli),nid,vid,did,emb_getemailcli(cli,lpad(tcli,3,0)) 
		into v_cli,v_sext,v_nomrest,v_nom,v_pre,v_dna,v_agec,v_tel,v_nid,v_vid,v_did,v_email
		from bkcli
		where cli=P_CLI;
		
		
		CH1:='{"RESULTSET":[{"Statut":"'||trim(v_Statut)||'","StatutMsg":"","StatutData":
		[{"Client":"'||trim(v_cli)||'","Sexe":"'||trim(v_sext)||'","NomPrenom":"'||trim(v_nomrest)||'","Nom":"'||trim(v_nom)||'"
		,"Prenom":"'||trim(v_pre)||'","DateNais":"'||trim(v_dna)||'","Agec":"'||trim(v_agec)||'","Tel":"'||trim(v_tel)||'",
		"PieceId":"'||trim(v_nid)||'","DateExpir":"'||trim(v_vid)||'","DateDelivr":"'||trim(v_vid)||'",
		"Langue":"'||trim(v_langue)||'","Email":"'||trim(v_email)||'","Comptes":[';
		FOR rec_com IN cur_com
		LOOP
			CH2:=',{"Compte":"'||trim(rec_com.ncp)||'","Agence":"'||trim(rec_com.age)||'","Ncg":"'||trim(rec_com.cha)||'","Libncg":"'||trim(rec_com.libcha)||'","Coddci":"'||trim(v_coddci)||'","Expl":"'||trim(rec_com.ges)||'"}';
			CH3:= trim(CH3)||trim(CH2);
		END LOOP;
		v_Resultat:= trim(CH1)||trim(CH3)||trim(CH5);
		v_Resultat:= replace(v_Resultat,'"Comptes":[,','"Comptes":[');
	  
	END IF;
	
    return trim(v_Resultat);

END EMB_GETSIGNALETIC;
