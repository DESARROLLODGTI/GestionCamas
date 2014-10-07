/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.RolOpcion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author termiwum
 */
@Local
public interface RolOpcionFacadeLocal {

    void create(RolOpcion rolOpcion);

    void edit(RolOpcion rolOpcion);

    void remove(RolOpcion rolOpcion);

    RolOpcion find(Object id);

    List<RolOpcion> findAll();

    List<RolOpcion> findRange(int[] range);

    int count();
    
}
