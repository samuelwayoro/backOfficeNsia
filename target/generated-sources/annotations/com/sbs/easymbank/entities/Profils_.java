package com.sbs.easymbank.entities;

import com.sbs.easymbank.entities.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2022-07-26T21:56:08")
@StaticMetamodel(Profils.class)
public class Profils_ { 

    public static volatile ListAttribute<Profils, Users> usersList;
    public static volatile SingularAttribute<Profils, Boolean> connexion_ttes_age;
    public static volatile SingularAttribute<Profils, String> libelle;
    public static volatile SingularAttribute<Profils, Boolean> creationUtilisateur;
    public static volatile SingularAttribute<Profils, Boolean> souscription;
    public static volatile SingularAttribute<Profils, Boolean> suppressionProfils;
    public static volatile SingularAttribute<Profils, Boolean> connexion;
    public static volatile SingularAttribute<Profils, Boolean> creationProfils;
    public static volatile SingularAttribute<Profils, Boolean> modificationProfilsUtilisateur;
    public static volatile SingularAttribute<Profils, Boolean> reinitialisationUtilisateur;
    public static volatile SingularAttribute<Profils, Boolean> creationProfilsClients;
    public static volatile SingularAttribute<Profils, Boolean> suppressionUtilisateur;
    public static volatile SingularAttribute<Profils, Boolean> suppressionAgence;
    public static volatile SingularAttribute<Profils, Boolean> transac_ttes_age;
    public static volatile SingularAttribute<Profils, Boolean> audits;
    public static volatile SingularAttribute<Profils, Boolean> creationCommissions;
    public static volatile SingularAttribute<Profils, Boolean> reporting;
    public static volatile SingularAttribute<Profils, Boolean> suppressionCommissions;
    public static volatile SingularAttribute<Profils, Boolean> supervalidation;
    public static volatile SingularAttribute<Profils, Boolean> listCommissions;
    public static volatile SingularAttribute<Profils, Integer> idprofils;
    public static volatile SingularAttribute<Profils, Boolean> resiliation;
    public static volatile SingularAttribute<Profils, Boolean> transactions;
    public static volatile SingularAttribute<Profils, Boolean> audit_ttes_age;
    public static volatile SingularAttribute<Profils, Boolean> parametrage;
    public static volatile SingularAttribute<Profils, Boolean> modificationProfils;
    public static volatile SingularAttribute<Profils, Boolean> suppressionProfilsClients;
    public static volatile SingularAttribute<Profils, Boolean> listAbonnements;
    public static volatile SingularAttribute<Profils, Boolean> abonn_ttes_age;
    public static volatile SingularAttribute<Profils, Boolean> creationAgence;
    public static volatile SingularAttribute<Profils, Boolean> securite;
    public static volatile SingularAttribute<Profils, Boolean> valide_ttes_age;

}