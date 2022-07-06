package com.sbs.easymbank.entities;

import com.sbs.easymbank.entities.Operateurs;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2022-07-06T17:23:57")
@StaticMetamodel(Transactions.class)
public class Transactions_ { 

    public static volatile SingularAttribute<Transactions, String> reason;
    public static volatile SingularAttribute<Transactions, String> accounttype;
    public static volatile SingularAttribute<Transactions, String> trandate;
    public static volatile SingularAttribute<Transactions, String> mobileno;
    public static volatile SingularAttribute<Transactions, Operateurs> operateurs;
    public static volatile SingularAttribute<Transactions, String> mobilename;
    public static volatile SingularAttribute<Transactions, String> currentbalance;
    public static volatile SingularAttribute<Transactions, String> availablebalance;
    public static volatile SingularAttribute<Transactions, String> requesttype;
    public static volatile SingularAttribute<Transactions, String> crdr;
    public static volatile SingularAttribute<Transactions, String> accountname;
    public static volatile SingularAttribute<Transactions, String> accountstatus;
    public static volatile SingularAttribute<Transactions, String> transferdescription;
    public static volatile SingularAttribute<Transactions, String> ccy;
    public static volatile SingularAttribute<Transactions, String> operatorcode;
    public static volatile SingularAttribute<Transactions, BigInteger> commission;
    public static volatile SingularAttribute<Transactions, String> requesttoken;
    public static volatile SingularAttribute<Transactions, String> branchcode;
    public static volatile SingularAttribute<Transactions, String> responsecode;
    public static volatile SingularAttribute<Transactions, String> amount;
    public static volatile SingularAttribute<Transactions, String> charge;
    public static volatile SingularAttribute<Transactions, String> externalrefno;
    public static volatile SingularAttribute<Transactions, String> mobilealias;
    public static volatile SingularAttribute<Transactions, String> accountalias;
    public static volatile SingularAttribute<Transactions, Boolean> reconcilie;
    public static volatile SingularAttribute<Transactions, String> tranrefno;
    public static volatile SingularAttribute<Transactions, String> responsemessage;
    public static volatile SingularAttribute<Transactions, String> affiliatecode;
    public static volatile SingularAttribute<Transactions, String> accountclass;
    public static volatile SingularAttribute<Transactions, String> udf3;
    public static volatile SingularAttribute<Transactions, String> origine;
    public static volatile SingularAttribute<Transactions, String> udf1;
    public static volatile SingularAttribute<Transactions, String> udf2;
    public static volatile SingularAttribute<Transactions, Integer> idtransactions;
    public static volatile SingularAttribute<Transactions, String> cbareferenceno;
    public static volatile SingularAttribute<Transactions, String> requestid;
    public static volatile SingularAttribute<Transactions, String> accountno;
    public static volatile SingularAttribute<Transactions, String> narration;
    public static volatile SingularAttribute<Transactions, String> trantype;

}