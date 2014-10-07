/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Cargo;
import cl.hblt.entities.Especialidad;
import cl.hblt.entities.Rol;
import cl.hblt.entities.RolOpcion;
import cl.hblt.entities.UsuarioOpcion;
import cl.hblt.entities.UsuarioSistema;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.CargoFacade;
import cl.hblt.sessions.CargoFacadeLocal;
import cl.hblt.sessions.EspecialidadFacade;
import cl.hblt.sessions.EspecialidadFacadeLocal;
import cl.hblt.sessions.RolFacade;
import cl.hblt.sessions.RolFacadeLocal;
import cl.hblt.sessions.UsuarioOpcionFacade;
import cl.hblt.sessions.UsuarioOpcionFacadeLocal;
import cl.hblt.sessions.UsuarioSistemaFacade;
import cl.hblt.sessions.UsuarioSistemaFacadeLocal;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import utils.appBean;

/**
 *
 * @author AndresEduardo
 */
@ManagedBean
@RequestScoped
public class usuariosBean {

    @EJB
    private final UsuarioOpcionFacadeLocal usuarioOpcionFacade;

    @EJB
    private final RolFacadeLocal rolFacade;
    @EJB
    private final EspecialidadFacadeLocal especialidadFacade;
    @EJB
    private final CargoFacadeLocal cargoFacade;
    @EJB
    private final BussinessFacadeLocal bussinessFacade;
    @EJB
    private final UsuarioSistemaFacadeLocal usuarioSistemaFacade;

    private UsuarioSistema usuario;
    private List<UsuarioSistema> usuarios;
    private List<UsuarioSistema> filterUsuarios;
    private List<Cargo> cargos;
    private List<Especialidad> especialidades;
    private List<Rol> roles;
    private boolean activo;
    private Rol rol;
    private Especialidad especialidad;
    private Cargo cargo;
    private String rut;

    /**
     * Creates a new instance of usuariosBean
     */
    public usuariosBean() {
        usuarioSistemaFacade = new UsuarioSistemaFacade();
        usuario = new UsuarioSistema();
        bussinessFacade = new BussinessFacade();
        usuarioOpcionFacade = new UsuarioOpcionFacade();
        rolFacade = new RolFacade();
        especialidadFacade = new EspecialidadFacade();
        cargoFacade = new CargoFacade();
        rol = new Rol();
        cargo = new Cargo();
        especialidad = new Especialidad();
    }

    public void usuarioCreate() throws IOException {
        rut = rut.replace("-", "");
        rut = rut.replace(".", "");
        String rut1 = rut.substring(0, rut.length() - 1);
        String dv = rut.charAt(rut.length() - 1) + "";

        if (bussinessFacade.findByRutUsuario(Integer.parseInt(rut1))) {
                try {
                    if (activo) {
                        usuario.setIndActivo((short) 1);
                    } else {
                        usuario.setIndActivo((short) 0);
                    }
                    camposMayusculas();
                    usuario.setClaveAcceso(sha256(usuario.getClaveAcceso()));
                    usuario.setRutUsuario(Integer.parseInt(rut1));
                    usuario.setDvUsuario(dv.toUpperCase());
                    usuarioSistemaFacade.create(usuario);
                    List<RolOpcion> Opciones = bussinessFacade.opcionesByRol(usuario.getIdRol());
                    for (RolOpcion rolOpcion : Opciones) {
                        UsuarioOpcion aux = new UsuarioOpcion();
                        aux.setIdUsuario(usuario);
                        aux.setIdOpcion(rolOpcion.getIdOpcion());
                        usuarioOpcionFacade.create(aux);
                    }

                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Successful", "Paciente Guardada Exitosamente: "));
                    context.getExternalContext().redirect("Usuarios.xhtml");
                } catch (Exception e) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Error", e.getMessage()));
                }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rut Ya Ingresado", "Rut Duplicado"));
        }
    }

    public void usuarioUpdate() throws IOException {
        try {
            if (activo) {
                usuario.setIndActivo((short) 1);
            } else {
                usuario.setIndActivo((short) 0);
            }
            camposMayusculas();
            usuario.setClaveAcceso(sha256(usuario.getClaveAcceso()));
            usuarioSistemaFacade.edit(usuario);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "Paciente Guardada Exitosamente: "));
            context.getExternalContext().redirect("Usuarios.xhtml");
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", e.getMessage()));
        }
    }

    public void camposMayusculas() {
        usuario.setNombreUsuario(usuario.getNombreUsuario().toUpperCase());
        usuario.setPapellidoUsuario(usuario.getPapellidoUsuario().toUpperCase());
        usuario.setSapellidoUsuario(usuario.getSapellidoUsuario().toUpperCase());
        usuario.setCorreoElectronico(usuario.getCorreoElectronico().toUpperCase());
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
    
     public String getNombreEspecialdad(){
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        Especialidad Aux = (Especialidad) extContext.getSessionMap().get("Especialidad");
        return Aux.getNombreEspecialidad();
    }

    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void onFormateaRut() {
        this.setRut(appBean.FormateaRut(rut));
    }

    public UsuarioSistema getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSistema usuario) {
        this.usuario = usuario;
    }

    public List<UsuarioSistema> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioSistema> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public List<UsuarioSistema> getFilterUsuarios() {
        return filterUsuarios;
    }

    public void setFilterUsuarios(List<UsuarioSistema> filterUsuarios) {
        this.filterUsuarios = filterUsuarios;
    }

    @PostConstruct
    public void myInitMethod() {
        usuarios = usuarioSistemaFacade.findAll2("papellidoUsuario");
        cargos = cargoFacade.findAll();
        roles = rolFacade.findAll();
        especialidades = especialidadFacade.findAll();
    }
}
