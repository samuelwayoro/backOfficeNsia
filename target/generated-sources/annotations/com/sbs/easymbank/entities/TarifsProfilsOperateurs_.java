package com.sbs.easymbank.entities;

import com.sbs.easymbank.entities.Operateurs;
import com.sbs.easymbank.entities.ProfilsClients;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2022-07-06T17:23:57")
@StaticMetamodel(TarifsProfilsOperateurs.class)
public class TarifsProfilsOperateurs_ { 

    public static volatile SingularAttribute<TarifsProfilsOperateurs, Short> idTarifs;
    public static volatile SingularAttribute<TarifsProfilsOperateurs, Long> tarif;
    public static volatile SingularAttribute<TarifsProfilsOperateurs, String> service;
    public static volatile SingularAttribute<TarifsProfilsOperateurs, ProfilsClients> profils;
    public static volatile SingularAttribute<TarifsProfilsOperateurs, Operateurs> operateurs;

}