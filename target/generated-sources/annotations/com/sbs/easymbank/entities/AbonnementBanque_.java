package com.sbs.easymbank.entities;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2022-09-07T18:22:11")
@StaticMetamodel(AbonnementBanque.class)
public class AbonnementBanque_ { 

    public static volatile SingularAttribute<AbonnementBanque, BigInteger> racine;
    public static volatile SingularAttribute<AbonnementBanque, String> numero;
    public static volatile SingularAttribute<AbonnementBanque, String> dateNaissance;
    public static volatile SingularAttribute<AbonnementBanque, String> libelle;
    public static volatile SingularAttribute<AbonnementBanque, BigDecimal> id;
    public static volatile SingularAttribute<AbonnementBanque, BigInteger> soldeCourant;
    public static volatile SingularAttribute<AbonnementBanque, String> devise;
    public static volatile SingularAttribute<AbonnementBanque, String> nom;
    public static volatile SingularAttribute<AbonnementBanque, BigInteger> soldeDispo;
    public static volatile SingularAttribute<AbonnementBanque, String> prenoms;
    public static volatile SingularAttribute<AbonnementBanque, String> cni;
    public static volatile SingularAttribute<AbonnementBanque, BigInteger> compte;

}