/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Cama;
import cl.hblt.entities.Especialidad;
import cl.hblt.entities.Sala;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.EspecialidadFacadeLocal;
import cl.hblt.sessions.SalaFacade;
import cl.hblt.sessions.SalaFacadeLocal;
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
public class SalaBean {

    @EJB
    private SalaFacadeLocal salaFacade;
    @EJB
    private EspecialidadFacadeLocal especialidadFacade;
    @EJB
    private BussinessFacadeLocal bussinessFacade;
    /**
     * Creates a new instance of SalaBean
     */
    private List<Cama> listaCamas = null;
    private List<Especialidad> listaEspecialidades = null;
    private List<Sala> listaSalas = null;
    private Especialidad especialidad = null;
    private Sala sala = null;
    private Integer id;
    private FacesContext fc;
    private String nombre;
    private Boolean activo;

    public SalaBean() {
        fc = FacesContext.getCurrentInstance();
        salaFacade = new SalaFacade();
        activo = true;
        nombre = null;
        especialidad = new Especialidad();
    }

    public void poderCrear() throws IOException {
        Boolean poder = false;

        if (!this.listaEspecialidades.isEmpty()) {
            poder = true;
            fc.getExternalContext().redirect("SalaCreate.xhtml");
        }
        if (!poder) {
            fc.addMessage(null, new FacesMessage("No Permitido", "No Existen Especialidades Activas"));
        }
    }

    public void create() {
        if (!bussinessFacade.existeSalaEnServicioByNombreServicioIdEspecialidad(nombre,especialidad.getIdEspecialidad())){
            sala.setNombreSala(nombre);
            sala.setIdEspecialidad(new Especialidad(especialidad.getIdEspecialidad()));
            short valor = 0;
            if (!activo) {
                sala.setIndActivo(valor);
            } else {
                valor = (short) (valor + 1);
                sala.setIndActivo(valor);
            }
            salaFacade.create(sala);
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("../Especialidades/Especialidades.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fc.addMessage(null, new FacesMessage("Error en la creacion", "Ya existe sala en este servicio"));
        }

    }

    public void update() {
//        if (bussinessFacade.findIdSalaByName(sala.getNombreSala()).equals(this.sala.getIdSala())) {
//            short valor = 0;
//            if (!activo) {
//                sala.setIndActivo(valor);
//            } else {
//                valor = (short) (valor + 1);
//                sala.setIndActivo(valor);
//            }
//            sala.setIdEspecialidad(new Especialidad(especialidad.getIdEspecialidad()));
//            salaFacade.edit(sala);
//            try {
//                FacesContext.getCurrentInstance().getExternalContext()
//                        .redirect("../Especialidades/Especialidades.xhtml");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
            if (bussinessFacade.findSalasByName(sala.getNombreSala()).isEmpty()) {
                short valor = 0;
                if (!activo) {
                    sala.setIndActivo(valor);
                } else {
                    valor = (short) (valor + 1);
                    sala.setIndActivo(valor);
                }
                sala.setIdEspecialidad(new Especialidad(especialidad.getIdEspecialidad()));
                salaFacade.edit(sala);
                try {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .redirect("../Especialidades/Especialidades.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                fc.addMessage(null, new FacesMessage("Error en la modificacion", "Ya existe la sala"));
            }
//        }
    }

    public List<Cama> getCamasByIdSala(Integer id) {
        List<Cama> lista = bussinessFacade.findCamasByIdSala(id);
        return lista;
    }

    public List<Especialidad> getListaEspecialidades() {
        return this.listaEspecialidades;
    }

    public List<Sala> getListaSalas() {
        return this.listaSalas;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
        this.activo = sala.getIndActivo() == 1;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public List<Cama> getListaCamas() {
        return listaCamas;
    }

    public void setListaCamas(List<Cama> listaCamas) {
        this.listaCamas = listaCamas;
    }

    @PostConstruct
    public void myInitMethod() {
        this.listaSalas = salaFacade.findAll();
        this.listaEspecialidades = bussinessFacade.findByIndActivo(Short.parseShort("1")); //Busca las especialidades activas
        this.sala = new Sala();
    }

}
