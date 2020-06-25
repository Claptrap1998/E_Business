/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BookTrading;

/**
 *
 * @author taeyeon
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("BookTrading.EmailValidator")
public class EmailValidator implements Validator{
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";

    private Pattern pattern;
    private Matcher matcher;
  
    public EmailValidator(){
      pattern = Pattern.compile(EMAIL_PATTERN);
    }
  
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        matcher = pattern.matcher(value.toString().trim());
        if(!matcher.matches()){
            FacesMessage msg = new FacesMessage("邮箱格式错误");
            throw new ValidatorException(msg);
        }        
    }
    
}
