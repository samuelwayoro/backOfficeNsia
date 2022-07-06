DECLARE FUNCTION EMB_POSTPAIEMENT(P_CODFACTURIER CHAR, P_DATTRANSACT CHAR, P_CODETRANSACT CHAR, P_REFTRANSACT CHAR, P_COMPTE CHAR,
P_MONTTRANSACT DECIMAL, P_MONTCOM DECIMAL,P_LIBELTRANSACT CHAR,P_LISTREFREL CHAR)
RETURN CLOB
IS
PRAGMA AUTONOMOUS_TRANSACTION;
v_Statut  CHAR(1);
v_StatutMsg CHAR(50);
v_StatutData CHAR(50);
CH1 CHAR(150);
CH2 CHAR(150);
CH3 CHAR(300);
CH5 CHAR(7);
CH6 CHAR(30);
CH7 CHAR(20);
CH8 CHAR(15);
CH9 CHAR(3);
CH4 CHAR(30);
v_langue CHAR(3);
v_Resultat CLOB;
v_nbe INTEGER;
v_nb INTEGER;
v_nbre INTEGER;
v_nbr INTEGER;
v_cpt INTEGER;
v_cpteur INTEGER;
w_cpt INTEGER;
w_cpteur INTEGER;
w_compteur INTEGER;
v_val INTEGER;
V_DATTRANSACT CHAR(10);
V_TAUTAX INTEGER;
V_W2B  CHAR(3);
V_B2W  CHAR(3);
V_CAN  CHAR(3);
V_CHAP1 BKCHAP.CHA%TYPE;
V_SIN1  BKCOM.SIN%TYPE;
V_MIND1 BKCOM.MIND%TYPE;
V_MINDS1 BKCOM.MINDS%TYPE;
V_MINJ1  BKCOM.MINJ%TYPE;
V_MINJS1 BKCOM.MINJS%TYPE;
V_CHAP2 BKCHAP.CHA%TYPE;
V_SIN2  BKCOM.SIN%TYPE;
V_MIND2 BKCOM.MIND%TYPE;
V_MINDS2 BKCOM.MINDS%TYPE;
V_MINJ2  BKCOM.MINJ%TYPE;
V_MINJS2 BKCOM.MINJS%TYPE;
V_MONTFRAIS BKCOM.SIN%TYPE;
V_MONTTRANS BKCOM.SIN%TYPE;
V_OPERA BKOPE.OPE%TYPE;
V_OPERAT BKOPE.OPE%TYPE;
V_LIB   BKOPE.LIB%TYPE;
V_NUM   BKOPE.NUM%TYPE;
V_LIBE  BKOPE.LIB%TYPE;
V_NUME   BKOPE.NUM%TYPE;
V_EVEN  CHAR(6);
V_PARTBANQUE BKCOM.SIN%TYPE;
V_PARTMARC BKCOM.SIN%TYPE;
V_MONTANTTAXE BKCOM.SIN%TYPE;
V_SDISPO BKCOM.SIN%TYPE;
W_AGE1       BKEVE.AGE1%TYPE;
W_DEV1       BKEVE.DEV1%TYPE;
W_NCP1       BKEVE.NCP1%TYPE;
W_AGE2       BKEVE.AGE2%TYPE;
W_DEV2       BKEVE.DEV2%TYPE;
W_NCP2       BKEVE.NCP2%TYPE;
W_MON1       BKEVE.MON1%TYPE;
W_MON2       BKEVE.MON2%TYPE;
v_refer      BKEVE.LIB3%TYPE;
v_refe       BKEVE.LIB3%TYPE;
v_etape      BKEVE.eta%TYPE;

---------------------------Initialiser les variables de bkeve--------------------------------------------------
V_AGSA       BKEVE.AGSA%TYPE;
V_AGE        BKEVE.AGE%TYPE;
V_OPE        BKEVE.OPE%TYPE;
V_EVE        BKEVE.EVE%TYPE;
V_TYP        BKEVE.TYP%TYPE;
V_NDOS       BKEVE.NDOS%TYPE;
V_AGE1       BKEVE.AGE1%TYPE;
V_DEV1       BKEVE.DEV1%TYPE;
V_NCP1       BKEVE.NCP1%TYPE;
V_SUF1       BKEVE.SUF1%TYPE;
V_CLC1       BKEVE.CLC1%TYPE;
V_CLI1       BKEVE.CLI1%TYPE;
V_NOM1       BKEVE.NOM1%TYPE;
V_GES1       BKEVE.GES1%TYPE;
V_SEN1       BKEVE.SEN1%TYPE;
V_MHT1       BKEVE.MHT1%TYPE;
V_MON1       BKEVE.MON1%TYPE;
V_DVA1       BKEVE.DVA1%TYPE;
V_EXO1       BKEVE.EXO1%TYPE;
V_SOL1       BKEVE.SOL1%TYPE;
V_INDH1      BKEVE.INDH1%TYPE;
V_INDS1      BKEVE.INDS1%TYPE;
V_DESA1      BKEVE.DESA1%TYPE;
V_DESA2      BKEVE.DESA2%TYPE;
V_DESA3      BKEVE.DESA3%TYPE;
V_DESA4      BKEVE.DESA4%TYPE;
V_DESA5      BKEVE.DESA5%TYPE;
V_AGE2       BKEVE.AGE2%TYPE;
V_DEV2       BKEVE.DEV2%TYPE;
V_NCP2       BKEVE.NCP2%TYPE;
V_SUF2       BKEVE.SUF2%TYPE;
V_CLC2       BKEVE.CLC2%TYPE;
V_CLI2       BKEVE.CLI2%TYPE;
V_NOM2       BKEVE.NOM2%TYPE;
V_GES2       BKEVE.GES2%TYPE;
V_SEN2       BKEVE.SEN2%TYPE;
V_MHT2       BKEVE.MHT2%TYPE;
V_MON2       BKEVE.MON2%TYPE;
V_DVA2       BKEVE.DVA2%TYPE;
V_DIN        BKEVE.DIN%TYPE;
V_EXO2       BKEVE.EXO2%TYPE;
V_SOL2       BKEVE.SOL2%TYPE;
V_INDH2      BKEVE.INDH2%TYPE;
V_INDS2      BKEVE.INDS2%TYPE;
V_DESC1      BKEVE.DESC1%TYPE;
V_DESC2      BKEVE.DESC2%TYPE;
V_DESC3      BKEVE.DESC3%TYPE;
V_DESC4      BKEVE.DESC4%TYPE;
V_DESC5      BKEVE.DESC5%TYPE;
V_ETAB       BKEVE.ETAB%TYPE;
V_GUIB       BKEVE.GUIB%TYPE;
V_NOME       BKEVE.NOME%TYPE;
V_DOMI       BKEVE.DOMI%TYPE;
V_ADB1       BKEVE.ADB1%TYPE;
V_ADB2       BKEVE.ADB2%TYPE;
V_ADB3       BKEVE.ADB3%TYPE;
V_VILB       BKEVE.VILB%TYPE;
V_CPOB       BKEVE.CPOB%TYPE;
V_CPAY       BKEVE.CPAY%TYPE;
V_ETABR      BKEVE.ETABR%TYPE;
V_GUIBR      BKEVE.GUIBR%TYPE;
V_COMB       BKEVE.COMB%TYPE;
V_CLEB       BKEVE.CLEB%TYPE;
V_NOMB       BKEVE.NOMB%TYPE;
V_MBAN       BKEVE.MBAN%TYPE;
V_DVAB       BKEVE.DVAB%TYPE;
V_CAI1       BKEVE.CAI1%TYPE;
V_TYC1       BKEVE.TYC1%TYPE;
V_DCAI1      BKEVE.DCAI1%TYPE;
V_SCAI1      BKEVE.SCAI1%TYPE;
V_MCAI1      BKEVE.MCAI1%TYPE;
V_ARRC1      BKEVE.ARRC1%TYPE;
V_CAI2       BKEVE.CAI2%TYPE;
V_TYC2       BKEVE.TYC2%TYPE;
V_DCAI2      BKEVE.DCAI2%TYPE;
V_SCAI2      BKEVE.SCAI2%TYPE;
V_MCAI2      BKEVE.MCAI2%TYPE;
V_ARRC2      BKEVE.ARRC2%TYPE;
V_DEV        BKEVE.DEV%TYPE;
V_MHT        BKEVE.MHT%TYPE;
V_MNAT       BKEVE.MNAT%TYPE;
V_MBOR       BKEVE.MBOR%TYPE;
V_NBOR       BKEVE.NBOR%TYPE;
V_NBLIG      BKEVE.NBLIG%TYPE;
V_TCAI2      BKEVE.TCAI2%TYPE;
V_TCAI3      BKEVE.TCAI3%TYPE;
V_NAT        BKEVE.NAT%TYPE;
V_NATO       BKEVE.NATO%TYPE;
V_OPEO       BKEVE.OPEO%TYPE;
V_EVEO       BKEVE.EVEO%TYPE;
V_PIEO       BKEVE.PIEO%TYPE;
V_DOU        BKEVE.DOU%TYPE;
V_DCO        BKEVE.DCO%TYPE;
V_ETA        BKEVE.ETA%TYPE;
V_ETAP       BKEVE.ETAP%TYPE;
V_NBV        BKEVE.NBV%TYPE;
V_NVAL       BKEVE.NVAL%TYPE;
V_UTI        BKEVE.UTI%TYPE;
V_UTF        BKEVE.UTF%TYPE;
V_UTA        BKEVE.UTA%TYPE;
V_MOA        BKEVE.MOA%TYPE;
V_MOF        BKEVE.MOF%TYPE;
V_LIB1       BKEVE.LIB1%TYPE;
V_LIB2       BKEVE.LIB2%TYPE;
V_LIB3       BKEVE.LIB3%TYPE;
V_LIB4       BKEVE.LIB4%TYPE;
V_LIB5       BKEVE.LIB5%TYPE;
V_LIB6       BKEVE.LIB6%TYPE;
V_LIB7       BKEVE.LIB7%TYPE;
V_LIB8       BKEVE.LIB8%TYPE;
V_LIB9       BKEVE.LIB9%TYPE;
V_LIB10      BKEVE.LIB10%TYPE;
V_AGEC       BKEVE.AGEC%TYPE;
V_AGEP       BKEVE.AGEP%TYPE;
V_INTR       BKEVE.INTR%TYPE;
V_ORIG       BKEVE.ORIG%TYPE;
V_RLET       BKEVE.RLET%TYPE;
V_CATR       BKEVE.CATR%TYPE;
V_CEB        BKEVE.CEB%TYPE;
V_PLB        BKEVE.PLB%TYPE;
V_CCO        BKEVE.CCO%TYPE;
V_DRET       BKEVE.DRET%TYPE;
V_NATP       BKEVE.NATP%TYPE;
V_NUMP       BKEVE.NUMP%TYPE;
V_DATP       BKEVE.DATP%TYPE;
V_NOMP       BKEVE.NOMP%TYPE;
V_AD1P       BKEVE.AD1P%TYPE;
V_AD2P       BKEVE.AD2P%TYPE;
V_DELP       BKEVE.DELP%TYPE;
V_SERIE      BKEVE.SERIE%TYPE;
V_NCHE       BKEVE.NCHE%TYPE;
V_CHQL       BKEVE.CHQL%TYPE;
V_CHQC       BKEVE.CHQC%TYPE;
V_CAB        BKEVE.CAB%TYPE;
V_NCFF       BKEVE.NCFF%TYPE;
V_CSA        BKEVE.CSA%TYPE;
V_CFRA       BKEVE.CFRA%TYPE;
V_NEFF       BKEVE.NEFF%TYPE;
V_TEFF       BKEVE.TEFF%TYPE;
V_DECH       BKEVE.DECH%TYPE;
V_TIRE       BKEVE.TIRE%TYPE;
V_AGTI       BKEVE.AGTI%TYPE;
V_AGRE       BKEVE.AGRE%TYPE;
V_NBJI       BKEVE.NBJI%TYPE;
V_PTFC       BKEVE.PTFC%TYPE;
V_EFAV       BKEVE.EFAV%TYPE;
V_NAVL       BKEVE.NAVL%TYPE;
V_EDOM       BKEVE.EDOM%TYPE;
V_EOPP       BKEVE.EOPP%TYPE;
V_EFAC       BKEVE.EFAC%TYPE;
V_MOTI       BKEVE.MOTI%TYPE;
V_ENVACC     BKEVE.ENVACC%TYPE;
V_ENOM       BKEVE.ENOM%TYPE;
V_VICL       BKEVE.VICL%TYPE;
V_TECO       BKEVE.TECO%TYPE;
V_TENV       BKEVE.TENV%TYPE;
V_EXJO       BKEVE.EXJO%TYPE;
V_ORG        BKEVE.ORG%TYPE;
V_CAI3       BKEVE.CAI3%TYPE;
V_MCAI3      BKEVE.MCAI3%TYPE;
V_FORC       BKEVE.FORC%TYPE;
V_OCAI3      BKEVE.OCAI3%TYPE;
V_NCAI3      BKEVE.NCAI3%TYPE;
V_CSP1       BKEVE.CSP1%TYPE;
V_CSP2       BKEVE.CSP2%TYPE;
V_CSP3       BKEVE.CSP3%TYPE;
V_CSP4       BKEVE.CSP4%TYPE;
V_CSP5       BKEVE.CSP5%TYPE;
V_NDOM       BKEVE.NDOM%TYPE;
V_CMOD       BKEVE.CMOD%TYPE;
V_DEVF       BKEVE.DEVF%TYPE;
V_NCPF       BKEVE.NCPF%TYPE;
V_SUFF       BKEVE.SUFF%TYPE;
V_MONF       BKEVE.MONF%TYPE;
V_DVAF       BKEVE.DVAF%TYPE;
V_EXOF       BKEVE.EXOF%TYPE;
V_AGEE       BKEVE.AGEE%TYPE;
V_DEVE       BKEVE.DEVE%TYPE;
V_NCPE       BKEVE.NCPE%TYPE;
V_SUFE       BKEVE.SUFE%TYPE;
V_CLCE       BKEVE.CLCE%TYPE;
V_NCPI       BKEVE.NCPI%TYPE;
V_SUFI       BKEVE.SUFI%TYPE;
V_MIMP       BKEVE.MIMP%TYPE;
V_DVAI       BKEVE.DVAI%TYPE;
V_NCPP       BKEVE.NCPP%TYPE;
V_SUFP       BKEVE.SUFP%TYPE;
V_PRGA       BKEVE.PRGA%TYPE;
V_MRGA       BKEVE.MRGA%TYPE;
V_TERM       BKEVE.TERM%TYPE;
V_TVAR       BKEVE.TVAR%TYPE;
V_INTP       BKEVE.INTP%TYPE;
V_CAP        BKEVE.CAP%TYPE;
V_PRLL       BKEVE.PRLL%TYPE;
V_ANO        BKEVE.ANO%TYPE;
V_ETAB1      BKEVE.ETAB1%TYPE;
V_GUIB1      BKEVE.GUIB1%TYPE;
V_COM1B      BKEVE.COM1B%TYPE;
V_ETAB2      BKEVE.ETAB2%TYPE;
V_GUIB2      BKEVE.GUIB2%TYPE;
V_COM2B      BKEVE.COM2B%TYPE;
V_TCOM1      BKEVE.TCOM1%TYPE;
V_MCOM1      BKEVE.MCOM1%TYPE;
V_TCOM2      BKEVE.TCOM2%TYPE;
V_MCOM2      BKEVE.MCOM2%TYPE;
V_TCOM3      BKEVE.TCOM3%TYPE;
V_MCOM3      BKEVE.MCOM3%TYPE;
V_FRAI1      BKEVE.FRAI1%TYPE;
V_FRAI2      BKEVE.FRAI2%TYPE;
V_FRAI3      BKEVE.FRAI3%TYPE;
V_TTAX1      BKEVE.TTAX1%TYPE;
V_MTAX1      BKEVE.MTAX1%TYPE;
V_TTAX2      BKEVE.TTAX2%TYPE;
V_MTAX2      BKEVE.MTAX2%TYPE;
V_TTAX3      BKEVE.TTAX3%TYPE;
V_MTAX3      BKEVE.MTAX3%TYPE;
V_MNT1       BKEVE.MNT1%TYPE;
V_MNT2       BKEVE.MNT2%TYPE;
V_MNT3       BKEVE.MNT3%TYPE;
V_MNT4       BKEVE.MNT4%TYPE;
V_MNT5       BKEVE.MNT5%TYPE;
V_TYC3       BKEVE.TYC3%TYPE;
V_DCAI3      BKEVE.DCAI3%TYPE;
V_SCAI3      BKEVE.SCAI3%TYPE;
V_ARRC3      BKEVE.ARRC3%TYPE;
V_MHTD       BKEVE.MHTD%TYPE;
V_TCAI4      BKEVE.TCAI4%TYPE;
V_TOPE       BKEVE.TOPE%TYPE;
V_IMG        BKEVE.IMG%TYPE;
V_DSAI       CHAR(10);
V_HSAI       BKEVE.HSAI%TYPE;
V_PAYSP      BKEVE.PAYSP%TYPE;
V_PDELP      BKEVE.PDELP%TYPE;
V_MANDA      BKEVE.MANDA%TYPE;
V_REFDOS     BKEVE.REFDOS%TYPE;
V_TCHFR      BKEVE.TCHFR%TYPE;
V_NIDNP      BKEVE.NIDNP%TYPE;
V_FRAISDIFF1 BKEVE.FRAISDIFF1%TYPE;
V_FRAISDIFF2 BKEVE.FRAISDIFF2%TYPE;

----------------------------Initialiser les variables de bknom--------------------------------------------------
v_codefact BKNOM.cacc%TYPE;
v_nomfact  BKNOM.lib1%TYPE;
v_compte   BKNOM.lib2%TYPE;
v_agence   BKNOM.lib3%TYPE;
v_codtr    BKNOM.lib5%TYPE;
v_oper     CHAR(9);
v_partb    BKNOM.mnt2%TYPE;
v_partm    BKNOM.mnt3%TYPE;
v_taux     BKNOM.mnt4%TYPE;
v_tb       BKNOM.mnt5%TYPE;
v_yy       CHAR(4);
v_mm       CHAR(2);
v_dd       CHAR(2);
BEGIN
CH3:='';
CH5:='"}]}]}';
v_statut:=0;
CH4:='","StatutData":[{"Nooper":"';
CH6:='{"RESULTSET":[{"Statut":"';
CH7:='","StatutMsg":"';
CH8:='","StatutData":';
CH9:='}]}';


v_yy:=substr(trim(P_DATTRANSACT),1,4);
v_mm:=substr(trim(P_DATTRANSACT),6,2);
v_dd:=substr(trim(P_DATTRANSACT),9,2);
V_DATTRANSACT:= v_dd||'/'||v_mm||'/'||v_yy;



-----------------------------Vider les informations contenues dans les tables temporaires---------------------------------------
DELETE FROM TEMP_BKEVE;
commit;
DELETE FROM TEMP_BKEVEC;
commit;
DELETE FROM TEMP_MVT;
commit;

 -------------------récupération en nomenclature des infos du facturier---------------------------------------------------------
    select count(*) into v_cpteur from bknom 
	where ctab= '067' and cacc= P_CODFACTURIER;
	
	IF (v_cpteur=0)	THEN
		v_Statut:=1;	
		v_StatutMsg:='CODE FACTURIER INEXISTANT-NOMENCLATURE INEXISTANTE';
		v_StatutData:='null';
		v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
	ELSE
		select trim(cacc),trim(lib1),trim(lib2),trim(lib3),to_char(trim(mnt5)),trim(lib5),trim(mnt1),trim(mnt2),trim(mnt3),trim(mnt4) into v_codefact,v_nomfact,v_compte,v_agence,v_tb,v_codtr,v_oper,v_partb,v_partm,v_taux
		from bknom
		where ctab= '067' and cacc= P_CODFACTURIER;
	END IF;
-------------------récupération des informations du code opération---------------------------------------------------------------
----------------Code ope Bank to wallet-------------------------------------------
    select ope,lib,num into V_OPERA,V_LIB,V_NUM from bkope where ope= to_char(substr(v_oper,1,3)) and age in (select mnt1 from bknom where ctab='098' and cacc='SITE-CENT');
	
	IF (V_NUM='999999') THEN
	    V_NUM:=0;
		update bkope set num=0 where ope=to_char(V_OPERA);
		commit;
	END IF;
	
----------------Code ope Wallet to Bank-------------------------------------------
    select ope,lib,num into V_OPERAT,V_LIBE,V_NUME from bkope where ope= to_char(substr(v_oper,4,3)) and age in (select mnt1 from bknom where ctab='098' and cacc='SITE-CENT');
	
	IF (V_NUME='999999') THEN
	    V_NUME:=0;
		update bkope set num=0 where ope=to_char(V_OPERAT);
		commit;
	END IF;
	
--------------------Récupérer la date du jour et Vérifier si la date de transaction est égale à la date du jour------------------------------------------------------------------------------------
    select to_char(to_date(sysdate,'dd/mm/yyyy')) into V_DSAI from dual;	
	IF (V_DATTRANSACT != V_DSAI) THEN
		v_Statut:=1;	
		v_StatutMsg:='DATE DE TRANSACTION INCORRECTE';
		v_StatutData:='null';
		v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
	
	END IF;
----------------------Vérifier la date comptable du système et la récupérer dans une variable-----------------------------------------
    select count(*) into v_nbe from bknom where ctab='001' AND cacc in (select mnt1 from bknom where ctab='098' and cacc='SITE-CENT') and lib2='FE';	
	IF (v_nbe=1) THEN
	
		v_Statut:=1;	
		v_StatutMsg:='TRANSACTION IMPOSSIBLE-BANQUE FERMEE';
		v_StatutData:='null';
		v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
	ELSE
	    SELECT to_date(LPAD(mnt1,8,0),'dd/mm/yyyy') into V_DCO FROM bknom WHERE ctab='001' AND cacc in (select mnt1 from bknom where ctab='098' and cacc='SITE-CENT');
	END IF;
	
-------------------------récupérer les types de transaations-----------------------------------------------

	V_W2B:= substr(v_codtr,1,3);
	V_B2W:= substr(v_codtr,4,3);
	V_CAN:= substr(v_codtr,7,3);


-------------------------Rechargement WALLET TO BANK-------------------------------------------------------------------------------
	
	IF (V_W2B=substr(trim(P_CODETRANSACT),1,3) )  THEN
	    select count(*) into v_refer from bkeve where substr(ndos,1,3)= substr(P_CODETRANSACT,1,3) and trim(lib3)=trim(P_REFTRANSACT);
		IF  (v_refer=0) THEN
	
				select count(*) into v_nb from bkcom where age=substr(P_COMPTE,1,5) and ncp= substr(P_COMPTE,6,11) and dev='952' and cfe='N';
				IF  (v_nb=0) THEN
					v_Statut:=1;	
					v_StatutMsg:='COMPTE A CREDITER INEXISTANT-FERME';
					v_StatutData:='null';
					v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
					
				ELSE
					
					select count(*) into v_nb from bkcom where age= v_agence and ncp= v_compte and dev='952' and cfe='N';
					IF (v_nb=0) THEN
						v_Statut:=1;	
						v_StatutMsg:='COMPTE A DEBITER INEXISTANT-FERME';
						v_StatutData:='null';
						v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
					ELSE
		-------------------------Récupération des informations du facturier--------------------------------------------------------------------------------------------
						select age,ncp,cli,emb_getnomcli(cli) nom,emb_getgescli(cli),clc,suf,dev,cha,sin,mind,minds,minj,minjs 
						into V_AGE1,V_NCP1,V_CLI1,V_NOM1,V_GES1,V_CLC1,V_SUF1,V_DEV1,V_CHAP1,V_SIN1,V_MIND1,V_MINDS1,V_MINJ1,V_MINJS1
						from bkcom where age=v_agence and ncp= v_compte and dev='952';
						 
						select age,ncp,cli,emb_getnomcli(cli) nom,emb_getgescli(cli),clc,suf,dev,cha,sin,mind,minds,minj,minjs 
						into V_AGE2,V_NCP2,V_CLI2,V_NOM2,V_GES2,V_CLC2,V_SUF2,V_DEV2,V_CHAP2,V_SIN2,V_MIND2,V_MINDS2,V_MINJ2,V_MINJS2
						from bkcom where age=substr(P_COMPTE,1,5) and ncp= substr(P_COMPTE,6,11) and dev='952';
						
						V_SDISPO:= V_SIN2 - (V_MIND2 + V_MINDS2 + V_MINJ2 + V_MINJS2);
						V_PARTBANQUE:= round(P_MONTCOM * (v_partb/100));
						V_PARTMARC:= round(P_MONTCOM * (v_partm/100));
						V_MONTANTTAXE:= round((V_PARTBANQUE+V_PARTMARC)  * (v_taux/100));
						V_MONTFRAIS:= round((V_PARTBANQUE+V_PARTMARC) + ((V_PARTBANQUE+V_PARTMARC)  * (v_taux/100)));
						V_MONTTRANS:= round(P_MONTTRANSACT);
						V_LIB3:=P_REFTRANSACT;
						V_EVEN:= TO_CHAR(LPAD(V_NUME+1,6,'0'));
						V_NDOS:=TRIM(V_W2B||'-'||V_AGE2||V_EVEN);
						
						
							IF  ( V_SDISPO < V_MONTFRAIS)  THEN
							
							v_Statut:=1;	
							v_StatutMsg:='SOLDE DU COMPTE INSUFFISANT';
							v_StatutData:='null';
							v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
							
							ELSE
							
								
								Insert into TEMP_BKEVE (AGSA,AGE,OPE,EVE,TYP,NDOS,AGE1,DEV1,NCP1,SUF1,CLC1,CLI1,NOM1,GES1,SEN1,MHT1,MON1,DVA1,EXO1,SOL1,INDH1,INDS1,DESA1,DESA2,DESA3,DESA4,DESA5,AGE2,DEV2,NCP2,SUF2,CLC2,CLI2,NOM2,GES2,SEN2,MHT2,MON2,DVA2,DIN,EXO2,SOL2,INDH2,INDS2,DESC1,DESC2,DESC3,DESC4,DESC5,ETAB,GUIB,NOME,DOMI,ADB1,ADB2,ADB3,VILB,CPOB,CPAY,ETABR,GUIBR,COMB,CLEB,NOMB,MBAN,DVAB,CAI1,TYC1,DCAI1,SCAI1,MCAI1,ARRC1,CAI2,TYC2,DCAI2,SCAI2,MCAI2,ARRC2,DEV,MHT,MNAT,MBOR,NBOR,NBLIG,TCAI2,TCAI3,NAT,NATO,OPEO,EVEO,PIEO,DOU,DCO,ETA,ETAP,NBV,NVAL,UTI,UTF,UTA,MOA,MOF,LIB1,LIB2,LIB3,LIB4,LIB5,LIB6,LIB7,LIB8,LIB9,LIB10,AGEC,AGEP,INTR,ORIG,RLET,CATR,CEB,PLB,CCO,DRET,NATP,NUMP,DATP,NOMP,AD1P,AD2P,DELP,SERIE,NCHE,CHQL,CHQC,CAB,NCFF,CSA,CFRA,NEFF,TEFF,DECH,TIRE,AGTI,AGRE,NBJI,PTFC,EFAV,NAVL,EDOM,EOPP,EFAC,MOTI,ENVACC,ENOM,VICL,TECO,TENV,EXJO,ORG,CAI3,MCAI3,FORC,OCAI3,NCAI3,CSP1,CSP2,CSP3,CSP4,CSP5,NDOM,CMOD,DEVF,NCPF,SUFF,MONF,DVAF,EXOF,AGEE,DEVE,NCPE,SUFE,CLCE,NCPI,SUFI,MIMP,DVAI,NCPP,SUFP,PRGA,MRGA,TERM,TVAR,INTP,CAP,PRLL,ANO,ETAB1,GUIB1,COM1B,ETAB2,GUIB2,COM2B,TCOM1,MCOM1,TCOM2,MCOM2,TCOM3,MCOM3,FRAI1,FRAI2,FRAI3,TTAX1,MTAX1,TTAX2,MTAX2,TTAX3,MTAX3,MNT1,MNT2,MNT3,MNT4,MNT5,TYC3,DCAI3,SCAI3,ARRC3,MHTD,TCAI4,TOPE,IMG,DSAI,HSAI,PAYSP,PDELP,MANDA,REFDOS,TCHFR,NIDNP,FRAISDIFF1,FRAISDIFF2) 
								values (V_AGE2,V_AGE2,to_char(V_OPERAT),V_EVEN,trim(v_tb),V_NDOS,V_AGE1,V_DEV1,V_NCP1,V_SUF1,V_CLC1,V_CLI1,V_NOM1,V_GES1,'D',V_MONTTRANS,V_MONTTRANS,decode(substr(V_CHAP1,1,3),'253',V_DCO-15,V_DCO-1),'N',V_SIN1,'0','0','    ','    ','    ','    ','    ',V_AGE2,V_DEV2,V_NCP2,V_SUF2,V_CLC2,V_CLI2,V_NOM2,V_GES2,'C',V_MONTTRANS,(V_MONTTRANS-V_MONTFRAIS),decode(substr(V_CHAP2,1,3),'253',V_DCO+15,V_DCO+1),V_DCO,'N',V_SIN2,'0','0',' ','    ','    ','    ','    ','          ','     ','                                        ','                              ','                              ','                              ','                              ','                              ','      ','   ','          ','     ','                         ','  ','                              ','0',null,'   ',' ','   ',' ','0','0','   ',' ','   ',' ','0','0','952',V_MONTTRANS,V_MONTTRANS,'0','      ','0','1','1','TRBANA','      ','   ','      ','           ',V_DCO,V_DCO,'VA','  ','0','0','AUTO','          ','          ','0','0',P_LIBELTRANSACT,P_CODETRANSACT,TRIM(V_LIB3),'                                        ','                                        ',P_LIBELTRANSACT,'                                                  ','                                                  ','                                                  ','                                                  ','     ','     ',' ',' ','        ',' ',' ',' ','0',null,'          ','                    ',null,'                                    ','                              ','                              ','                              ','  ','              ',' ',' ',' ','        ',' ','  ','        ',' ',null,'      ',' ',' ','0',' ',' ','      ',' ',' ',' ','    ',' ',' ',' ',' ',' ',' ','     ','   ','0',' ','  ','0','          ','          ','          ','          ','          ','                         ','          ','   ','           ','  ','0',null,' ','     ','   ','           ','  ','  ','           ','  ','0',null,'           ','  ','0','0',' ',' ',' ',' ',' ',' ','          ','     ','               ','          ','     ','               ','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0',' ','   ',' ','0','0','0','   ','N',V_DSAI,TO_CHAR(CAST (SYSTIMESTAMP AS TIMESTAMP ( 3 )),'hh24:mi:ss.ff'),'   ','                              ','N',' ','0','                    ','N','N');
								
								commit;
								
								Insert into TEMP_BKEVEC (AGE,OPE,EVE,NAT,IDEN,TYPC,DEVR,MCOMR,TXREF,MCOMC,MCOMN,MCOMT,TAX,TCOM) values (V_AGE2,to_char(V_OPERAT),to_char(V_EVEN),'COMBAN','COM01','C','952',V_PARTBANQUE,'1',V_PARTBANQUE,V_PARTBANQUE,V_PARTBANQUE,'O','0');
								Insert into TEMP_BKEVEC (AGE,OPE,EVE,NAT,IDEN,TYPC,DEVR,MCOMR,TXREF,MCOMC,MCOMN,MCOMT,TAX,TCOM) values (V_AGE2,to_char(V_OPERAT),to_char(V_EVEN),'FRABAN','FRA01','C','952',V_PARTMARC,'1',V_PARTMARC,V_PARTMARC,V_PARTMARC,'O','0');
								Insert into TEMP_BKEVEC (AGE,OPE,EVE,NAT,IDEN,TYPC,DEVR,MCOMR,TXREF,MCOMC,MCOMN,MCOMT,TAX,TCOM) values (V_AGE2,to_char(V_OPERAT),to_char(V_EVEN),'TAUTAX','TAX01','C','952',V_MONTANTTAXE,'1',V_MONTANTTAXE,V_MONTANTTAXE,V_MONTANTTAXE,'T',v_taux);
								commit;
								
								select count(*) into v_nbre from TEMP_BKEVE;
								select count(*) into v_nbr from TEMP_BKEVEC;
								
									IF (v_nbre!=0 and v_nbr!=0) THEN
									
										insert into bkeve select * from TEMP_BKEVE;
										commit;
										insert into bkevec select * from TEMP_BKEVEC;
										commit;
										
										update bkcom set sin= sin - V_MONTTRANS where age=v_agence and ncp= v_compte and dev='952';
										commit;
										
										update bkcom set sin= (sin + (V_MONTTRANS-V_MONTFRAIS)) where age=substr(P_COMPTE,1,5) and ncp= substr(P_COMPTE,6,11) and dev='952';
										commit;
										
										update bkope set num= V_NUME+1 where ope=V_OPERAT;
										commit;
										
										v_Statut:=0;	
										v_StatutMsg:='TRAITE AVEC SUCCES';
										v_StatutData:=V_NDOS;
										v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH4)||trim(v_StatutData)||trim(CH5);
												
									END IF;
								
							END IF;
						 
				    END IF;
		        END IF;
		ELSE
				v_Statut:=1;	
				v_StatutMsg:='TRANSACTION DEJA EFFECTUEE';
				v_StatutData:='null';
				v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
		END IF;
-------------------------Rechargement BANK TO WALLET-------------------------------------------------------------------------------
	
		
		ELSIF (V_B2W=substr(trim(P_CODETRANSACT),1,3))  THEN
		
			select count(*) into v_refe from bkeve where substr(ndos,1,3)= substr(P_CODETRANSACT,1,3) and trim(lib3)=trim(P_REFTRANSACT);
			IF  (v_refe= 0) THEN
		
					select count(*) into v_nb from bkcom where age=substr(P_COMPTE,1,5) and ncp= substr(P_COMPTE,6,11) and dev='952' and cfe='N';
					IF  (v_nb=0) THEN
						v_Statut:=1;	
						v_StatutMsg:='COMPTE A DEBITER INEXISTANT-FERME';
						v_StatutData:='null';
						v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
						
					ELSE
						
						select count(*) into v_nb from bkcom where age= v_agence and ncp= v_compte and dev='952' and cfe='N';
						IF (v_nb=0) THEN
							v_Statut:=1;	
							v_StatutMsg:='COMPTE A CREDITER INEXISTANT-FERME';
							v_StatutData:='null';
							v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
						ELSE
			-------------------------Récupération des informations du facturier--------------------------------------------------------------------------------------------
							select age,ncp,cli,emb_getnomcli(cli) nom,emb_getgescli(cli),clc,suf,dev,cha,sin,mind,minds,minj,minjs 
							into V_AGE1,V_NCP1,V_CLI1,V_NOM1,V_GES1,V_CLC1,V_SUF1,V_DEV1,V_CHAP1,V_SIN1,V_MIND1,V_MINDS1,V_MINJ1,V_MINJS1
							from bkcom where age=substr(P_COMPTE,1,5) and ncp= substr(P_COMPTE,6,11) and dev='952';
							 
							select age,ncp,cli,emb_getnomcli(cli) nom,emb_getgescli(cli),clc,suf,dev,cha,sin,mind,minds,minj,minjs 
							into V_AGE2,V_NCP2,V_CLI2,V_NOM2,V_GES2,V_CLC2,V_SUF2,V_DEV2,V_CHAP2,V_SIN2,V_MIND2,V_MINDS2,V_MINJ2,V_MINJS2
							from bkcom where age=v_agence and ncp= v_compte and dev='952';
							
							V_SDISPO:= V_SIN1 - (V_MIND1 + V_MINDS1 + V_MINJ1 + V_MINJS1);
							V_PARTBANQUE:= round(P_MONTCOM * (v_partb/100));
							V_PARTMARC:= round(P_MONTCOM * (v_partm/100));
							V_MONTANTTAXE:= round((V_PARTBANQUE+V_PARTMARC)  * (v_taux/100));
							V_MONTFRAIS:= round((V_PARTBANQUE+V_PARTMARC) + ((V_PARTBANQUE+V_PARTMARC)  * (v_taux/100)));
							V_MONTTRANS:= round(P_MONTTRANSACT);
							V_LIB3:= P_REFTRANSACT;
							V_EVEN:= TO_CHAR(LPAD(V_NUM+1,6,'0'));
							V_NDOS:=TRIM(V_B2W||'-'||V_AGE2||V_EVEN);
							
							
								IF  ( V_SDISPO < (V_MONTTRANS + V_MONTFRAIS ))  THEN
								
								v_Statut:=1;	
								v_StatutMsg:='SOLDE DU COMPTE INSUFFISANT';
								v_StatutData:='null';
								v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
								
								ELSE
								
									
									Insert into TEMP_BKEVE (AGSA,AGE,OPE,EVE,TYP,NDOS,AGE1,DEV1,NCP1,SUF1,CLC1,CLI1,NOM1,GES1,SEN1,MHT1,MON1,DVA1,EXO1,SOL1,INDH1,INDS1,DESA1,DESA2,DESA3,DESA4,DESA5,AGE2,DEV2,NCP2,SUF2,CLC2,CLI2,NOM2,GES2,SEN2,MHT2,MON2,DVA2,DIN,EXO2,SOL2,INDH2,INDS2,DESC1,DESC2,DESC3,DESC4,DESC5,ETAB,GUIB,NOME,DOMI,ADB1,ADB2,ADB3,VILB,CPOB,CPAY,ETABR,GUIBR,COMB,CLEB,NOMB,MBAN,DVAB,CAI1,TYC1,DCAI1,SCAI1,MCAI1,ARRC1,CAI2,TYC2,DCAI2,SCAI2,MCAI2,ARRC2,DEV,MHT,MNAT,MBOR,NBOR,NBLIG,TCAI2,TCAI3,NAT,NATO,OPEO,EVEO,PIEO,DOU,DCO,ETA,ETAP,NBV,NVAL,UTI,UTF,UTA,MOA,MOF,LIB1,LIB2,LIB3,LIB4,LIB5,LIB6,LIB7,LIB8,LIB9,LIB10,AGEC,AGEP,INTR,ORIG,RLET,CATR,CEB,PLB,CCO,DRET,NATP,NUMP,DATP,NOMP,AD1P,AD2P,DELP,SERIE,NCHE,CHQL,CHQC,CAB,NCFF,CSA,CFRA,NEFF,TEFF,DECH,TIRE,AGTI,AGRE,NBJI,PTFC,EFAV,NAVL,EDOM,EOPP,EFAC,MOTI,ENVACC,ENOM,VICL,TECO,TENV,EXJO,ORG,CAI3,MCAI3,FORC,OCAI3,NCAI3,CSP1,CSP2,CSP3,CSP4,CSP5,NDOM,CMOD,DEVF,NCPF,SUFF,MONF,DVAF,EXOF,AGEE,DEVE,NCPE,SUFE,CLCE,NCPI,SUFI,MIMP,DVAI,NCPP,SUFP,PRGA,MRGA,TERM,TVAR,INTP,CAP,PRLL,ANO,ETAB1,GUIB1,COM1B,ETAB2,GUIB2,COM2B,TCOM1,MCOM1,TCOM2,MCOM2,TCOM3,MCOM3,FRAI1,FRAI2,FRAI3,TTAX1,MTAX1,TTAX2,MTAX2,TTAX3,MTAX3,MNT1,MNT2,MNT3,MNT4,MNT5,TYC3,DCAI3,SCAI3,ARRC3,MHTD,TCAI4,TOPE,IMG,DSAI,HSAI,PAYSP,PDELP,MANDA,REFDOS,TCHFR,NIDNP,FRAISDIFF1,FRAISDIFF2) 
									values (V_AGE1,V_AGE1,to_char(v_opera),to_char(V_EVEN),trim(v_tb),V_NDOS,V_AGE1,V_DEV1,V_NCP1,V_SUF1,V_CLC1,V_CLI1,V_NOM1,V_GES1,'D',V_MONTTRANS,(V_MONTTRANS + V_MONTFRAIS),decode(substr(V_CHAP1,1,3),'253',V_DCO-15,V_DCO-1),'N',V_SIN1,'0','0','    ','    ','    ','    ','    ',V_AGE2,V_DEV2,V_NCP2,V_SUF2,V_CLC2,V_CLI2,V_NOM2,V_GES2,'C',V_MONTTRANS,V_MONTTRANS,decode(substr(V_CHAP2,1,3),'253',V_DCO+15,V_DCO+1),V_DCO,'N',V_SIN2,'0','0',' ','    ','    ','    ','    ','          ','     ','                                        ','                              ','                              ','                              ','                              ','                              ','      ','   ','          ','     ','                         ','  ','                              ','0',null,'   ',' ','   ',' ','0','0','   ',' ','   ',' ','0','0','952',V_MONTTRANS,V_MONTTRANS,'0','      ','0','1','1','TRBANA','      ','   ','      ','           ',V_DCO,V_DCO,'VA','  ','0','0','AUTO','          ','          ','0','0',P_LIBELTRANSACT,P_CODETRANSACT,TRIM(V_LIB3),'                                        ','                                        ',P_LIBELTRANSACT,'                                                  ','                                                  ','                                                  ','                                                  ','     ','     ',' ',' ','        ',' ',' ',' ','0',null,'          ','                    ',null,'                                    ','                              ','                              ','                              ','  ','              ',' ',' ',' ','        ',' ','  ','        ',' ',null,'      ',' ',' ','0',' ',' ','      ',' ',' ',' ','    ',' ',' ',' ',' ',' ',' ','     ','   ','0',' ','  ','0','          ','          ','          ','          ','          ','                         ','          ','   ','           ','  ','0',null,' ','     ','   ','           ','  ','  ','           ','  ','0',null,'           ','  ','0','0',' ',' ',' ',' ',' ',' ','          ','     ','               ','          ','     ','               ','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0',' ','   ',' ','0','0','0','   ','N',V_DSAI,TO_CHAR(CAST (SYSTIMESTAMP AS TIMESTAMP ( 3 )),'hh24:mi:ss.ff'),'   ','                              ','N',' ','0','                    ','N','N');
									
									commit;
									
									Insert into TEMP_BKEVEC (AGE,OPE,EVE,NAT,IDEN,TYPC,DEVR,MCOMR,TXREF,MCOMC,MCOMN,MCOMT,TAX,TCOM) values (V_AGE1,to_char(v_opera),to_char(V_EVEN),'COMBAN','COM01','D','952',V_PARTBANQUE,'1',V_PARTBANQUE,V_PARTBANQUE,V_PARTBANQUE,'O','0');
									Insert into TEMP_BKEVEC (AGE,OPE,EVE,NAT,IDEN,TYPC,DEVR,MCOMR,TXREF,MCOMC,MCOMN,MCOMT,TAX,TCOM) values (V_AGE1,to_char(v_opera),to_char(V_EVEN),'FRABAN','FRA01','D','952',V_PARTMARC,'1',V_PARTMARC,V_PARTMARC,V_PARTMARC,'O','0');
									Insert into TEMP_BKEVEC (AGE,OPE,EVE,NAT,IDEN,TYPC,DEVR,MCOMR,TXREF,MCOMC,MCOMN,MCOMT,TAX,TCOM) values (V_AGE1,to_char(v_opera),to_char(V_EVEN),'TAUTAX','TAX01','D','952',V_MONTANTTAXE,'1',V_MONTANTTAXE,V_MONTANTTAXE,V_MONTANTTAXE,'T',v_taux);
									commit;
									
									select count(*) into v_nbre from TEMP_BKEVE;
									select count(*) into v_nbr from TEMP_BKEVEC;
									
										IF (v_nbre!=0 and v_nbr!=0) THEN
										
											insert into bkeve select * from TEMP_BKEVE;
											commit;
											insert into bkevec select * from TEMP_BKEVEC;
											commit;
											
											update bkcom set sin= sin - (V_MONTTRANS+V_MONTFRAIS) where age=substr(P_COMPTE,1,5) and ncp= substr(P_COMPTE,6,11) and dev='952';
											commit;
											
											update bkcom set sin= sin + V_MONTTRANS where age=v_agence and ncp= v_compte and dev='952';
											commit;
											
											update bkope set num= V_NUM+1 where ope=V_OPERA;
											commit;
											
											v_Statut:=0;	
											v_StatutMsg:='TRAITE AVEC SUCCES';
											v_StatutData:=V_NDOS;
											v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH4)||trim(v_StatutData)||trim(CH5);
													
										END IF;
									
								END IF;
							 
						END IF;
				
			        END IF;
				ELSE
				v_Statut:=1;	
				v_StatutMsg:='TRANSACTION DEJA EFFECTUEE';
				v_StatutData:='null';
				v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
			
		    END IF;	
-------------------------ANNULATION JOUR-------------------------------------------------------------------------------
				
		ELSIF (V_CAN=substr(trim(P_CODETRANSACT),1,3) )  THEN
				select count(*) into w_cpt from bkheve where trim(ndos)= trim(P_LISTREFREL) and eta='VA';
				select count(*) into v_cpt from bkeve where trim(ndos)= trim(P_LISTREFREL);
					IF (v_cpt!=0) THEN			
						select eta into v_etap from bkeve where substr(ndos,1,3)= substr(P_LISTREFREL,1,3)  and trim(ndos)= trim(P_LISTREFREL);
						IF  (trim(v_etap)= 'VA') THEN	
								select age1,ncp1,dev1,mon1,age2,ncp2,dev2,mon2 into w_age1,w_ncp1,w_dev1,w_mon1,w_age2,w_ncp2,w_dev2,w_mon2 from bkeve where trim(ndos)= trim(P_LISTREFREL);
								update bkeve set eta='IG',etap='VA' where trim(ndos)= trim(P_LISTREFREL);
								commit;
								
								update bkcom set sin= sin + w_mon1 where age= w_age1 and ncp=w_ncp1 and dev=w_dev1;
								commit;
								
								update bkcom set sin= sin - w_mon2 where age= w_age2 and ncp=w_ncp2 and dev=w_dev2;
								commit;
							 
								v_Statut:=0;	
								v_StatutMsg:='ANNULATION TERMINEE AVEC SUCCES';
								v_StatutData:=P_LISTREFREL;
								v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH4)||trim(v_StatutData)||trim(CH5);
						ELSE
								v_Statut:=1;	
								v_StatutMsg:='ANNULATION DEJA EFFECTUEE';
								v_StatutData:='null';
								v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
						END IF;
					ELSIF (w_cpt!=0) THEN
					    insert into TEMP_MVT 
						select age,dev,emb_getchacpt(age,ncp,dev) cha,ncp,suf,substr(v_oper,7,3),' ',' ','AUTO',' ',emb_getclc(age,ncp,dev),dco,ser,
						dva,mon,decode(sen,'C','D','D','C') sen,'ANNULATION TRANSACTION '||refdos,exo,pie,rlet,des1,des2,des3,des4,des5,utf,uta,'1,00',
						din,' ','0',ncc,suc,' ',' ',' ',mar,dech,agsa, agem,'aged',devc, mctv,pieo,' ','',' ',' ', modu,refdos, 
						' ',label,nat, eta, schema, ceticpt, ' ', fusion
						from bkhis
						where 
						refdos=P_LISTREFREL;
						commit;
					    select count(*) into w_cpteur from TEMP_MVT;
					    IF (w_cpteur!=0) THEN
						   select count(*) into w_compteur from bkmvti where refdos= (select distinct refdos from TEMP_MVT);
						    IF (w_compteur=0) THEN
							   insert into bkmvti
							   select * from TEMP_MVT;
							   commit;
							ELSE
								v_Statut:=1;	
								v_StatutMsg:='ANNULATION DEJA EFFECTUEE';
								v_StatutData:='null';
								v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
							END IF;
						ELSE
						   v_Statut:=1;	
						   v_StatutMsg:='ANNULATION IMPOSSIBLE';
						   v_StatutData:='null';
						   v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);       
						END IF;
					ELSE	
						v_Statut:=1;	
						v_StatutMsg:='REFERENCE INCORRECTE';
						v_StatutData:='null';
						v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
						
					END IF;
			
	    ELSE
	
	    v_Statut:=1;	
		v_StatutMsg:='CODE TRANSACTION INCORRECT'||V_B2W;
		v_StatutData:='null';
		v_Resultat:= trim(CH6)||trim(v_statut)||trim(CH7)||trim(v_statutMsg)||trim(CH8)||trim(v_StatutData)||trim(CH9);
	
	
	END IF;
   
    return trim(v_Resultat);

END EMB_POSTPAIEMENT;
BEGIN
? := EMB_POSTPAIEMENT(?,?,?,?,?,?,?,?,?);
END;

