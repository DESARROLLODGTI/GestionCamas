/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.RolOpcion;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.RolOpcionFacade;
import cl.hblt.sessions.RolOpcionFacadeLocal;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author termiwum
 */
@ManagedBean
@RequestScoped
public class AccionRolOpcion {
    @EJB
    private RolOpcionFacadeLocal rolOpcionFacade;
    @EJB
    private BussinessFacadeLocal bussinessFacade;
    

    private RolOpcion rolOpcion;
    private RolOpcion ro;
    private Boolean agregar;
    private Boolean editar;
    private Boolean asignarCama;
    private Boolean trasladarPaciente;
    private Boolean egresarPaciente;
    private Boolean modEstadoPAciente;
    private Boolean asignarApoderado;

    /**
     * Creates a new instance of AccionRolOpcion
     */
    public AccionRolOpcion() {
        rolOpcionFacade = new RolOpcionFacade();
        rolOpcion = new RolOpcion();
        ro = new RolOpcion();
        agregar = false;
        editar = false;
        asignarCama = false;
        trasladarPaciente = false;
        egresarPaciente = false;
        modEstadoPAciente = false;
        asignarApoderado = false;
    }

    public void update() throws IOException {
        Short valor1 = 1;
        Short valor2 = 0;
        ro = bussinessFacade.findByIdRolOpcion(rolOpcion.getIdRolOpcion());
        if (agregar) {
            ro.setOpcionAg(valor1);
        } else {
            ro.setOpcionAg(valor2);
        }

        if (editar) {
            ro.setOpcionEd(valor1);
        } else {
            ro.setOpcionEd(valor2);
        }

        if (asignarApoderado) {
            ro.setOpcionAa(valor1);
        } else {
            ro.setOpcionAa(valor2);
        }

        if (asignarCama) {
            ro.setOpcionAc(valor1);
        } else {
            ro.setOpcionAc(valor2);
        }

        if (trasladarPaciente) {
            ro.setOpcionTp(valor1);
        } else {
            ro.setOpcionTp(valor2);
        }

        if (modEstadoPAciente) {
            ro.setOpcionMep(valor1);
        } else {
            ro.setOpcionMep(valor2);
        }
         if (egresarPaciente) {
            ro.setOpcionEp(valor1);
        } else {
            ro.setOpcionEp(valor2);
        }
        
        rolOpcionFacade.edit(ro);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect("../Roles/Roles.xhtml");
    }

    public RolOpcion getRolOpcion() {
        return rolOpcion;
    }
    
    public void setRolOpcion(RolOpcion rolOpcion) {
        this.rolOpcion = rolOpcion;
    }

    public Boolean getAgregar() {
        return agregar;
    }

    public void setAgregar(Boolean agregar) {
        this.agregar = agregar;
    }

    public Boolean getEditar() {
        return editar;
    }

    public void setEditar(Boolean editar) {
        this.editar = editar;
    }

    public Boolean getAsignarCama() {
        return asignarCama;
    }

    public void setAsignarCama(Boolean asignarCama) {
        this.asignarCama = asignarCama;
    }

    public Boolean getTrasladarPaciente() {
        return trasladarPaciente;
    }

    public void setTrasladarPaciente(Boolean trasladarPaciente) {
        this.trasladarPaciente = trasladarPaciente;
    }

    public Boolean getEgresarPaciente() {
        return egresarPaciente;
    }

    public void setEgresarPaciente(Boolean egresarPaciente) {
        this.egresarPaciente = egresarPaciente;
    }

    public Boolean getModEstadoPAciente() {
        return modEstadoPAciente;
    }

    public void setModEstadoPAciente(Boolean modEstadoPAciente) {
        this.modEstadoPAciente = modEstadoPAciente;
    }

    public Boolean getAsignarApoderado() {
        return asignarApoderado;
    }

    public void setAsignarApoderado(Boolean asignarApoderado) {
        this.asignarApoderado = asignarApoderado;
    }
    
    @PostConstruct
    public void myInitMethod() {
          
    }

}
