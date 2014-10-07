/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Prevision;
import cl.hblt.entities.TipoPrevision;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.PrevisionFacade;
import cl.hblt.sessions.PrevisionFacadeLocal;
import cl.hblt.sessions.TipoPrevisionFacade;
import cl.hblt.sessions.TipoPrevisionFacadeLocal;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Edwin_Guaman
 */
public class tipoPrevisionBean {
  @EJB
  private PrevisionFacadeLocal previsionFacade= null;
  @EJB
  private BussinessFacadeLocal bussinessFacade=null;
  @EJB
  private TipoPrevisionFacadeLocal tipoPrevisionFacade = null;
  
  
  private Prevision prevision;
  private TipoPrevision tipoPrevision = null;
  private List<TipoPrevision> listTipoPrevisiones = null;
  private List<Prevision> listPrevisiones = null;

  public tipoPrevisionBean() {
    tipoPrevisionFacade = new TipoPrevisionFacade();
    bussinessFacade= new BussinessFacade();
    previsionFacade= new PrevisionFacade();
    this.tipoPrevision= new TipoPrevision();
    this.prevision = new Prevision();
  }

  public Prevision getPrevision() {
    return prevision;
  }

  public void setPrevision(Prevision prevision) {
    this.prevision = prevision;
  }

  public TipoPrevision getTipoPrevision() {
    return tipoPrevision;
  }

  public void setTipoPrevision(TipoPrevision tipoPrevision) {
    this.tipoPrevision = tipoPrevision;
  }

  /**
   * *
   * @return lista de tipo de previsiones
   */
  public List<TipoPrevision> getListTipoPrevisiones() {
    return this.listTipoPrevisiones;
  }
  public List<Prevision> getListPrevisiones() {
    return this.listPrevisiones;
  }

  public void addTipoPrevision() {
    try {
      tipoPrevision.setDescripcionTipoPrevision(tipoPrevision.getDescripcionTipoPrevision().toUpperCase());
      tipoPrevision.setIdPrevision(prevision);
      if (!bussinessFacade.findByDescripcionTipoPrevision(tipoPrevision)) {
        tipoPrevisionFacade.create(tipoPrevision);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful", "Tipo Previsión Guardada Exitosamente: "));
        context.getExternalContext().redirect("Previsiones.xhtml");
      } else {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nombre de Tipo Previsión Existe", null));
      }
    } catch (IOException e) {
      FacesContext context = FacesContext.getCurrentInstance();
      context.addMessage(null, new FacesMessage("Error", e.getMessage()));
    }
  }
  
  public void editTipoPrevision() {
    try {
      tipoPrevision.setDescripcionTipoPrevision(tipoPrevision.getDescripcionTipoPrevision().toUpperCase());
      tipoPrevision.setIdPrevision(prevision);
      if (!bussinessFacade.findByDescripcionTipoPrevision(tipoPrevision)) {
        tipoPrevisionFacade.edit(tipoPrevision);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful", "Tipo Previsión Modificada Exitosamente: "));
        context.getExternalContext().redirect("../Prevision/Previsiones.xhtml");
      } else {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nombre de Tipo Previsión Existe", null));
      }
    } catch (IOException e) {
      FacesContext context = FacesContext.getCurrentInstance();
      context.addMessage(null, new FacesMessage("Error", e.getMessage()));
    }
  }
  
  

  @PostConstruct
  public void initMethod() {
    this.listTipoPrevisiones = tipoPrevisionFacade.findAll();
    this.listPrevisiones = previsionFacade.findAll();
    
  }

  public TipoPrevision find(Integer id) {
    for (TipoPrevision tp : listTipoPrevisiones) {
      if(tp.getIdTipoPrevision().equals(id)){
        return tp;
      }
    }
    return null;
  }
  
  
}
