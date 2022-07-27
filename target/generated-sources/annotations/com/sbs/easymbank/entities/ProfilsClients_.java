package com.sbs.easymbank.entities;

import com.sbs.easymbank.entities.Abonnements;
import com.sbs.easymbank.entities.Commissions;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2022-07-26T21:56:08")
@StaticMetamodel(ProfilsClients.class)
public class ProfilsClients_ { 

    public static volatile ListAttribute<ProfilsClients, Commissions> commissionsList;
    public static volatile SingularAttribute<ProfilsClients, Short> idProfils;
    public static volatile SingularAttribute<ProfilsClients, String> designationProfils;
    public static volatile SingularAttribute<ProfilsClients, String> codeProfils;
    public static volatile SingularAttribute<ProfilsClients, String> dateCreationProfils;
    public static volatile ListAttribute<ProfilsClients, Abonnements> abonnementsList;

}