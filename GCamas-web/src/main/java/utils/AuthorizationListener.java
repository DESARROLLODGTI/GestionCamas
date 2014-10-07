/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 *
 * @author AndresEduardo
 */
public class AuthorizationListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        String currentPage = facesContext.getViewRoot().getViewId();
        boolean isLoginPage;
        isLoginPage = currentPage.lastIndexOf("login.xhtml") > -1;
        String usuario; 
        if (extContext.getSessionMap().get("Usuario") == null)
            usuario = "";
        else
            usuario = (String) extContext.getSessionMap().get("Usuario");
        if (!isLoginPage) {
            if (usuario.isEmpty()) {
                String url = extContext.encodeActionURL(context.getApplication().getViewHandler().getActionURL(context, "/login.xhtml"));
                try {
                    extContext.redirect(url);
                } catch (IOException ex) {
                }
            }
        }

    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
