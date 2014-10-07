/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Menu;
import cl.hblt.entities.Opcion;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.MenuFacade;
import cl.hblt.sessions.MenuFacadeLocal;
import cl.hblt.sessions.OpcionFacade;
import cl.hblt.sessions.OpcionFacadeLocal;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author AndresEduardo
 */
@ManagedBean
@RequestScoped
public class menuBean {
    @EJB
    private final OpcionFacadeLocal opcionFacade;
    @EJB
    private BussinessFacadeLocal bussinessFacade;
    @EJB
    private final MenuFacadeLocal menuFacade;
    private Menu menu;
    private Opcion opcion;
    private List<Menu> filterMenu;
    private List<Opcion> opciones;
    private List<Opcion> filterOpciones;
    /**
     * Creates a new instance of menuBean
     */
    public menuBean() {
        menuFacade = new MenuFacade();
        opcionFacade = new OpcionFacade();
        menu = new Menu();
        opcion = new Opcion();
    }

    public void createMenu() throws IOException {
        if (menu.getNombre() != null && !menu.getNombre().equalsIgnoreCase("") && bussinessFacade.BuscarNombreMenu(menu.getNombre())) {
            menuFacade.create(menu);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "Menu Guardado Exitosamente: "));
            context.getExternalContext().redirect("Menus.xhtml");
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "Menu ya Existe o Campo Vacio"));
        }

    }

    public void updateMenu() throws IOException {
        if (menu.getNombre() != null && !menu.getNombre().equalsIgnoreCase("") && bussinessFacade.BuscarNombreMenu(menu.getNombre())) {
            menuFacade.edit(menu);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "Menu Guardado Exitosamente: "));
            context.getExternalContext().redirect("Menus.xhtml");
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "Menu ya Existe "));
        }
    }

    public void createSubMenu() throws IOException {
        if (!opcion.getNombreOpcion().equals("") && !opcion.getPaginaOpcion().equals("")) {
            opcion.setNombreOpcion(opcion.getNombreOpcion().toUpperCase());
            opcionFacade.create(opcion);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "SubMenu Guardado Exitosamente: "));
            context.getExternalContext().redirect("Menus.xhtml");

        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", " "));
        }
    }

    public void updateSubMenu() throws IOException {
        if (!opcion.getNombreOpcion().equals("") && !opcion.getPaginaOpcion().equals("")) {
            opcion.setNombreOpcion(opcion.getNombreOpcion().toUpperCase());
            opcionFacade.edit(opcion);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "SubMenu Guardado Exitosamente: "));
            context.getExternalContext().redirect("Menus.xhtml");

        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", " "));
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menu> getMenus() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        return (List<Menu>) extContext.getSessionMap().get("Menus");
        
    }

    public List<Menu> getFilterMenu() {
        return filterMenu;
    }

    public void setFilterMenu(List<Menu> filterMenu) {
        this.filterMenu = filterMenu;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

    public List<Opcion> getOpciones(Menu menu) {
        this.opciones = bussinessFacade.findByIdMenu(menu);
        return this.opciones;
    }

    public void setOpciones(List<Opcion> opciones) {
        this.opciones = opciones;
    }

    public List<Opcion> getFilterOpciones() {
        return filterOpciones;
    }

    public void setFilterOpciones(List<Opcion> filterOpciones) {
        this.filterOpciones = filterOpciones;
    }

    public boolean checkMenu(Integer idMenu){
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        Integer id = (Integer)extContext.getSessionMap().get("idUsuario");
        return bussinessFacade.checkMenu(id, idMenu);
    }

}
