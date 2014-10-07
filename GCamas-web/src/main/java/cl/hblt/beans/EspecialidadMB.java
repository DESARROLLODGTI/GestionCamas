/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Especialidad;
import cl.hblt.entities.Sala;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.EspecialidadFacade;
import cl.hblt.sessions.EspecialidadFacadeLocal;
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
 * @author termiwum
 */
@ManagedBean
@RequestScoped
public class EspecialidadMB {
    @EJB
    private BussinessFacadeLocal bussinessFacade;

    @EJB
    private EspecialidadFacadeLocal especialidadFacade;

    private List<Sala> listaSalasByIdEspecialidad = null;
    private List<Especialidad> listaEspecialidades = null;
    private Especialidad especialidad = null;
    private String nombre;
    private Boolean activo;
    private Integer id;
    private FacesContext fc;
    /**
     * Creates a new instance of EspecialidadMB
     */
    public EspecialidadMB() {
        fc = FacesContext.getCurrentInstance();
        especialidadFacade = new EspecialidadFacade();
        especialidad = new Especialidad();
        activo = true;
        nombre = null;
    }

    public void update() {
//        if (bussinessFacade.findIdEspecialidadByName(especialidad.getNombreEspecialidad()).equals(this.especialidad.getIdEspecialidad())) {
//            short valor = 0;
//            if (!activo) {
//                especialidad.setIndActivo(valor);
//            } else {
//                valor = (short) (valor + 1);
//                especialidad.setIndActivo(valor);
//            }
//            especialidadFacade.edit(especialidad);
//            try {
//                FacesContext.getCurrentInstance().getExternalContext()
//                        .redirect("Especialidades.xhtml");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
            if (bussinessFacade.findEspecialidadByName(especialidad.getNombreEspecialidad()) == null) {
                short valor = 0;
                if (!activo) {
                    especialidad.setIndActivo(valor);
                } else {
                    valor = (short) (valor + 1);
                    especialidad.setIndActivo(valor);
                }
                especialidadFacade.edit(especialidad);
                try {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .redirect("Especialidades.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                fc.addMessage(null, new FacesMessage("Error en la modificacion", "Ya existe Especialidad"));
            }
//        }
    }

    public void create() {

        if (bussinessFacade.findEspecialidadByName(nombre) == null) {
            especialidad = new Especialidad();
            especialidad.setNombreEspecialidad(nombre);
            short valor = 0;
            if (!activo) {
                especialidad.setIndActivo(valor);
            } else {
                valor = (short) (valor + 1);
                especialidad.setIndActivo(valor);
            }
            especialidadFacade.create(especialidad);
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("Especialidades.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fc.addMessage(null, new FacesMessage("Error en la creacion", "Ya existe Especialidad"));
        }
    }
    
    public List<Sala> getSalasByEspecialidad(Integer id){
        List<Sala> lista =  bussinessFacade.findSalasByIdEspecialidad(id);
        return lista;
    }

    public List<Especialidad> getEspecialidades() {
        return this.listaEspecialidades;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
        this.activo = especialidad.getIndActivo() == 1;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @PostConstruct
    public void myInitMethod() {
        this.listaEspecialidades = especialidadFacade.findAll();
    }
}
