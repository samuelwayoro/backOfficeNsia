package com.sbs.easymbank.entities;

import com.sbs.easymbank.entities.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2022-07-06T17:23:57")
@StaticMetamodel(HistPassword.class)
public class HistPassword_ { 

    public static volatile SingularAttribute<HistPassword, String> password;
    public static volatile SingularAttribute<HistPassword, String> datecreation;
    public static volatile SingularAttribute<HistPassword, Users> idUsers;
    public static volatile SingularAttribute<HistPassword, Long> idHist;

}