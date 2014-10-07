/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.TipoPrevision;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author termiwum
 */
@Local
public interface TipoPrevisionFacadeLocal {

    void create(TipoPrevision tipoPrevision);

    void edit(TipoPrevision tipoPrevision);

    void remove(TipoPrevision tipoPrevision);

    TipoPrevision find(Object id);

    List<TipoPrevision> findAll();

    List<TipoPrevision> findRange(int[] range);

    int count();
    
}
