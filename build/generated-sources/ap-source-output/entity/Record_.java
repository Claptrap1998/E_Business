package entity;

import entity.Good;
import entity.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-08T19:57:54")
@StaticMetamodel(Record.class)
public class Record_ { 

    public static volatile SingularAttribute<Record, Integer> recordId;
    public static volatile SingularAttribute<Record, Date> recordTime;
    public static volatile SingularAttribute<Record, Integer> recordAmount;
    public static volatile SingularAttribute<Record, Integer> recordTotalprice;
    public static volatile SingularAttribute<Record, User> useruserid;
    public static volatile SingularAttribute<Record, Good> goodgoodid;

}