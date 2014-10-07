/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Rol;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.RolFacade;
import cl.hblt.sessions.RolFacadeLocal;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author AndresEduardo
 */
@ManagedBean (name = "rolBean")
@RequestScoped
public class rolBean {
    
    @EJB
    private RolFacadeLocal rolFacade;
    
    @EJB
    private BussinessFacadeLocal bussinessFacade;
    
    private Rol rol;
    List<Rol> Roles = null;
    boolean aux;
    
    public rolBean() {
        rolFacade = new RolFacade();
        rol = new Rol();
    }
    
    public void create() {
        if (bussinessFacade.FindByDato("Rol", "findByDescripcionRol", "descripcionRol", rol.getDescripcionRol())) {
            try {
                if (aux) {
                    rol.setIndActivo((short) 1);
                } else {
                    rol.setIndActivo((short) 0);
                }
                rol.setDescripcionRol(rol.getDescripcionRol().toUpperCase());
                rolFacade.create(rol);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cargo Guardado Exitosamente", null));
                context.getExternalContext().redirect("Roles.xhtml");
            } catch (IOException e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error", e.getMessage()));
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cargo ya Existe", null));
        }
    }
    
    public void update() {
        if (bussinessFacade.IdByDato2(rol.getDescripcionRol()).equals(this.rol.getIdRol())) { 
            try {
                if (aux) {
                    rol.setIndActivo((short) 1);
                } else {
                    rol.setIndActivo((short) 0);
                }
                rol.setDescripcionRol(rol.getDescripcionRol().toUpperCase());
                rolFacade.edit(rol);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cargo Guardado Exitosamente", null));
                context.getExternalContext().redirect("Roles.xhtml");
            } catch (IOException e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error", e.getMessage()));
            }
        } else {
            if (bussinessFacade.FindByDato("Rol", "findIdByRol", "descripcionRol", rol.getDescripcionRol())) {
            try {
                if (aux) {
                    rol.setIndActivo((short) 1);
                } else {
                    rol.setIndActivo((short) 0);
                }
                rol.setDescripcionRol(rol.getDescripcionRol().toUpperCase());
                rolFacade.edit(rol);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cargo Guardado Exitosamente", null));
                context.getExternalContext().redirect("Roles.xhtml");
            } catch (IOException e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error", e.getMessage()));
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cargo ya Existe", null));
        }
            
        }
    }
    
    public List<Rol> getRoles() {
        return Roles;
    }
    
    public void setRoles(List<Rol> Roles) {
        this.Roles = Roles;
    }
    
    public Rol getRol() {
        return rol;
    }
    
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public boolean isAux() {
        return aux;
    }
    
    public void setAux(boolean aux) {
        this.aux = aux;
    }
    
    @PostConstruct
    public void myInitMethod() {
        Roles = rolFacade.findAll2("descripcionRol");
    }
    
}
