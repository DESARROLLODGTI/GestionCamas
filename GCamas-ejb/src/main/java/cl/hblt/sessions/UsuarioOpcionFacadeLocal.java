/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.UsuarioOpcion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author termiwum
 */
@Local
public interface UsuarioOpcionFacadeLocal {

    void create(UsuarioOpcion usuarioOpcion);

    void edit(UsuarioOpcion usuarioOpcion);

    void remove(UsuarioOpcion usuarioOpcion);

    UsuarioOpcion find(Object id);

    List<UsuarioOpcion> findAll();

    List<UsuarioOpcion> findRange(int[] range);

    int count();
    
}
