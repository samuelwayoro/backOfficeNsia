CREATE OR REPLACE FUNCTION EMB_GETBALANCE(P_NCP CHAR)
RETURN CHAR
IS
v_Statut  CHAR(1);
v_StatutMsg CHAR(100);
v_StatutData CHAR(100);
v_age CHAR(5);
v_ncp CHAR(11);
v_sde DECIMAL(19,4);
v_nbe INTEGER;
v_nb INTEGER;
CH1 CHAR(30);
CH2 CHAR(20);
CH3 CHAR(30);
CH4 CHAR(15);
CH5 CHAR(6);
CH6 CHAR(15);
CH7 CHAR(3);
v_Resultat CHAR(500);
BEGIN
CH1:='{"RESULTSET":[{"Statut":"';
CH2:='","StatutMsg":"';
CH3:='","StatutData":[{"Compte":"';
CH4:='","Montant":"';
CH5:='"}]}]}';
CH6:='","StatutData":';
CH7:='}]}';
v_statut:=0;

 -------------------Vérifier l'existence du compte---------------------------------------------------------
 
	select count(*) into v_nbe from bkcom where age=substr(P_NCP,1,5) and ncp= substr(P_NCP,6,11) and dev='952';
	IF  (v_nbe=0) THEN
		v_Statut:=1;	
		v_StatutMsg:='COMPTE INEXISTANT';
		v_StatutData:='null';
		v_Resultat:= trim(CH1)||trim(v_statut)||trim(CH2)||trim(v_statutMsg)||trim(CH6)||trim(v_StatutData)||trim(CH7);
	ELSE
	    --------------Verifier si le compte n'est pas fermé-----------------------------------------------
		select count(*) into v_nb from bkcom where age=substr(P_NCP,1,5) and ncp= substr(P_NCP,6,11) and dev='952' and cfe='O';
	    IF (v_nb=1)  THEN
			v_Statut:=1;	
			v_StatutMsg:='COMPTE FERME';
			v_StatutData:='null';
			v_Resultat:= trim(CH1)||trim(v_statut)||trim(CH2)||trim(v_statutMsg)||trim(CH6)||trim(v_StatutData)||trim(CH7);
		ELSE
			v_Statut:=0;
			v_StatutMsg:='';
			select ncp,sin - (minds+mind+minj+minjs) into v_ncp,v_sde from bkcom where age=substr(P_NCP,1,5) and ncp= substr(P_NCP,6,11) and dev='952';
		    v_Resultat:=trim(CH1)||trim(v_statut)||trim(CH2)||trim(v_statutMsg)||trim(CH3)||trim(v_ncp)||trim(CH4)||trim(v_sde)||trim(CH5);
		END IF;
	END IF;
	
    return v_Resultat;

END EMB_GETBALANCE;
