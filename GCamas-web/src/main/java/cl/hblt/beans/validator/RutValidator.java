/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import utils.appBean;

/**
 *
 * @author Edwin_Guaman
 */
@FacesValidator("Validator.RutValidator")
public class RutValidator implements Validator {

  public RutValidator() {
  }

  @Override
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    String rutCompleto = value.toString().replace("-", "");
    rutCompleto = rutCompleto.replace(".", "");
    if (rutCompleto.isEmpty()) {
      throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rut Invalido", null));
    } else {
      String rut1 = rutCompleto.substring(0, rutCompleto.length() - 1);
      String dv = rutCompleto.charAt(rutCompleto.length() - 1) + "";

      if (!appBean.validarRut(rut1, dv)) {
        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rut Invalido", null));
      }
    }
  }

}
