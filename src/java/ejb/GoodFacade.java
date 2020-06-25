/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Good;
import entity.Store;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author 13250
 */
@Stateless
public class GoodFacade extends AbstractFacade<Good> {

    @PersistenceContext(unitName = "E_BusinessPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GoodFacade() {
        super(Good.class);
    }
    
        public List<Good> Findspeci(int store_id){
        String s="SELECT good FROM good,store WHERE good.store_store_id=store."+store_id;
        Query query=em.createNativeQuery(s,Store.class);
        return query.getResultList();
    }

    List Findspeci(List Store_store_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public List<Good> findbystoreid(int id){
        return (List<Good>)em.createNamedQuery("Good.findBystorestoreid").setParameter("storeid", id).getResultList();
    }
    
}
