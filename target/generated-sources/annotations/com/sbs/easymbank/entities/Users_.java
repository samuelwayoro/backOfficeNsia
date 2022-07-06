package com.sbs.easymbank.entities;

import com.sbs.easymbank.entities.Agences;
import com.sbs.easymbank.entities.Logs;
import com.sbs.easymbank.entities.Profils;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2022-07-06T17:23:57")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> datedesactivation;
    public static volatile ListAttribute<Users, Logs> logsList;
    public static volatile SingularAttribute<Users, String> etatconnexion;
    public static volatile SingularAttribute<Users, String> dateactivation;
    public static volatile SingularAttribute<Users, Profils> idprofils;
    public static volatile SingularAttribute<Users, String> datemodification;
    public static volatile SingularAttribute<Users, Boolean> reinitialise;
    public static volatile SingularAttribute<Users, String> login;
    public static volatile SingularAttribute<Users, Boolean> activer;
    public static volatile SingularAttribute<Users, String> nom;
    public static volatile SingularAttribute<Users, Agences> idagences;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile SingularAttribute<Users, Integer> idusers;
    public static volatile SingularAttribute<Users, String> datecreation;
    public static volatile SingularAttribute<Users, String> datesuppression;
    public static volatile SingularAttribute<Users, String> datereinitialisation;
    public static volatile SingularAttribute<Users, String> prenom;

}