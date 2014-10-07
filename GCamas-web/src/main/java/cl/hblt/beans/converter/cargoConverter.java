/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.beans.converter;

import cl.hblt.entities.Cargo;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author AndresEduardo
 */
@FacesConverter(value = "cargoConverter", forClass = Cargo.class)
public class cargoConverter  implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent arg1, String arg2) throws ConverterException {
        if (arg2.trim().equals("")) {
            return null;
        } else {
            return new Cargo(Integer.parseInt(arg2));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent arg1, Object arg2) throws ConverterException{
        if (arg2 == null || arg2.equals("")) {
            return "";
        } else {
            return String.valueOf(((Cargo) arg2).getIdCargo());
        }
    }
    
}
