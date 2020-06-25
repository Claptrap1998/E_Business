/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Store;
import entity.User;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import webcontroller.util.JsfUtil;
import webcontroller.util.PaginationHelper;

/**
 *
 * @author taeyeon
 */
@Named("registerService")
@SessionScoped
@Stateful
public class RegisterService implements Serializable {

    @EJB
    private UserFacade ejbFacade;
    @PersistenceContext(unitName = "E_BusinessPU")
    private EntityManager em;
    /////////////////////////////
    private User current;
    private DataModel items = null;

    private PaginationHelper pagination;
    private int selectedItemIndex;
    //////////////////////////////
    private static final Logger logger = Logger.getLogger("BookTrading.ejb.RegisterService");

    private String type;
    private String name;
    private String email;
    private String phonenumber;
    private String password;
    private String passwordConfirm;
    private String login;
    private String loginPassword;
    private Integer userId;
    private String userAddr;
    private int userBalance;
    private Integer storeId;
    private short storeVerify;
    private String storeName;

    private String named;
    private int Sid;

    String name_;
    String password_;
    String address_;
    String tel_;
    int balance_;
    String mail_;

    public void createUser(Integer userId, String userName, String userPassword, String userAddr, String userTel, int userBalance, String userMail) {
        try {
            User user = new User(userId, userName, userPassword, userAddr, userTel, userBalance, userMail);
            em.persist(user);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public boolean checkUser(String login, String loginPassword) {
        try {
            List checklists = em.createNamedQuery("User.findAll").getResultList();
            for (Iterator it = checklists.iterator(); it.hasNext();) {
                User checklist = (User) it.next();
                if (login.equals(checklist.getUserName()) && loginPassword.equals(checklist.getUserPassword())) {
                    this.name = login;
                    return true;
                } else if (login.equals(checklist.getUserMail()) && loginPassword.equals(checklist.getUserPassword())) {
                    this.email = login;
                    return true;
                } else if (login.equals(checklist.getUserTel()) && loginPassword.equals(checklist.getUserPassword())) {
                    this.phonenumber = login;
                    return true;
                }
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return false;
    }

    public String getNamed() {
        if (this.name != null) {
            User user = (User) em.createNamedQuery("User.findByUserName").setParameter("userName", this.name).getSingleResult();
            this.phonenumber = user.getUserTel();
            this.password = user.getUserPassword();
            this.userId = user.getUserId();
            this.userAddr = user.getUserAddr();
            this.userBalance = user.getUserBalance();
            this.email = user.getUserMail();
            return name;
        } else if (this.email != null) {
            User user = (User) em.createNamedQuery("User.findByUserMail").setParameter("userMail", this.email).getSingleResult();
            this.name = user.getUserName();
            this.phonenumber = user.getUserTel();
            this.password = user.getUserPassword();
            this.userId = user.getUserId();
            this.userAddr = user.getUserAddr();
            this.userBalance = user.getUserBalance();
            return name;
        } else if (this.phonenumber != null) {
            User user = (User) em.createNamedQuery("User.findByUserTel").setParameter("userTel", this.phonenumber).getSingleResult();
            this.name = user.getUserName();
            this.password = user.getUserPassword();
            this.userId = user.getUserId();
            this.userAddr = user.getUserAddr();
            this.userBalance = user.getUserBalance();
            this.email = user.getUserMail();
            return name;
        }
        return null;
    }

    public void setNamed(String named) {
        this.named = named;
    }

    public int getSidd() {
        Store store = (Store) em.createNamedQuery("Store.findByStoreName").setParameter("storeName", this.name).getSingleResult();
        this.Sid = store.getStoreId();
        return Sid;
    }

    public int getSid() {
        return Sid;
    }

    public void setSid(int Sid) {
        this.Sid = Sid;
    }

    public void createStore(Integer storeId, String storeName, short storeVerify, String storePassword, String storeEmail, String storeTel) {
        try {
            Store store = new Store(storeId, storeName, storeVerify, storePassword);
            em.persist(store);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public boolean checkStore(String login, String loginPassword) {
        try {
            List checklists = em.createNamedQuery("Store.findAll").getResultList();
            for (Iterator it = checklists.iterator(); it.hasNext();) {
                Store checklist = (Store) it.next();
                if (login.equals(checklist.getStoreName()) && loginPassword.equals(checklist.getStorePassword())) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return false;
    }

    public String submitRegister() {
        try {
//            注册类型如果为personal，则创建一个用户，并向用户数据库表添加信息；
//            注册类型如果为shop，则创建一个店铺，并向店铺数据库表添加信息；
            if (type.equals("personal")) {
                createUser(userId, name, password, userAddr, phonenumber, userBalance, email);
                logger.log(Level.INFO, "Created new user with user userId {0},name {1}, password {2},useAddr {3},phonenumber {4},and email {5} .",
                        new Object[]{userId, name, password, userAddr, phonenumber, userBalance, email});
            } else if (type.equals("shop")) {
                createStore(storeId, name, storeVerify, password, email, phonenumber);
                logger.log(Level.INFO, "Created new store with store storeId {0},name {1}, verify {2},password {3},email {4},and phonenumber {5} .",
                        new Object[]{storeId, name, storeVerify, password, email, phonenumber});
            }
//            返回注册成功页面
            return "/PERSON/index.xhtml";

        } catch (Exception e) {
            logger.warning("注册失败");
//            返回注册失败界面
            return "failure";
        }
    }

    public void checkUserLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UIComponent c = null;
//        寻找相同父组件下的子组件，在此函数中即寻找和密码框处于相同父组件的登录名框
        for (UIComponent ui : component.getParent().getChildren()) {
            if ("login".equals(ui.getId()) || "slogin".equals(ui.getId()) || "alogin".equals(ui.getId())) {
                c = ui;
                break;
            }
        }
//        将登录名框转化为htmlInputText
        HtmlInputText htmlInputText = (HtmlInputText) c;
        boolean checked = checkUser(htmlInputText.getValue().toString().trim(), value.toString().trim());
        if (!checked) {
            FacesMessage msg = new FacesMessage("密码错误！");
            throw new ValidatorException(msg);
        }
    }

    public void checkStoreLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UIComponent c = null;
        for (UIComponent ui : component.getParent().getChildren()) {
            if ("login".equals(ui.getId()) || "slogin".equals(ui.getId()) || "alogin".equals(ui.getId())) {
                c = ui;
                break;
            }
        }
        HtmlInputText htmlInputText = (HtmlInputText) c;
        boolean checked = checkStore(htmlInputText.getValue().toString().trim(), value.toString().trim());
        if (!checked) {
            FacesMessage msg = new FacesMessage("密码错误！");
            throw new ValidatorException(msg);
        }
    }

    public void isPasswordRight(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UIComponent c = null;

        for (UIComponent ui : component.getParent().getChildren()) {
            if ("register_pw1".equals(ui.getId())) {
                c = ui;
                break;
            }
        }
        HtmlInputSecret htmlInputSecret = (HtmlInputSecret) c;
        if (!value.toString().trim().equals(htmlInputSecret.getValue().toString().trim())) {
            FacesMessage msg = new FacesMessage("两次输入密码不一致！");
            throw new ValidatorException(msg);
        }
        if (value.toString().trim().length() < 6 || value.toString().trim().length() > 15) {
            FacesMessage msg = new FacesMessage("输入密码长度应为6~15");
            throw new ValidatorException(msg);
        }
        if (htmlInputSecret.getValue().toString().trim().length() < 6 || htmlInputSecret.getValue().toString().trim().length() > 15) {
            FacesMessage msg = new FacesMessage("输入密码长度应为6~15");
            throw new ValidatorException(msg);
        }
    }

/////////////////////////////////////////////
    public User getSelected() {
        if (current == null) {
            current = new User();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UserFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new User();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public User getUser(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    //////////////////////////////////////////   
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Integer getUserId() {
        try {
            List checklists = em.createNamedQuery("User.findAll").getResultList();
            for (Iterator it = checklists.iterator(); it.hasNext();) {
                User checklist = (User) it.next();
                if (login.equals(checklist.getUserName()) && loginPassword.equals(checklist.getUserPassword())) {
                    return checklist.getUserId();
                } else if (login.equals(checklist.getUserMail()) && loginPassword.equals(checklist.getUserPassword())) {
                    return checklist.getUserId();
                } else if (login.equals(checklist.getUserTel()) && loginPassword.equals(checklist.getUserPassword())) {
                    return checklist.getUserId();
                }
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return 1;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public int getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(int userBalance) {
        this.userBalance = userBalance;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public short getStoreVerify() {
        return storeVerify;
    }

    public void setStoreVerify(short storeVerify) {
        this.storeVerify = storeVerify;
    }

    public String getName_() {
        name_ = ejbFacade.find(this.userId).getUserName();
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    public void setUserName_(ActionEvent event) {
        current = ejbFacade.find(this.userId);
        current.setUserName(name_);
        ejbFacade.edit(current);
    }

    public String getPassword_() {
        password_ = ejbFacade.find(this.userId).getUserPassword();
        return password_;
    }

    public void setPassword_(String password_) {
        this.password_ = password_;
    }

//    为什么不能用password？ 为什么最后要用edit。。。。。。actionevent是啥
    public void setUserPassword(ActionEvent event) {
        current = ejbFacade.find(this.userId);
        current.setUserPassword(password_);
        ejbFacade.edit(current);
    }

    public String getAddress_() {
        address_ = ejbFacade.find(this.userId).getUserAddr();
        return address_;
    }

    public void setAddress_(String address_) {
        this.address_ = address_;
    }

    public void setUserAddress(ActionEvent event) {
        current = ejbFacade.find(this.userId);
        current.setUserAddr(address_);
        ejbFacade.edit(current);
    }

    public String getTel_() {
        tel_ = ejbFacade.find(this.userId).getUserTel();
        return tel_;
    }

    public void setTel_(String tel_) {
        this.tel_ = tel_;
    }

    public void setUserTel(ActionEvent event) {
        current = ejbFacade.find(this.userId);
        current.setUserTel(tel_);
        ejbFacade.edit(current);
    }

    public int getBalance_() {
        balance_ = ejbFacade.find(this.userId).getUserBalance();
        return balance_;
    }

    public void setBalance_(int balance_) {
        this.balance_ = balance_;
    }

    public void setUserBalance(ActionEvent event) {
        current = ejbFacade.find(this.userId);
        current.setUserBalance(balance_);
        ejbFacade.edit(current);
    }

    public String getMail_() {
        mail_ = ejbFacade.find(this.userId).getUserMail();
        return mail_;
    }

    public void setMail_(String mail_) {
        this.mail_ = mail_;
    }

    public void setUserMail(ActionEvent event) {
        current = ejbFacade.find(this.userId);
        current.setUserMail(mail_);
        ejbFacade.edit(current);
    }
}
