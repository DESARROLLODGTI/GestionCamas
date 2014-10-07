/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Opcion;
import cl.hblt.entities.Rol;
import cl.hblt.entities.RolOpcion;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.OpcionFacade;
import cl.hblt.sessions.OpcionFacadeLocal;
import cl.hblt.sessions.RolFacade;
import cl.hblt.sessions.RolFacadeLocal;
import cl.hblt.sessions.RolOpcionFacade;
import cl.hblt.sessions.RolOpcionFacadeLocal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author termiwum
 */
@ManagedBean
@RequestScoped
public class RolOpcionBean {

    @EJB
    private RolOpcionFacadeLocal rolOpcionFacade;
    @EJB
    private OpcionFacadeLocal opcionFacade;
    @EJB
    private RolFacadeLocal rolFacade;
    @EJB
    private BussinessFacadeLocal bussinessFacade;

    private List<Opcion> opcionesNoUsadas;
    private List<RolOpcion> listaOpcionesPorRol;

    private Rol rol,rol1;
    private Integer idRol;
    private DualListModel<Opcion> opcionesDisponibles;
    private Boolean activar;

    /**
     * Creates a new instance of RolOpcionBean
     */
    public RolOpcionBean() {
        opcionFacade = new OpcionFacade();
        opcionesDisponibles = new DualListModel<Opcion>();
        bussinessFacade = new BussinessFacade();
        rolFacade = new RolFacade();
        rol = new Rol();
        rol1 = new Rol();
        rolOpcionFacade = new RolOpcionFacade();
        activar = false;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public DualListModel<Opcion> getOpcionesDisponibles() {
        return opcionesDisponibles;
    }

    public void setOpcionesDisponibles(DualListModel<Opcion> opcionesDisponibles) {
        this.opcionesDisponibles = opcionesDisponibles;
    }

    public List<RolOpcion> getListaOpcionesPorRol() {
        return listaOpcionesPorRol;
    }

    public void setListaOpcionesPorRol(List<RolOpcion> listaOpcionesPorRol) {
        this.listaOpcionesPorRol = listaOpcionesPorRol;
    }

    public List<RolOpcion> getListaOpcionesByRol(Rol rol) {
        listaOpcionesPorRol = bussinessFacade.opcionesByRol(rol);
        return listaOpcionesPorRol;
    }

    public String getParams(FacesContext fc) {
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("idRol");
    }

    public String asignarPermisos() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        this.idRol = Integer.parseInt(getParams(fc));
        List<RolOpcion> opcionesXrol = bussinessFacade.opcionesByRol(new Rol(idRol));
        List<Opcion> opcionesXrolaux = new ArrayList<Opcion>();
        opcionesNoUsadas = bussinessFacade.findOpcionesNotUssedByRol(new Rol(idRol));
        for (RolOpcion rolOpcion : opcionesXrol) {
            opcionesXrolaux.add(rolOpcion.getIdOpcion());
        }
        List<Opcion> Source = opcionesNoUsadas;
        List<Opcion> Target = opcionesXrolaux;
        opcionesDisponibles = new DualListModel<Opcion>(Source, Target);
        return "../Permisos/asignacionPermisosRoles.xhtml";
    }

    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            Opcion aux = opcionFacade.find(((Opcion) item).getIdOpcion());
            builder.append(aux.getNombreOpcion());
        }
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Opciones Modificadas");
        msg.setDetail(builder.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void update() throws IOException {
        List<Opcion> lista = opcionesDisponibles.getTarget();
        List<RolOpcion> lista2 = bussinessFacade.opcionesByRol(new Rol(idRol));
        for (RolOpcion rolOpcion : lista2) {
            bussinessFacade.deleteRolOpcionByIdRol(rolOpcion.getIdRol());
        }
        for (Opcion opcion : lista) {
            RolOpcion rolOpcion = new RolOpcion();
            rolOpcion.setIdOpcion(opcion);
            rolOpcion.setIdRol(new Rol(idRol));
            rolOpcionFacade.create(rolOpcion);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Roles Modificados Exitosamente", null));
        context.getExternalContext().redirect("../Roles/Roles.xhtml");
    }

    public void onCellEdit(CellEditEvent event,Integer idRolOpcion) {
            RolOpcion aux = rolOpcionFacade.find(new RolOpcion(idRolOpcion));
            rolOpcionFacade.edit(aux);
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Opcion> getOpcionesNoUsadas() {
        return opcionesNoUsadas;
    }

    public void setOpcionesNoUsadas(List<Opcion> opcionesNoUsadas) {
        this.opcionesNoUsadas = opcionesNoUsadas;
    }

    public Boolean getActivar() {
        return activar;
    }

    public void setActivar(Boolean activar) {
        this.activar = activar;
    }

    public Rol getRol1() {
        return rol1;
    }

    public void setRol1(Rol rol1) {
        this.rol1 = rol1;
    }
    
}
