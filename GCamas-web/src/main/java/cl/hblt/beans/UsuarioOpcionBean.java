/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Opcion;
import cl.hblt.entities.Rol;
import cl.hblt.entities.UsuarioOpcion;
import cl.hblt.entities.UsuarioSistema;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.OpcionFacadeLocal;
import cl.hblt.sessions.UsuarioOpcionFacadeLocal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author termiwum
 */
@ManagedBean
@RequestScoped
public class UsuarioOpcionBean {
    @EJB
    private UsuarioOpcionFacadeLocal usuarioOpcionFacade;

    @EJB
    private OpcionFacadeLocal opcionFacade;

    @EJB
    private BussinessFacadeLocal bussinessFacade;
    
    

    /**
     * Creates a new instance of UsuarioOpcionBean
     */
    private Integer idUsuario;
    private UsuarioSistema usuarioSistema;
    private Rol rol;
    private List<Opcion> opcionesNoUsadas;
    private List<UsuarioOpcion> listaOpcionesPorUsuario;
    private DualListModel<Opcion> opcionesDisponibles;

    public UsuarioOpcionBean() {
        usuarioSistema = new UsuarioSistema();
        rol = new Rol();
    }

    public String getParams(FacesContext fc) {
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("idUsuario");
    }

    public String asignarPermisos() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        this.idUsuario = Integer.parseInt(getParams(fc));
        List<UsuarioOpcion> opcionesXusser = bussinessFacade.opcionesByUsser(new UsuarioSistema(idUsuario));
        List<Opcion> opcionesXusseraux = new ArrayList<Opcion>();
        opcionesNoUsadas = bussinessFacade.findOpcionesNotUssedByUsser(new UsuarioSistema(idUsuario));
        for (UsuarioOpcion usuarioOpcion : opcionesXusser) {
            opcionesXusseraux.add(usuarioOpcion.getIdOpcion());
        }
        List<Opcion> Source = opcionesNoUsadas;
        List<Opcion> Target = opcionesXusseraux;
        opcionesDisponibles = new DualListModel<Opcion>(Source, Target);
        return "../Permisos/asignacionPermisosUsuarios.xhtml";
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
        List<UsuarioOpcion> lista2 = bussinessFacade.opcionesByUsser(new UsuarioSistema(idUsuario));
        for (UsuarioOpcion usuarioOpcion : lista2) {
            bussinessFacade.deleteUsuarioOpcionByIdUsser(new UsuarioSistema(idUsuario));
        }
        for (Opcion opcion : lista) {
            UsuarioOpcion usuarioOpcion = new UsuarioOpcion();
            usuarioOpcion.setIdOpcion(opcion);
            usuarioOpcion.setIdUsuario(new UsuarioSistema(idUsuario));
            usuarioOpcionFacade.create(usuarioOpcion);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Roles Modificados Exitosamente", null));
        context.getExternalContext().redirect("../Usuarios/Usuarios.xhtml");
    }

    public List<UsuarioOpcion> getListaOpcionesByUsser(UsuarioSistema usuario) {
        listaOpcionesPorUsuario = bussinessFacade.opcionesByUsser(usuario);
        return listaOpcionesPorUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public UsuarioSistema getUsuarioSistema() {
        return usuarioSistema;
    }

    public void setUsuarioSistema(UsuarioSistema usuarioSistema) {
        this.usuarioSistema = usuarioSistema;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public DualListModel<Opcion> getOpcionesDisponibles() {
        return opcionesDisponibles;
    }

    public void setOpcionesDisponibles(DualListModel<Opcion> opcionesDisponibles) {
        this.opcionesDisponibles = opcionesDisponibles;
    }

}
