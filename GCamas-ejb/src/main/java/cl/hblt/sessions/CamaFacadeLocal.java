/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.Cama;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author termiwum
 */
@Local
public interface CamaFacadeLocal {

    void create(Cama cama);

    void edit(Cama cama);

    void remove(Cama cama);

    Cama find(Object id);

    List<Cama> findAll();

    List<Cama> findRange(int[] range);

    int count();
    
}
