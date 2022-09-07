package com.sbs.easymbank.entities;

import com.sbs.easymbank.entities.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2022-09-07T18:22:11")
@StaticMetamodel(Logs.class)
public class Logs_ { 

    public static volatile SingularAttribute<Logs, Date> dateLog;
    public static volatile SingularAttribute<Logs, String> machine;
    public static volatile SingularAttribute<Logs, String> module;
    public static volatile SingularAttribute<Logs, String> action;
    public static volatile SingularAttribute<Logs, Integer> idlogs;
    public static volatile SingularAttribute<Logs, String> message;
    public static volatile SingularAttribute<Logs, String> login;
    public static volatile SingularAttribute<Logs, Users> users;

}