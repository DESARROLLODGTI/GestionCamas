/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.EstadoPaciente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author termiwum
 */
@Local
public interface EstadoPacienteFacadeLocal {

    void create(EstadoPaciente estadoPaciente);

    void edit(EstadoPaciente estadoPaciente);

    void remove(EstadoPaciente estadoPaciente);

    EstadoPaciente find(Object id);

    List<EstadoPaciente> findAll();

    List<EstadoPaciente> findRange(int[] range);

    int count();
    
}
