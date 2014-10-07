/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.RegistroLog;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author termiwum
 */
@Stateless
public class RegistroLogFacade extends AbstractFacade<RegistroLog> implements RegistroLogFacadeLocal {
    @PersistenceContext(unitName = "cl.hblt_GCamas-ejb_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistroLogFacade() {
        super(RegistroLog.class);
    }
    
}
