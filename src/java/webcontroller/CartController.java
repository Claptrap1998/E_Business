package webcontroller;

import entity.Cart;
import webcontroller.util.JsfUtil;
import webcontroller.util.JsfUtil.PersistAction;
import ejb.CartFacade;
import entity.Good;
import entity.User;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("cartController")
@RequestScoped
public class CartController implements Serializable {

    @EJB
    private ejb.CartFacade ejbFacade;
    private List<Cart> items = null;
    private Cart selected=new Cart();

    public CartController() {
    }

    public Cart getSelected() {
        return selected;
    }

    public void setSelected(Cart selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CartFacade getFacade() {
        return ejbFacade;
    }

    public Cart prepareCreate() {
        System.out.println("1");
        selected = new Cart();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        System.out.println("2");
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CartCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CartUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CartDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Cart> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Cart getCart(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Cart> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Cart> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Cart.class)
    public static class CartControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CartController controller = (CartController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cartController");
            return controller.getCart(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cart) {
                Cart o = (Cart) object;
                return getStringKey(o.getCartId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cart.class.getName()});
                return null;
            }
        }

    }
    
    int total = 0;

    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    
    
      private List<Cart> carts = null;

    public List<Cart> getCarts(int id) {
        if (carts == null) {
            carts = ejbFacade.findbyuserid(id);
        }
        
        for(int i = 0;i < carts.size();i++){
            Cart acart= carts.get(i);
            int m = acart.getGoodgoodid().getGoodPrice();
            total +=acart.getCartAmount() * m;
              
    } 
        System.out.println(total);
//        logger.log(Level.INFO, "Created new user with user userId {0},name {1}, password {2},useAddr {3},phonenumber {4},and email {5} .",
//                        new Object[]{userId, name, password, userAddr, phonenumber, userBalance, email});
        return carts;
    }
    
    public String removeCart(Cart cart) {
        try {
            ejbFacade.remove(cart);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "index.xhtml";
    }
    


}
