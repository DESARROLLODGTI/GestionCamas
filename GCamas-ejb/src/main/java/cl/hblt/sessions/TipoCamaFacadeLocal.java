/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.TipoCama;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author termiwum
 */
@Local
public interface TipoCamaFacadeLocal {

    void create(TipoCama tipoCama);

    void edit(TipoCama tipoCama);

    void remove(TipoCama tipoCama);

    TipoCama find(Object id);

    List<TipoCama> findAll();

    List<TipoCama> findRange(int[] range);

    int count();
    
}
