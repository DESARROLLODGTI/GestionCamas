/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.TipoEgreso;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Edwin_Guaman
 */
@Stateless
public class TipoEgresoFacade extends AbstractFacade<TipoEgreso> implements TipoEgresoFacadeLocal {
  @PersistenceContext(unitName = "cl.hblt_GCamas-ejb_ejb_1.0PU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public TipoEgresoFacade() {
    super(TipoEgreso.class);
  }
  
}
