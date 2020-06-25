/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcontroller;

import ejb.RegisterService;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 *
 * @author taeyeon
 */
@Named("registerManager")
@RequestScoped
public class RegisterManager implements Serializable {

    @EJB
    private RegisterService service;
    private static final Logger logger = Logger.getLogger("BookTrading.web.RegisterManager");
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

    public String submitRegister() {
        try {
            if (type.equals("personal")) {
                service.createUser(userId, name, password, userAddr, phonenumber, userBalance, email);
                logger.log(Level.INFO, "Created new user with user userId {0},name {1}, password {2},useAddr {3},phonenumber {4},and email {5} .",
                        new Object[]{userId, name, password, userAddr, phonenumber, userBalance, email});
            } else if (type.equals("shop")) {
                service.createStore(storeId, name, storeVerify, password, email, phonenumber);
                logger.log(Level.INFO, "Created new store with store storeId {0},name {1}, verify {2},password {3},email {4},and phonenumber {5} .",
                        new Object[]{storeId, name, storeVerify, password, email, phonenumber});
            }
            return "register_success";

        } catch (Exception e) {
            logger.warning("注册失败");
            return "failure";
        }
    }

//    public String checkLogin() throws ValidatorException {
//        try {
//            boolean checked = service.check(login, loginPassword);
//            if (checked == true) {
//                return "personal";
//            } else if (checked == false) {
//                FacesMessage msg = new FacesMessage("密码错误！请检查登录名");
//                throw new ValidatorException(msg);
//            }
//        } catch (Exception e) {
//            logger.warning("登陆失败");
//        }
//        return null;
//    }
    public void checkUserLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UIComponent c = null;
        for (UIComponent ui : component.getParent().getChildren()) {
            if ("login".equals(ui.getId()) || "slogin".equals(ui.getId()) || "alogin".equals(ui.getId())) {
                c = ui;
                break;
            }
        }
        HtmlInputText htmlInputText = (HtmlInputText) c;
        boolean checked = service.checkUser(htmlInputText.getValue().toString().trim(), value.toString().trim());
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
        boolean checked = service.checkStore(htmlInputText.getValue().toString().trim(), value.toString().trim());
        if (!checked) {
            FacesMessage msg = new FacesMessage("密码错误！");
            throw new ValidatorException(msg);
        }
    }

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
        return userId;
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

    public short getStoreVerify() {
        return storeVerify;
    }

    public void setStoreVerify(short storeVerify) {
        this.storeVerify = storeVerify;
    }
//   public void checkType(FacesContext context, UIComponent component, Object value){
//       UIComponent personal = null;
//       UIComponent shop = null;
//       for (UIComponent ui : component.getChildren()) {
//            if ("personal".equals(ui.getId())) {
//                personal = ui;
//                break;
//            }
//       }       
//       for (UIComponent ui : component.getChildren()) {
//            if ("shop".equals(ui.getId())) {
//                shop = ui;
//                break;
//            }
//       }     
//   }

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

}
