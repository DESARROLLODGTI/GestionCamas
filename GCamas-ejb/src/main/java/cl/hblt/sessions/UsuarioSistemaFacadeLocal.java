/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.UsuarioSistema;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author termiwum
 */
@Local
public interface UsuarioSistemaFacadeLocal {

    void create(UsuarioSistema usuarioSistema);

    void edit(UsuarioSistema usuarioSistema);

    void remove(UsuarioSistema usuarioSistema);

    UsuarioSistema find(Object id);

    List<UsuarioSistema> findAll();
    List<UsuarioSistema> findAll2(String dato);
    List<UsuarioSistema> findRange(int[] range);

    int count();
    
}
