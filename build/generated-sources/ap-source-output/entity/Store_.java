package entity;

import entity.Good;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-08T19:57:54")
@StaticMetamodel(Store.class)
public class Store_ { 

    public static volatile SingularAttribute<Store, String> storePassword;
    public static volatile SingularAttribute<Store, String> storeName;
    public static volatile SingularAttribute<Store, Short> storeVerify;
    public static volatile CollectionAttribute<Store, Good> goodCollection;
    public static volatile SingularAttribute<Store, Integer> storeId;

}