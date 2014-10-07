/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Cargo;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.CargoFacade;
import cl.hblt.sessions.CargoFacadeLocal;
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
public class cargoBean {
    @EJB
    private BussinessFacadeLocal bussinessFacade;

    @EJB
    private CargoFacadeLocal cargoFacade;
    
    private Cargo Cargo;
    private Cargo SelectedCargo;
    List<Cargo> Cargos = null;

    /**
     * Creates a new instance of cargoBean
     */
    public cargoBean() {
        cargoFacade = new CargoFacade();
        Cargo = new Cargo();
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedCargo") != null) {
            this.Cargo = (Cargo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedCargo");
            //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedCargo", null);
        }
    }

    public List<Cargo> getAllCargos() {
        List<Cargo> cargos = this.Cargos;
        return cargos;
    }

    public Cargo getCargo() {
        return Cargo;
    }

    public void setCargo(Cargo Cargo) {
        this.Cargo = Cargo;
    }

    public void guardarCargo() {
        Cargo.setDescripcionCargo(Cargo.getDescripcionCargo().toUpperCase());
        if (bussinessFacade.findByDescripcionCargo(Cargo)) {
            try {
                cargoFacade.create(Cargo);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cargo Guardado Exitosamente",null));
                context.getExternalContext().redirect("Tabla.xhtml");

            } catch (IOException e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error", e.getMessage()));
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cargo ya Existe",null));
        }

    }

    public void volver() throws IOException {

        Cargo.setDescripcionCargo("");
        Cargo.setIdCargo(0);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect("Tabla.xhtml");

    }

    public void guardarMod() {
        Cargo.setDescripcionCargo(Cargo.getDescripcionCargo().toUpperCase());
        if (bussinessFacade.findByDescripcionCargo(Cargo)) {
            try {
                cargoFacade.edit(Cargo);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Successful", "Cargo Guardado Exitosamente: "));
                context.getExternalContext().redirect("Tabla.xhtml");

            } catch (IOException e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error", e.getMessage()));
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cargo ya Existe o Es el Mismo",null));
        }

    }

    public Cargo getSelectedCargo() {
        return SelectedCargo;
    }

    public void setSelectedCargo(Cargo SelectedCargo) {
        this.SelectedCargo = SelectedCargo;

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedCargo", this.SelectedCargo);
    }

    public void setIdSession(Cargo algo) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedCargo", algo);
    }

//    public List<Cargo> getCargos() {
//        return Cargos;
//    }
//
//    public void setCargos(List<Cargo> Cargos) {
//        this.Cargos = Cargos;
//    }
    @PostConstruct
    public void myInitMethod() {
        this.Cargos = cargoFacade.findAll2("descripcionCargo");
    }
}
