/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Cama;
import cl.hblt.entities.Especialidad;
import cl.hblt.entities.EstadoCama;
import cl.hblt.entities.Sala;
import cl.hblt.entities.TipoCama;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.CamaFacadeLocal;
import cl.hblt.sessions.EstadoCamaFacade;
import cl.hblt.sessions.EstadoCamaFacadeLocal;
import cl.hblt.sessions.TipoCamaFacade;
import cl.hblt.sessions.TipoCamaFacadeLocal;
import java.io.IOException;
import java.math.BigInteger;
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
public class CamaBean {

    @EJB
    private BussinessFacadeLocal bussinessFacade;
    @EJB
    private EstadoCamaFacadeLocal estadoCamaFacade;
    @EJB
    private TipoCamaFacadeLocal tipoCamaFacade;
    @EJB
    private CamaFacadeLocal camaFacade;

    private Boolean activo;
    private List<EstadoCama> listaEstadosCama;
    private List<TipoCama> listaTiposCama;
    private Especialidad especilialidad;
    private Sala sala;
    private EstadoCama estadoCama;
    private TipoCama tipoCama;
    private Cama cama;
    private FacesContext fc;
    private BigInteger numeroCama;
    private Integer idCamaParaUpdate;

    /**
     * Creates a new instance of CamaBean
     */
    public CamaBean() {
        fc = FacesContext.getCurrentInstance();
        estadoCamaFacade = new EstadoCamaFacade();
        bussinessFacade = new BussinessFacade();
        tipoCamaFacade = new TipoCamaFacade();
        especilialidad = new Especialidad();
        tipoCama = new TipoCama();
        sala = new Sala();
        cama = new Cama();
        activo = true;
        numeroCama = null;
    }

    public void create() {
        if (!bussinessFacade.existeCamaEnSala(this.numeroCama, sala)) {
            this.cama.setNumeroCama(this.numeroCama);
            this.cama.setIdSala(new Sala(sala.getIdSala()));
            this.cama.setIdEstadoCama(new EstadoCama(2)); // Para la creacion de camas el estado de la cama debe ser desocupada 1:OCUPADA ; 2:DESOCUPADA
            this.cama.setIdTipoCama(new TipoCama(tipoCama.getIdTipoCama()));
            short valor = 0;
            if (!activo) {
                cama.setIndActivo(valor);
            } else {
                valor = (short) (valor + 1);
                cama.setIndActivo(valor);
            }
            camaFacade.create(this.cama);
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("../Salas/Salas.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fc.addMessage(null, new FacesMessage("Error en la creacion", "Ya existe cama en la sala"));
        }
    }

    public void update() {

        sala = bussinessFacade.findSalaById(cama.getIdSala().getIdSala());
        cama = bussinessFacade.findCamaById(idCamaParaUpdate);
        
        
        if (cama != null && cama.getIdCama().equals(idCamaParaUpdate)) {
            short valor = 0;
            if (!activo) {
                cama.setIndActivo(valor);
            } else {
                valor = (short) (valor + 1);
                cama.setIndActivo(valor);
            }
            cama.setIdTipoCama(tipoCama);
            cama.setNumeroCama(numeroCama);
            camaFacade.edit(this.cama);
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("../Salas/Salas.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cama = new Cama();
            if (!bussinessFacade.existeCamaEnSala(numeroCama, sala)) {
                short valor = 0;
                if (!activo) {
                    cama.setIndActivo(valor);
                } else {
                    valor = (short) (valor + 1);
                    cama.setIndActivo(valor);
                }
                cama.setIdCama(idCamaParaUpdate);
                cama.setIdTipoCama(tipoCama);
                cama.setNumeroCama(numeroCama);
                cama.setIdEstadoCama(new EstadoCama(2));
                cama.setIdSala(sala);
                camaFacade.edit(this.cama);
                try {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .redirect("../Salas/Salas.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                fc.addMessage(null, new FacesMessage("Error en la modificacion", "Ya existe esta cama en la sala"));
            }
        }
    }

    public List<EstadoCama> getListaEstadosCama() {
        return listaEstadosCama;
    }

    public void setListaEstadosCama(List<EstadoCama> listaEstadosCama) {
        this.listaEstadosCama = listaEstadosCama;
    }

    public List<TipoCama> getListaTiposCama() {
        return listaTiposCama;
    }

    public void setListaTiposCama(List<TipoCama> listaTiposCama) {
        this.listaTiposCama = listaTiposCama;
    }

    public Especialidad getEspecilialidad() {
        return especilialidad;
    }

    public void setEspecilialidad(Especialidad especilialidad) {
        this.especilialidad = especilialidad;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public EstadoCama getEstadoCama() {
        return estadoCama;
    }

    public void setEstadoCama(EstadoCama estadoCama) {
        this.estadoCama = estadoCama;
    }

    public TipoCama getTipoCama() {
        return tipoCama;
    }

    public void setTipoCama(TipoCama tipoCama) {
        this.tipoCama = tipoCama;
    }

    public Cama getCama() {
        return cama;
    }

    public void setCama(Cama cama) {
        this.cama = cama;
        this.activo = cama.getIndActivo() == 1;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public BigInteger getNumeroCama() {
        return numeroCama;
    }

    public void setNumeroCama(BigInteger numeroCama) {
        this.numeroCama = numeroCama;
    }

    public Integer getIdCamaParaUpdate() {
        return idCamaParaUpdate;
    }

    public void setIdCamaParaUpdate(Integer idCamaParaUpdate) {
        this.idCamaParaUpdate = idCamaParaUpdate;
    }

    @PostConstruct
    public void myInitMethod() {
        this.listaEstadosCama = estadoCamaFacade.findAll();
        this.listaTiposCama = tipoCamaFacade.findAll();

    }

}
