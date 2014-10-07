/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.ControlAcceso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author termiwum
 */
@Local
public interface ControlAccesoFacadeLocal {

    void create(ControlAcceso controlAcceso);

    void edit(ControlAcceso controlAcceso);

    void remove(ControlAcceso controlAcceso);

    ControlAcceso find(Object id);

    List<ControlAcceso> findAll();

    List<ControlAcceso> findRange(int[] range);

    int count();
    
}
