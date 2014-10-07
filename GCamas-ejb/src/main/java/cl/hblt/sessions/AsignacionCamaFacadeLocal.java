/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.AsignacionCama;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author termiwum
 */
@Local
public interface AsignacionCamaFacadeLocal {

    void create(AsignacionCama asignacionCama);

    void edit(AsignacionCama asignacionCama);

    void remove(AsignacionCama asignacionCama);

    AsignacionCama find(Object id);

    List<AsignacionCama> findAll();

    List<AsignacionCama> findRange(int[] range);

    int count();
    
}
