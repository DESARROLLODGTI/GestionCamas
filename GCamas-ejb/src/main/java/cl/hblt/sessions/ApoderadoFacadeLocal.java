/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.Apoderado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author termiwum
 */
@Local
public interface ApoderadoFacadeLocal {

    void create(Apoderado apoderado);

    void edit(Apoderado apoderado);

    void remove(Apoderado apoderado);

    Apoderado find(Object id);

    List<Apoderado> findAll();
    
     List<Apoderado> findAll2(String dato);

    List<Apoderado> findRange(int[] range);

    int count();
    
}
