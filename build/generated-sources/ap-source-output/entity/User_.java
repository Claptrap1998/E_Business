package entity;

import entity.Cart;
import entity.Record;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-08T19:57:54")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile CollectionAttribute<User, Cart> cartCollection;
    public static volatile SingularAttribute<User, String> userPassword;
    public static volatile SingularAttribute<User, String> userMail;
    public static volatile SingularAttribute<User, String> userAddr;
    public static volatile SingularAttribute<User, String> userTel;
    public static volatile SingularAttribute<User, Integer> userBalance;
    public static volatile SingularAttribute<User, String> userName;
    public static volatile CollectionAttribute<User, Record> recordCollection;
    public static volatile SingularAttribute<User, Integer> userId;

}