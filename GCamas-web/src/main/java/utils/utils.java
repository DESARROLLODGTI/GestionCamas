/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author AndresEduardo
 */
public class utils {
     public static String getBaseUrl() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String serverContextPath = servletContext.getContextPath();
        return serverContextPath;
    }
}
