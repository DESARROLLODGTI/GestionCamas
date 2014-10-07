/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Parentesco;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.ParentescoFacade;
import cl.hblt.sessions.ParentescoFacadeLocal;
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
@ManagedBean
@RequestScoped
public class ParentescoBean {

    @EJB
    private final BussinessFacadeLocal bussinessFacade;
    @EJB
    private final ParentescoFacadeLocal parentescoFacade;
    private Parentesco parentesco;
    private List<Parentesco> parentescos;
    private List<Parentesco> filterParentesco;

    /**
     * Creates a new instance of ParentescoBean
     */
    public ParentescoBean() {
        bussinessFacade = new BussinessFacade();
        parentescoFacade = new ParentescoFacade();
        parentesco = new Parentesco();
    }

    public void create() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        parentesco.setDescripcionParentesco(parentesco.getDescripcionParentesco().toUpperCase());
        if (bussinessFacade.checkParentesco(parentesco.getDescripcionParentesco())) {
            parentescoFacade.create(parentesco);
            context.getExternalContext().redirect("Parentescos.xhtml");
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Parentesco ya Existe", null));
        }
    }
    
    public void update() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        parentesco.setDescripcionParentesco(parentesco.getDescripcionParentesco().toUpperCase());
        if (bussinessFacade.checkParentesco(parentesco.getDescripcionParentesco())) {
            parentescoFacade.edit(parentesco);
            context.getExternalContext().redirect("Parentescos.xhtml");
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Chequear el campo Descripción", null));
        }
    }


    @PostConstruct
    public void myInitMethod() {
        parentescos = parentescoFacade.findAll();
    }

    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }

    public List<Parentesco> getParentescos() {
        return parentescos;
    }

    public void setParentescos(List<Parentesco> parentescos) {
        this.parentescos = parentescos;
    }

    public List<Parentesco> getFilterParentesco() {
        return filterParentesco;
    }

    public void setFilterParentesco(List<Parentesco> filterParentesco) {
        this.filterParentesco = filterParentesco;
    }

}
