CREATE OR REPLACE FUNCTION EMB_GETSTATUS(P_NOOPER CHAR)
RETURN CHAR
IS
v_Statut  CHAR(1);
v_StatutMsg CHAR(50);
v_StatutData CHAR(50);
CH5 CHAR(7);
CH6 CHAR(30);
CH7 CHAR(20);
CH8 CHAR(15);
CH9 CHAR(3);
CH4 CHAR(30);
v_langue CHAR(3);
v_Resultat CHAR(500);
v_nbe INTEGER;
w_cpt INTEGER;
v_refdos BKEVE.ndos%TYPE;
BEGIN
CH5:='"}]}]}';
v_statut:=0;
CH4:='","StatutData":[{"Nooper":"';
CH6:='{"RESULTSET":[{"Statut":"';
CH7:='","StatutMsg":"';
CH8:='","StatutData":';
CH9:='}]}';
        select count(*) into v_nbe from bkeve where trim(lib3)= trim(P_NOOPER) and eta='VA';
		select count(*) into w_cpt from bkheve where trim(lib3)= trim(P_NOOPER) and eta='VA';
		IF (v_nbe!=0) THEN
	        select ndos into v_refdos from bkeve where trim(lib3)=trim(P_NOOPER);
	        v_Statut:=0;	
	        v_StatutMsg:='TRAITE AVEC SUCCES';
	        v_StatutData:=v_refdos;
	        v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH4)||trim(v_StatutData)||trim(CH5);
												
		ELSIF (w_cpt!=0) THEN
	        select ndos into v_refdos from bkheve where trim(lib3)=trim(P_NOOPER);
	        v_Statut:=0;	
	        v_StatutMsg:='TRAITE AVEC SUCCES';
	        v_StatutData:=v_refdos;
	        v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH4)||trim(v_StatutData)||trim(CH5);
		ELSE	
			
			v_Statut:=1;	
			v_StatutMsg:='CODE TRANSACTION INCORRECT';
			v_StatutData:='null';
			v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
	
	   END IF;
   
    return trim(v_Resultat);

END EMB_GETSTATUS;
/
