/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.TrasladoTemporal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author AndresEduardo
 */
@Local
public interface TrasladoTemporalFacadeLocal {

    void create(TrasladoTemporal trasladoTemporal);

    void edit(TrasladoTemporal trasladoTemporal);

    void remove(TrasladoTemporal trasladoTemporal);

    TrasladoTemporal find(Object id);

    List<TrasladoTemporal> findAll();
    List<TrasladoTemporal> findAll2(String dato);
    List<TrasladoTemporal> findRange(int[] range);

    int count();
    
}
