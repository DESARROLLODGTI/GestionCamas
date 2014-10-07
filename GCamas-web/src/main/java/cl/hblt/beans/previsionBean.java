package cl.hblt.beans;

import cl.hblt.entities.Prevision;
import cl.hblt.entities.TipoPrevision;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.PrevisionFacade;
import cl.hblt.sessions.PrevisionFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public final class previsionBean implements Serializable {
  @EJB
  private final BussinessFacadeLocal bussinessFacade;
  @EJB
  private final PrevisionFacadeLocal previsionFacade;
  
  
  private Prevision prevision = null;
  private List<Prevision> listPrevision = null;
  private List<TipoPrevision> listTipoPrevision = null;
  public FacesContext context1 = null;

  public previsionBean() {
    prevision = new Prevision();
    previsionFacade = new PrevisionFacade();
    bussinessFacade = new BussinessFacade();
    context1= FacesContext.getCurrentInstance();
  }

  public Prevision getPrevision() {
    return prevision;
  }

  public void setPrevision(Prevision prevision) {
    this.prevision = prevision;
  }

  /**
   * Metodo getAddPrevision Se encarga de ingresar una previsión
   */
  public void addPrevision() {
    try {
      prevision.setDescripcionPrevision(prevision.getDescripcionPrevision().toUpperCase());
      if (!bussinessFacade.findByDescripcionPrevision(prevision)) {
        previsionFacade.create(prevision);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful", "Previsión Guardada Exitosamente: "));
        context.getExternalContext().redirect("Previsiones.xhtml");
      } else {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nombre de Previsión Existe", null));
      }
    } catch (IOException e) {
      FacesContext context = FacesContext.getCurrentInstance();
      context.addMessage(null, new FacesMessage("Error", e.getMessage()));
    }
  }

  public void editPrevision() {
    try {
      prevision.setDescripcionPrevision(prevision.getDescripcionPrevision().toUpperCase());
      if (!bussinessFacade.findByDescripcionPrevision(prevision)) {
        previsionFacade.edit(prevision);
        context1.getExternalContext().redirect("Previsiones.xhtml");
        context1.addMessage(null, new FacesMessage("Successful", "Previsión Guardada Exitosamente: "));
      } else {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nombre de Previsión Existe", null));
      }
    } catch (IOException e) {
      FacesContext context = FacesContext.getCurrentInstance();
      context.addMessage(null, new FacesMessage("Error", e.getMessage()));
    }
  }

  public List<Prevision> getAllPrevisiones() {
    return this.listPrevision;
  }

  public List<TipoPrevision> getListTipoPrevision() {
    return this.listTipoPrevision;
  }
  
  
  public List<TipoPrevision> listByPrevision(Prevision prevision) {
    this.listTipoPrevision= bussinessFacade.findByIdPrevision(prevision);
    return this.listTipoPrevision;
  }

  @PostConstruct
  public void myInitMethod() {
    //this.listPrevision = bussinessFacade.findAll();
    this.listPrevision = previsionFacade.findAll2("descripcionPrevision");
  }
}
