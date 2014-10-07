/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.RegistroLog;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author termiwum
 */
@Local
public interface RegistroLogFacadeLocal {

    void create(RegistroLog registroLog);

    void edit(RegistroLog registroLog);

    void remove(RegistroLog registroLog);

    RegistroLog find(Object id);

    List<RegistroLog> findAll();

    List<RegistroLog> findRange(int[] range);

    int count();
    
}
