package com.sbs.easymbank.entities;

import com.sbs.easymbank.entities.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2022-09-07T18:22:11")
@StaticMetamodel(Agences.class)
public class Agences_ { 

    public static volatile SingularAttribute<Agences, String> codeagence;
    public static volatile ListAttribute<Agences, Users> usersList;
    public static volatile SingularAttribute<Agences, String> libelle;
    public static volatile SingularAttribute<Agences, String> datecreation;
    public static volatile SingularAttribute<Agences, Integer> idagences;

}