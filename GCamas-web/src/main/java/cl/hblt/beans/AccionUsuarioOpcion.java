/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.UsuarioOpcion;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.UsuarioOpcionFacade;
import cl.hblt.sessions.UsuarioOpcionFacadeLocal;
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
public class AccionUsuarioOpcion {
    @EJB
    private BussinessFacadeLocal bussinessFacade;
    @EJB
    private UsuarioOpcionFacadeLocal usuarioOpcionFacade;
    

    private UsuarioOpcion usuarioOpcion;
    private UsuarioOpcion uo;
    private Boolean agregar;
    private Boolean editar;
    private Boolean asignarCama;
    private Boolean trasladarPaciente;
    private Boolean egresarPaciente;
    private Boolean modEstadoPAciente;
    private Boolean asignarApoderado;

    /**
     * Creates a new instance of AccionUsuarioOpcion
     */
    public AccionUsuarioOpcion() {
        usuarioOpcionFacade = new UsuarioOpcionFacade();
        usuarioOpcion = new UsuarioOpcion();
        uo = new UsuarioOpcion();
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
        uo = bussinessFacade.findByIdUsuarioOpcion(usuarioOpcion.getIdUsuarioOpcion());
        if (agregar) {
            uo.setOpcionAg(valor1);
        } else {
            uo.setOpcionAg(valor2);
        }

        if (editar) {
            uo.setOpcionEd(valor1);
        } else {
            uo.setOpcionEd(valor2);
        }

        if (asignarApoderado) {
            uo.setOpcionAa(valor1);
        } else {
            uo.setOpcionAa(valor2);
        }

        if (asignarCama) {
            uo.setOpcionAc(valor1);
        } else {
            uo.setOpcionAc(valor2);
        }

        if (trasladarPaciente) {
            uo.setOpcionTp(valor1);
        } else {
            uo.setOpcionTp(valor2);
        }

        if (modEstadoPAciente) {
            uo.setOpcionMep(valor1);
        } else {
            uo.setOpcionMep(valor2);
        }
         if (egresarPaciente) {
            uo.setOpcionEp(valor1);
        } else {
            uo.setOpcionEp(valor2);
        }
        
        usuarioOpcionFacade.edit(uo);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect("../Usuarios/Usuarios.xhtml");
    }

    public UsuarioOpcion getUsuarioOpcion() {
        return usuarioOpcion;
    }

    public void setUsuarioOpcion(UsuarioOpcion usuarioOpcion) {
        this.usuarioOpcion = usuarioOpcion;
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
