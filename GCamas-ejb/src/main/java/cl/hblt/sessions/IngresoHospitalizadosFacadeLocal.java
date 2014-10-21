/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.IngresoHospitalizados;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author termiwum
 */
@Local
public interface IngresoHospitalizadosFacadeLocal {

    void create(IngresoHospitalizados ingresoHospitalizados);

    void edit(IngresoHospitalizados ingresoHospitalizados);

    void remove(IngresoHospitalizados ingresoHospitalizados);

    IngresoHospitalizados find(Object id);

    List<IngresoHospitalizados> findAll();

    List<IngresoHospitalizados> findRange(int[] range);

    int count();
    
}
