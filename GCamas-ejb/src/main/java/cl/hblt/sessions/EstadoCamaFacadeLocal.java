/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.EstadoCama;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author termiwum
 */
@Local
public interface EstadoCamaFacadeLocal {

    void create(EstadoCama estadoCama);

    void edit(EstadoCama estadoCama);

    void remove(EstadoCama estadoCama);

    EstadoCama find(Object id);

    List<EstadoCama> findAll();

    List<EstadoCama> findRange(int[] range);

    int count();
    
}
