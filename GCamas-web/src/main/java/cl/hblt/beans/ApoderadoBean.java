/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Apoderado;
import cl.hblt.entities.Parentesco;
import cl.hblt.sessions.ApoderadoFacade;
import cl.hblt.sessions.ApoderadoFacadeLocal;
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
import utils.appBean;

/**
 *
 * @author AndresEduardo
 */
@ManagedBean
@RequestScoped
public class ApoderadoBean {

    @EJB
    private final ParentescoFacadeLocal parentescoFacade;
    @EJB
    private final BussinessFacadeLocal bussinessFacade;
    @EJB
    private final ApoderadoFacadeLocal apoderadoFacade;
    private Apoderado apoderado;
    private List<Apoderado> Apoderados;
    private List<Parentesco> parentescos;
    private List<Apoderado> filterApoderados;
    private String rut;
    private boolean tarjetaApoderado;

    public ApoderadoBean() {
        apoderadoFacade = new ApoderadoFacade();
        bussinessFacade = new BussinessFacade();
        parentescoFacade = new ParentescoFacade();
        apoderado = new Apoderado();
    }

    public void create() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        camposMayusculas();
        rut = rut.replace("-", "");
        rut = rut.replace(".", "");
        String rut1 = rut.substring(0, rut.length() - 1);
        String dv = rut.charAt(rut.length() - 1) + "";
        if (bussinessFacade.checkApoderado(rut)) {
            apoderado.setRunApoderado(Integer.parseInt(rut1));
            apoderado.setDvApoderado(dv.toUpperCase());
            apoderadoFacade.create(apoderado);
            context.getExternalContext().redirect("Apoderados.xhtml");
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Rut Ya Ingresado", "Rut Duplicado"));
        }
    }

    public void update() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        camposMayusculas();
        apoderadoFacade.edit(apoderado);
        context.getExternalContext().redirect("Apoderados.xhtml");

    }

    public void camposMayusculas() {

        apoderado.setNombreApoderado(apoderado.getNombreApoderado().toUpperCase());
        apoderado.setPapellidoApoderado(apoderado.getPapellidoApoderado().toUpperCase());
        apoderado.setSapellidoApoderado(apoderado.getSapellidoApoderado().toUpperCase());
    }

    public void onValidaRut() {
        rut = rut.replace("-", "");
        rut = rut.replace(".", "");
        String rut1 = rut.substring(0, rut.length() - 1);
        String dv = rut.charAt(rut.length() - 1) + "";
        if (!appBean.validarRut(rut1, dv)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rut Invalido", "Rut Invalido"));
        }
    }

    public void onFormateaRut() {
        this.setRut(appBean.FormateaRut(rut));
    }

    @PostConstruct
    public void myInitMenthod() {
        Apoderados = apoderadoFacade.findAll2("papellidoApoderado");
        parentescos = parentescoFacade.findAll();
    }

    public Apoderado getApoderado() {
        return apoderado;
    }

    public void setApoderado(Apoderado apoderado) {
        this.apoderado = apoderado;
    }

    public List<Apoderado> getApoderados() {
        return Apoderados;
    }

    public void setApoderados(List<Apoderado> Apoderados) {
        this.Apoderados = Apoderados;
    }

    public List<Apoderado> getFilterApoderados() {
        return filterApoderados;
    }

    public void setFilterApoderados(List<Apoderado> filterApoderados) {
        this.filterApoderados = filterApoderados;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public List<Parentesco> getParentescos() {
        return parentescos;
    }

    public void setParentescos(List<Parentesco> parentescos) {
        this.parentescos = parentescos;
    }

    public boolean isTarjetaApoderado() {
        return tarjetaApoderado;
    }

    public void setTarjetaApoderado(boolean tarjetaApoderado) {
        this.tarjetaApoderado = tarjetaApoderado;
    }
    
    
}
