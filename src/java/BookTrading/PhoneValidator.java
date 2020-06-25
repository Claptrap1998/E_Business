/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BookTrading;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author taeyeon
 */
@FacesValidator("BookTrading.PhoneValidator")
public class PhoneValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value.toString().trim().length() < 11) {
            FacesMessage msg = new FacesMessage("输入号码过短");
            throw new ValidatorException(msg);
        } else if (value.toString().trim().length() > 11) {
            FacesMessage msg = new FacesMessage("输入号码过长");
            throw new ValidatorException(msg);
        }
    }

}
