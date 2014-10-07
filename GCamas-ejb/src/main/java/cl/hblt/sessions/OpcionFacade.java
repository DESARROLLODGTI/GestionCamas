/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.Opcion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author termiwum
 */
@Stateless
public class OpcionFacade extends AbstractFacade<Opcion> implements OpcionFacadeLocal {
    @PersistenceContext(unitName = "cl.hblt_GCamas-ejb_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OpcionFacade() {
        super(Opcion.class);
    }
    
}
