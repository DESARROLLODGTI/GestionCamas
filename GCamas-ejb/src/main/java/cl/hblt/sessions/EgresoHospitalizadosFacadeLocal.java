/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.sessions;

import cl.hblt.entities.EgresoHospitalizados;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Edwin_Guaman
 */
@Local
public interface EgresoHospitalizadosFacadeLocal {

  void create(EgresoHospitalizados egresoHospitalizados);

  void edit(EgresoHospitalizados egresoHospitalizados);

  void remove(EgresoHospitalizados egresoHospitalizados);

  EgresoHospitalizados find(Object id);

  List<EgresoHospitalizados> findAll();

  List<EgresoHospitalizados> findRange(int[] range);

  int count();
  
}
