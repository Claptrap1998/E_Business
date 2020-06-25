package entity;

import entity.Cart;
import entity.Record;
import entity.Store;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-08T19:57:54")
@StaticMetamodel(Good.class)
public class Good_ { 

    public static volatile CollectionAttribute<Good, Cart> cartCollection;
    public static volatile SingularAttribute<Good, Integer> goodStock;
    public static volatile SingularAttribute<Good, String> goodName;
    public static volatile SingularAttribute<Good, String> goodDescribe;
    public static volatile SingularAttribute<Good, Integer> goodId;
    public static volatile SingularAttribute<Good, String> goodCate;
    public static volatile SingularAttribute<Good, Integer> goodPrice;
    public static volatile CollectionAttribute<Good, Record> recordCollection;
    public static volatile SingularAttribute<Good, Store> storestoreid;
    public static volatile SingularAttribute<Good, String> goodPath;

}