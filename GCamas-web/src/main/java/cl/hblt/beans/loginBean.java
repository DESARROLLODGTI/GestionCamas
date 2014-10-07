/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import static cl.hblt.beans.usuariosBean.sha256;
import cl.hblt.entities.Especialidad;
import cl.hblt.entities.Menu;
import cl.hblt.entities.Opcion;
import cl.hblt.entities.UsuarioOpcion;
import cl.hblt.entities.UsuarioSistema;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.MenuFacade;
import cl.hblt.sessions.MenuFacadeLocal;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import utils.appBean;

/**
 *
 * @author AndresEduardo
 */
@ManagedBean
@SessionScoped
public class loginBean implements Serializable {

    @EJB
    private final MenuFacadeLocal menuFacade;
    @EJB
    private final BussinessFacadeLocal bussinessFacade;
    private List<Menu> menus;
    private String clave;
    private String rut;
    private char dv;
    private static String nombreEspecialidad;
    private static UsuarioSistema user;
    private static String usuarioLogeado;
    private static Integer idOpcUsuario;
    private appBean app;

    /**
     * Creates a new instance of loginBean
     */
    public loginBean() {
        bussinessFacade = new BussinessFacade();
        menuFacade = new MenuFacade();
    }

    public void login(ActionEvent actionEvent) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        rut = rut.replace("-", "");
        rut = rut.replace(".", "");
        String rut1 = rut.substring(0, rut.length() - 1);
        String dv2 = rut.charAt(rut.length() - 1) + "";
        clave = sha256(clave);
        String ruta = "";
        menus = menuFacade.findAll2("nombre");
        user = bussinessFacade.findByLoginUsuario(Integer.parseInt(rut1));
        if (user.getClaveAcceso().equals(clave)) {
            if (user.getIndActivo() == 1) {
                loggedIn = true;
                ExternalContext extContext = context.getExternalContext();
                extContext.getSessionMap().put("Usuario", user.getNombreUsuario());
                extContext.getSessionMap().put("idEspecialidad", user.getIdEspecialidad().getIdEspecialidad());
                extContext.getSessionMap().put("Especialidad", user.getIdEspecialidad());
                extContext.getSessionMap().put("idRol", user.getIdRol().getIdRol());
                extContext.getSessionMap().put("idUsuario", user.getIdUsuario());
                extContext.getSessionMap().put("ObjetoUsuario", user);
               
                
                List<Menu> AuxMenu = new ArrayList<Menu>();
                List<UsuarioOpcion> auxOpUser = bussinessFacade.getOpcionesUsuario(user.getIdUsuario());
                //creacion del menu
                for (Menu menu : menus) {
                    ArrayList<Opcion> aux2 = new ArrayList<Opcion>();
                    for (UsuarioOpcion usuarioOpcion : auxOpUser) {
                        if (menu.getIdMenu() == usuarioOpcion.getIdOpcion().getIdMenu().getIdMenu().intValue()) {
                            aux2.add(usuarioOpcion.getIdOpcion());
                        }
                    }
                    if (!aux2.isEmpty()) {
                        Menu AuxObjeto = menu;
                        AuxObjeto.setOpcionList(aux2);
                        AuxMenu.add(AuxObjeto);
                    }
                }
                extContext.getSessionMap().put("Menus", AuxMenu);
                String url = extContext.encodeActionURL(context.getApplication().getViewHandler().getActionURL(context, "/Welcome.xhtml"));
                extContext.redirect(url);
            } else {
                loggedIn = false;
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario Inactivo", "Usuario Inactivo"));
            }
        } else {
            loggedIn = false;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error en Credenciales", "Datos Invalidos"));
        }
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
    
   

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void logOut() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.invalidate();
        RequestContext context = RequestContext.getCurrentInstance();
        app = new appBean();
        context.addCallbackParam("url2", app.getBaseUrl2() + "/login.xhtml");
    }

    public String getUsuarioLogeado() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        usuarioLogeado = (String) extContext.getSessionMap().get("Usuario");
        return usuarioLogeado;
    }

    public void onFormateaRut() {
        this.setRut(appBean.FormateaRut(rut));
    }

    public static Integer getIdOpcUsuario() {
        return idOpcUsuario;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public char getDv() {
        return dv;
    }

    public String getRut() {
        return rut;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setDv(char dv) {
        this.dv = dv;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

}
