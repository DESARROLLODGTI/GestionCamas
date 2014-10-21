/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.sessions;

import cl.hblt.entities.Apoderado;
import cl.hblt.entities.AsignacionCama;
import cl.hblt.entities.Cama;
import cl.hblt.entities.Cargo;
import cl.hblt.entities.EgresoHospitalizados;
import cl.hblt.entities.Especialidad;
import cl.hblt.entities.EstadoCama;
import cl.hblt.entities.EstadoPaciente;
import cl.hblt.entities.IngresoHospitalizados;
import cl.hblt.entities.Menu;
import cl.hblt.entities.Opcion;
import cl.hblt.entities.Paciente;
import cl.hblt.entities.Parentesco;
import cl.hblt.entities.Prevision;
import cl.hblt.entities.Rol;
import cl.hblt.entities.RolOpcion;
import cl.hblt.entities.Sala;
import cl.hblt.entities.TipoCama;
import cl.hblt.entities.TipoEgreso;
import cl.hblt.entities.TipoPrevision;
import cl.hblt.entities.TrasladoTemporal;
import cl.hblt.entities.UsuarioOpcion;
import cl.hblt.entities.UsuarioSistema;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Edwin_Guaman
 */
@Stateless
public class BussinessFacade implements BussinessFacadeLocal {

    @PersistenceContext(unitName = "cl.hblt_GCamas-ejb_ejb_1.0PU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public boolean findByDescripcionCargo(Cargo cargo) {
        boolean aux = false;
        try {
            Query query = em.createNamedQuery("Cargo.findByDescripcionCargo");
            query.setParameter("descripcionCargo", cargo.getDescripcionCargo());
            if (query.getResultList().isEmpty()) {
                aux = true;
            }
        } catch (NoResultException nre) {
            aux = false;
        }
        return aux;
    }

    @Override
    public boolean findByDescripcionPrevision(Prevision prevision) {
        try {
            Query query = em.createNamedQuery("Prevision.findByDescripcionPrevision");
            query.setParameter("descripcionPrevision", prevision.getDescripcionPrevision());
            if (query.getResultList().isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (NoResultException nre) {
            return false;
        }
    }

    @Override
    public Especialidad findEspecialidadByName(Object nombre) {
        try {
            Query query = em.createNamedQuery("Especialidad.findByNombreEspecialidad");
            query.setParameter("nombreEspecialidad", nombre);
            Especialidad e = (Especialidad) query.getSingleResult();
            if (e == null) {
                return null;
            } else {
                return e;
            }
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return null;
    }

    @Override
    public Integer IdByName(Object nombre) {
        Integer id = 0;
        try {
            Query query = em.createNamedQuery("Especialidad.findIdByNombreEspecialidad");
            query.setParameter("nombreEspecialidad", nombre);
            id = (Integer) query.getSingleResult();
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return null;
    }

    @Override
    public List<Especialidad> findByIndActivo(Short indActivo) {
        List<Especialidad> list = null;
        try {
            Query query = em.createNamedQuery("Especialidad.findByIndActivo");
            query.setParameter("indActivo", indActivo);
            list = query.getResultList();
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return list;
    }

    @Override
    public List<Sala> findSalasByName(Object name) {
        List<Sala> list = null;
        try {
            Query query = em.createNamedQuery("Sala.findByNombreSala");
            query.setParameter("nombreSala", name);
            list = query.getResultList();
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return list;
    }

    @Override
    public List<Sala> findSalasByIdEspecialidad(Integer id) {
        List<Sala> list = null;
        try {
            Query query = em.createNamedQuery("Sala.findByIdEspecialidad");
            query.setParameter("idEspecialidad", new Especialidad(id));
            list = query.getResultList();
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return list;
    }

    @Override
    public Sala findSalaByName(Object name) {
        Sala sala = null;
        try {
            Query query = em.createNamedQuery("Sala.findByNombreSala");
            query.setParameter("nombreSala", name);
            sala = (Sala) query.getSingleResult();
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return sala;
    }

    @Override
    public Sala findSalaById(Integer id) {
        Sala sala = null;
        try {
            Query query = em.createNamedQuery("Sala.findByIdSala");
            query.setParameter("idSala", id);
            sala = (Sala) query.getSingleResult();
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return sala;
    }

    @Override
    public Integer findIdSalaByName(Object name) {
        Integer id = 0;
        Sala sala;
        try {
            Query query = em.createNamedQuery("Sala.findByNombreSala");
            query.setParameter("nombreSala", name);
            sala = (Sala) query.getSingleResult();
            id = sala.getIdSala();
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return id;
    }

    @Override
    public List<Cama> findCamasByIdSala(Integer id) {
        List<Cama> list = null;
        try {
            Query query = em.createNamedQuery("Cama.findByIdSala");
            query.setParameter("idSala", new Sala(id));
            list = query.getResultList();
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return list;
    }

    @Override
    public Boolean existeSalaEnServicioByNombreServicioIdEspecialidad(Object name, Integer id) {
        Boolean existe = false;
        List<Sala> salas;
        try {
            Query query = em.createQuery("SELECT s FROM Sala s WHERE s.idEspecialidad = :idEspecialidad AND s.nombreSala = :nombreSala");
            query.setParameter("idEspecialidad", new Especialidad(id));
            query.setParameter("nombreSala", name);
            salas = query.getResultList();
            if (!salas.isEmpty()) {
                existe = true;
            }
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return existe;
    }

    @Override
    public boolean findByDescripcionTipoPrevision(TipoPrevision tipoPrevision) {
        try {
            Query query = em.createNamedQuery("TipoPrevision.findByDescripcionTipoPrevision");
            query.setParameter("descripcionTipoPrevision", tipoPrevision.getDescripcionTipoPrevision());
            if (query.getResultList().isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (NoResultException nre) {
            return false;
        }
    }

    @Override
    public List<TipoPrevision> findByIdPrevision(Prevision prevision) {
        Query query = em.createQuery("SELECT t FROM TipoPrevision t JOIN t.idPrevision p WHERE p.idPrevision = :idPrevision");
        query.setParameter("idPrevision", prevision.getIdPrevision());
        return query.getResultList();
    }

    @Override
    public Especialidad findByName(Object nombre) {
        try {
            Query query = em.createNamedQuery("Especialidad.findByNombreEspecialidad");
            query.setParameter("nombreEspecialidad", nombre);
            Especialidad e = (Especialidad) query.getSingleResult();
            if (e == null) {
                return null;
            } else {
                return e;
            }
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return null;
    }

    @Override
    public Integer findIdEspecialidadByName(Object nombre) {
        Integer id = 0;
        try {
            Query query = em.createNamedQuery("Especialidad.findIdByNombreEspecialidad");
            query.setParameter("nombreEspecialidad", nombre);
            id = (Integer) query.getSingleResult();
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return id;
    }

    /**
     * *
     *
     * @param nombreEntidad = Nombre de la entridad donde buscar el dato (puede
     * verse en la queryname antes de .)
     * @param nombreQuery = Nombre de la query donde buscar el dato (puede verse
     * en la queryname despues del .)
     * @param nombreParametro = parametro del where en la queryname
     * @param dato
     * @return
     */
    @Override
    public Integer IdByDato(String nombreEntidad, String nombreQuery, String nombreParametro, Object dato) {
        Integer id = 0;
        try {
            String aux = nombreEntidad + "." + nombreQuery;
            Query query = em.createNamedQuery(aux);
            query.setParameter(nombreParametro, dato);
            id = (Integer) query.getSingleResult();
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return id;
    }

    @Override
    public boolean FindByDato(String nombreEntidad, String nombreQuery, String nombreParametro, Object dato) {
        boolean aux = false;
        try {
            String aux2 = nombreEntidad + "." + nombreQuery;
            Query query = em.createNamedQuery(aux2);
            query.setParameter(nombreParametro, dato);
            if (query.getResultList().isEmpty()) {
                aux = true;
            }
        } catch (NoResultException nre) {
            aux = false;
        }
        return aux;
    }

    @Override
    public Integer IdByDato2(Object dato) {
        Integer id = 0;
        Query query = em.createNamedQuery("Rol.findByDescripcionRol");
        query.setParameter("descripcionRol", dato);
        if (!query.getResultList().isEmpty()) {
            Rol Aux = (Rol) query.getResultList().get(0);
            id = Aux.getIdRol();
        }
        return id;
    }

    @Override
    public boolean findByRutVd(Integer rut, String dv) {
        boolean aux = false;
        try {
            Query query = em.createNamedQuery("UsuarioSistema.findByRutByVDUsuario");
            query.setParameter("rutUsuario", rut);
            query.setParameter("dvUsuario", dv);
            if (query.getResultList().isEmpty()) {
                aux = true;
            }
        } catch (Exception e) {
            //Ignore this because as per your logic this is ok!
        }
        return aux;
    }

    @Override
    public Boolean existeCamaEnSala(BigInteger numeroCama, Sala sala) {
        Boolean existe = false;
        try {
            Query query = em.createQuery("SELECT c FROM Cama c WHERE c.idSala = :idSala AND c.numeroCama = :numeroCama");
            query.setParameter("numeroCama", numeroCama);
            query.setParameter("idSala", sala);
            if (!query.getResultList().isEmpty()) {
                existe = true;
            }
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
            System.out.println(nre);
        }
        return existe;
    }

    @Override
    public Cama findCamaById(Integer idCama) {
        try {
            Query query = em.createNamedQuery("Cama.findByIdCama");
            query.setParameter("idCama", idCama);
            Cama c = (Cama) query.getSingleResult();
            if (c == null) {
                return null;
            } else {
                return c;
            }
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return null;
    }

    @Override
    public Cama findCamaByNumeroCama(BigInteger numeroCama) {
        try {
            Query query = em.createNamedQuery("Cama.findByNumeroCama");
            query.setParameter("numeroCama", numeroCama);
            Cama c = (Cama) query.getSingleResult();
            if (c == null) {
                return null;
            } else {
                return c;
            }
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return null;
    }

    @Override
    public Cama findCamaByID(BigInteger numeroCama) {
        try {
            Query query = em.createNamedQuery("Cama.findByIdCama");
            query.setParameter("idCama", numeroCama);
            Cama c = (Cama) query.getSingleResult();
            if (c == null) {
                return null;
            } else {
                return c;
            }
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return null;
    }

    @Override
    public List<AsignacionCama> findAsigCamasEnCurso() {
        List<AsignacionCama> lista = null;
        try {
            Query query = em.createQuery("SELECT ac FROM AsignacionCama AS ac WHERE ac.fechaEgreso IS NULL");
            lista = query.getResultList();
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return lista;
    }

    @Override
    public Paciente findPacienteByRun(Integer run) {
        List<Paciente> lista;
        Paciente p = null;
        try {
            Query query = em.createNamedQuery("Paciente.findByRunPaciente");
            query.setParameter("runPaciente", run);
            lista = query.getResultList();
            for (Iterator iterador = lista.listIterator(); iterador.hasNext();) {
                p = (Paciente) iterador.next();
            }
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return p;
    }

    /**
     * *
     * Metodo que permite buscar si el rut del paciente existe o no en nuestro
     * sistema
     *
     * @param rut resive el Rut (paciente.getRunPaciente)
     * @return retorna un true si existe el paciente, retorna el false si no
     * encuentra el paciente
     */
    @Override
    public boolean findByRutPaciente(int rut) {
        Query query = em.createQuery("SELECT p FROM Paciente p WHERE p.runPaciente = :runPaciente");
        query.setParameter("runPaciente", rut);
        return query.getResultList().isEmpty();
    }

    /**
     * *
     * Metodo que busca si el Número de ficha ingresado se encuentra registrado
     * en el sistema
     *
     * @param nroFicha valor String ingresado por el usuario
     * @return retorna true si se encuentra el nro de ficha en el sistema, falso
     * si no lo encuentra
     */
    @Override
    public boolean findByNroFicha(String nroFicha) {
        Query query = em.createQuery("SELECT p FROM Paciente p WHERE p.numeroFicha = :numeroFicha");
        query.setParameter("numeroFicha", nroFicha);
        return query.getResultList().isEmpty();
    }

    @Override
    public boolean findByRutUsuario(int rut) {
        Query query = em.createQuery("SELECT u FROM UsuarioSistema u WHERE u.rutUsuario = :rutUsuario");
        query.setParameter("rutUsuario", rut);
        if (query.getResultList().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean findByEmailUsuario(String email) {
        Query query = em.createNamedQuery("UsuarioSistema.findByCorreoElectronico");
        query.setParameter("correoElectronico", email);
        if (query.getResultList().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * *
     * Metodo que me permite listar las salas activas de mi especialidad
     *
     * @param especialidad Object tipo Especialidad
     * @return Retorna una lista de Salas Activas
     */
    @Override
    public List<Sala> listSalasActivasByEspecialidad(Especialidad especialidad) {
        Query query = em.createQuery("SELECT s FROM Sala s JOIN s.idEspecialidad e  WHERE e.idEspecialidad = :idEspecialidad AND s.indActivo=:indActivo");
        query.setParameter("idEspecialidad", especialidad.getIdEspecialidad());
        query.setParameter("indActivo", EstadoObjeto.ACTIVO);
        return query.getResultList();
    }

    /**
     * *
     * metodo que me permite listar las camas activas con estado "Desocupado"
     *
     * @param sala Object tipo Sala
     * @return retorna una lista de Camas Desocupadas Activas
     */
    @Override
    public List<Cama> listCamasActivasDesocupadasBySala(Sala sala) {
        Query query = em.createQuery("SELECT c from Cama c JOIN c.idSala s JOIN c.idEstadoCama e "
                + "WHERE s.idSala=:idSala AND c.indActivo=:indActivo AND e.idEstadoCama=2 ");
        query.setParameter("idSala", sala.getIdSala());
        query.setParameter("indActivo", EstadoObjeto.ACTIVO);
        return query.getResultList();
    }

    /**
     * *
     * Metodo que listas camas, dependiendo de la sala y del tipo de cama
     *
     * @param idSala Objeto Sala
     * @param idTipoCama Objeto TipoCama
     * @return Retorna una lista de camas
     */
    @Override
    public List<Cama> listCamasActivasDesocupadasByEstadoCama(Sala idSala, TipoCama idTipoCama) {
        Query query = em.createQuery("SELECT c from Cama c JOIN c.idSala s JOIN c.idEstadoCama e "
                + "JOIN c.idTipoCama t WHERE s.idSala=:idSala AND c.indActivo=:indActivo "
                + "AND t.idTipoCama=:idTipoCama  AND e.idEstadoCama=2 ");
        query.setParameter("idSala", idSala.getIdSala());
        query.setParameter("indActivo", EstadoObjeto.ACTIVO);
        query.setParameter("idTipoCama", idTipoCama.getIdTipoCama());
        return query.getResultList();
    }

    @Override
    public List<EstadoPaciente> listEstadoPacienteByTipoCama(TipoCama idTipoCama) {
        Query query = em.createQuery("SELECT ep from EstadoPaciente ep WHERE ep.idTipoCama=:idTipoCama");
        query.setParameter("idTipoCama", idTipoCama.getIdTipoCama());
        return query.getResultList();
    }

    @Override
    public EstadoPaciente finEstadoById(Integer id) {
        Query q = em.createNamedQuery("EstadoPaciente.findByIdEstadoPaciente");
        q.setParameter("idEstadoPaciente", id);
        if (!q.getResultList().isEmpty()) {
            EstadoPaciente estado = (EstadoPaciente) q.getResultList().get(0);
            return estado;
        }
        return null;
    }

    @Override
    public boolean pacienteHospitalizado(Paciente paciente) {
        Boolean encontrado = false;
        Query query = em.createQuery("SELECT a.idPaciente FROM AsignacionCama a WHERE a.idPaciente=:idPaciente AND a.fechaEgreso IS NULL");
        query.setParameter("idPaciente", new Paciente(paciente.getIdPaciente()));
        List<Paciente> lista = query.getResultList();
        if (lista.isEmpty()) {
            encontrado = true;
        }
        return encontrado;
    }

    @Override
    public boolean camasDisponiblesEnEspecialidad(Especialidad especialidad) {
        Boolean encontrado = false;
        Query query = em.createQuery("SELECT c FROM Cama c JOIN c.idSala s JOIN s.idEspecialidad e JOIN c.idEstadoCama ec WHERE s.idEspecialidad=:idEspecialidad AND c.indActivo=:indActivo AND s.indActivo=:indActivo AND e.indActivo=:indActivo AND ec.idEstadoCama=2");
        query.setParameter("idEspecialidad", new Especialidad(especialidad.getIdEspecialidad()));
        query.setParameter("indActivo", EstadoObjeto.ACTIVO);
        List<Cama> lista = query.getResultList();
        if (!lista.isEmpty()) {
            encontrado = true;
        }
        return encontrado;
    }

    @Override
    public Paciente findPacienteByNroFicha(String nroFicha) {
        List<Paciente> lista;
        Paciente p = null;
        try {
            Query query = em.createNamedQuery("Paciente.findByNumeroFicha");
            query.setParameter("numeroFicha", nroFicha);
            lista = query.getResultList();
            for (Iterator iterador = lista.listIterator(); iterador.hasNext();) {
                p = (Paciente) iterador.next();
            }
        } catch (NoResultException nre) {
            //Ignore this because as per your logic this is ok!
        }
        return p;
    }

    @Override
    public boolean BuscarNombreMenu(String nombre) {
        Query q = em.createQuery("SELECT c from Menu c WHERE c.nombre = :nombre");
        q.setParameter("nombre", nombre);
        return q.getResultList().isEmpty();
    }

    @Override
    public List<Opcion> findByIdMenu(Menu menu) {
        Query q = em.createQuery("SELECT o FROM Opcion o JOIN O.idMenu AS M WHERE M.idMenu = :idMenu");
        q.setParameter("idMenu", menu.getIdMenu());
        return q.getResultList();
    }

    @Override
    public List<RolOpcion> opcionesByRol(Rol rol) {
        Query query = em.createQuery("SELECT ro FROM RolOpcion ro WHERE ro.idRol=:idRol");
        query.setParameter("idRol", new Rol(rol.getIdRol()));
        return query.getResultList();
    }

    @Override
    public List<Opcion> findAllOpciones() {
        Query query = em.createQuery("SELECT r FROM Rol r WHERE r.indActivo=:indActivo");
        query.setParameter("indActivo", EstadoObjeto.ACTIVO);
        return query.getResultList();
    }

    @Override
    public List<Opcion> findOpcionesNotUssedByRol(Rol rol) {
        Query query = em.createNativeQuery("select o.* from opcion o except (select op.* from opcion op inner join rol_opcion ro on op.id_opcion=ro.id_opcion where ro.id_rol=:idRol)", Opcion.class);
        query.setParameter("idRol", new Rol(rol.getIdRol()));
        return query.getResultList();
    }

    @Override
    public void deleteRolOpcionByIdRol(Rol rol) {
        Query query = em.createQuery("DELETE FROM RolOpcion ro WHERE ro.idRol=:idRol ");
        query.setParameter("idRol", new Rol(rol.getIdRol()));
        query.executeUpdate();
    }

    @Override
    public Especialidad getEspecialidad(int id) {
        Especialidad aux2 = new Especialidad();
        Query q = em.createNamedQuery("Especialidad.findByIdEspecialidad");
        q.setParameter("idEspecialidad", id);
        if (q.getSingleResult() != null) {
            aux2 = (Especialidad) q.getSingleResult();
        }
        return aux2;
    }

    @Override
    public UsuarioSistema findByLoginUsuario(Integer rut) {
        UsuarioSistema aux = new UsuarioSistema();
        Query q = em.createQuery("SELECT u FROM UsuarioSistema u WHERE u.rutUsuario = :rutUsuario");
        q.setParameter("rutUsuario", rut);
        //q.setParameter("dvUsuario", dv);
        if (q.getSingleResult() != null) {
            aux = (UsuarioSistema) q.getSingleResult();
        }
        return aux;
    }

    @Override
    public List<UsuarioOpcion> getOpcionesUsuario(Integer id) {
        Query q = em.createQuery("SELECT o FROM UsuarioOpcion o JOIN O.idOpcion as op WHERE o.idUsuario.idUsuario = :idUsuario");
        q.setParameter("idUsuario", id);
        List<UsuarioOpcion> aux;
        aux = q.getResultList();
        return aux;
    }

    public UsuarioOpcion getOpciones(Integer id) {
        Query q = em.createQuery("SELECT o FROM UsuarioOpcion o WHERE o.idUsuario = :idUsuario");
        q.setParameter("idUsuario", id);
        UsuarioOpcion aux = new UsuarioOpcion();
        aux = (UsuarioOpcion) q.getResultList();
        return aux;
    }

    @Override
    public boolean trasladosPendientes(Paciente paciente) {
        Query query = em.createQuery("SELECT t FROM TrasladoTemporal t WHERE t.idPaciente.idPaciente=:idPaciente AND t.estado=:estado");
        query.setParameter("idPaciente", paciente.getIdPaciente());
        query.setParameter("estado", 'P');
        List<Cama> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public List<IngresoHospitalizados> listIngresos() {
        Query query = em.createQuery("SELECT it FROM IngresoHospitalizados it WHERE it.egreso = 0");
        if (!query.getResultList().isEmpty()) {
            List<IngresoHospitalizados> lista = query.getResultList();
            return lista;
        } else {
            return null;
        }
    }

    @Override
    public IngresoHospitalizados obtenerIngresoHospitalizado(Paciente paciente) {
        IngresoHospitalizados ih = null;
        Query query = em.createQuery("SELECT ih FROM IngresoHospitalizados ih WHERE ih.idPaciente.idPaciente = :idPaciente and ih.fechaIngreso = "
                + "(SELECT MAX(ihh.fechaIngreso) FROM IngresoHospitalizados ihh where ih.idPaciente.idPaciente = ihh.idPaciente.idPaciente )");
        query.setParameter("idPaciente", paciente.getIdPaciente());
        List<IngresoHospitalizados> listIngHos = query.getResultList();
        if (!listIngHos.isEmpty()) {
            ih = listIngHos.get(0);
        }
        return ih;
    }

    @Override
    public EgresoHospitalizados obtenerEgresoHospitalizado(Paciente paciente) {
        EgresoHospitalizados eh = null;
        Query query = em.createQuery("SELECT eh FROM EgresoHospitalizados eh WHERE eh.idPaciente.idPaciente = :idPaciente AND eh.fechaEgreso = "
                + "(SELECT MAX(egg.fechaEgreso) FROM EgresoHospitalizados egg where egg.idPaciente.idPaciente = eh.idPaciente.idPaciente)");
        query.setParameter("idPaciente", paciente.getIdPaciente());
        List<EgresoHospitalizados> listIngHos = query.getResultList();
        if (!listIngHos.isEmpty()) {
            eh = listIngHos.get(0);
        }
        return eh;
    }

    @Override
    public List<AsignacionCama> listCamasAsignadasByPaciente(Paciente paciente) {
        Integer year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        Integer month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        Integer day = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        Date date1 = new java.sql.Date(year, month, day);
        List<AsignacionCama> asignacionCamas = null;
        //PRIMERO OBTENTGO EL ULTIMO INGRESO DEL PACIENTE
        IngresoHospitalizados ih = this.obtenerIngresoHospitalizado(paciente);
        //SEGUNDO OBTENGO EL ULTIMO EGRESO DEL PACIENTE 
        EgresoHospitalizados eh = this.obtenerEgresoHospitalizado(paciente);
        //TERCERO VALIDO QUE LA FECHA DE EGRESO SEA MAYOR A LA DE INGRESO, DE LO CONTRARIO 
        if (ih == null && eh == null) {
            System.out.println("ambos son nulos");
        } else if (ih == null) {
            System.out.println("Ingreso es Nulo");
        } else if (eh == null) {
            //si el egreso es nulo se debe comparar con la fecha de hoy.
            System.out.println("Egreso es Nulo: " + "SELECT ac from  AsignacionCama ac WHERE ac.idPaciente.idPaciente=" + paciente.getIdPaciente() + " AND "
                    + "ac.fechaAsignacion>=" + ih.getFechaIngreso() + " AND ac.fechaAsignacion<=" + date1);
            Query query2 = em.createQuery("SELECT ac from  AsignacionCama ac WHERE ac.idPaciente.idPaciente = :idPaciente AND "
                    + "ac.fechaAsignacion>=:fechaAsignacion AND ac.fechaAsignacion <= :fechaEgreso");
            query2.setParameter("idPaciente", paciente.getIdPaciente());
            query2.setParameter("fechaAsignacion", ih.getFechaIngreso());
            query2.setParameter("fechaEgreso", date1);
            asignacionCamas = query2.getResultList();

        } else {
            //aqui pregunto si la fecha de egreso es mayor a la de ingreso
            if (eh.getFechaEgreso().before(ih.getFechaIngreso())) {
                System.out.println("fecha egreso es menor");
                //Si la fecha es menor significa que la fecha no corresponde a ese ingreso 
                //hospitalario y se debera traer los datos hasta la fecha de hoy 
                Query query2 = em.createQuery("SELECT ac from  AsignacionCama ac WHERE ac.idPaciente.idPaciente=:idPaciente AND "
                        + "ac.fechaAsignacion>=:fechaAsignacion AND ac.fechaAsignacion<=:fechaEgreso");
                query2.setParameter("idPaciente", paciente.getIdPaciente());
                query2.setParameter("fechaAsignacion", ih.getFechaIngreso());
                query2.setParameter("fechaEgreso", date1);
                asignacionCamas = query2.getResultList();
            } else {
                System.out.println("fecha egreso es mayor");
                //Como la fecha es mayor se debera traer todas las asignaciones de cama
                //desde la fecha de ingreso, hasta la fecha de egreso
                Query query2 = em.createQuery("SELECT ac from  AsignacionCama ac WHERE ac.idPaciente.idPaciente=:idPaciente AND "
                        + "ac.fechaAsignacion>=:fechaAsignacion AND ac.fechaAsignacion<=:fechaEgreso");
                query2.setParameter("idPaciente", paciente.getIdPaciente());
                query2.setParameter("fechaAsignacion", ih.getFechaIngreso());
                query2.setParameter("fechaEgreso", eh.getFechaEgreso());
                asignacionCamas = query2.getResultList();
            }
        }

        return asignacionCamas;
    }

    @Override
    public boolean existeCierreDeIngreso(Paciente paciente) {
        boolean aux;
        //Cargo Datos de ingreso y egreso segun paciente fechas
        IngresoHospitalizados ih = this.obtenerIngresoHospitalizado(paciente);
        EgresoHospitalizados eh = this.obtenerEgresoHospitalizado(paciente);
        //TERCERO VALIDO QUE LA FECHA DE EGRESO SEA MAYOR A LA DE INGRESO, DE LO CONTRARIO 
        if (ih == null && eh == null) {
            aux = true;
        } else if (ih == null) {
            aux = true;
        } else if (eh == null) {
            aux = false;
        } else {
            //aqui pregunto si la fecha de ingreso es mayor a la de egreso
            aux = ih.getFechaIngreso().before(eh.getFechaEgreso());
        }

        return aux;
    }

    @Override
    public boolean checkMenu(Integer id, Integer id2) {
        Query q = em.createQuery("SELECT o FROM UsuarioOpcion o JOIN O.idOpcion as op WHERE o.idUsuario.idUsuario = :idUsuario AND op.idMenu.idMenu = :idMenu");
        q.setParameter("idUsuario", id);
        q.setParameter("idMenu", id2);
        if (!q.getResultList().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    //Metodos de traslados temporales

    @Override
    public List<TrasladoTemporal> getTrasladosTemporalesByIdEsp(Integer id) {
        Query q = em.createQuery("SELECT t FROM TrasladoTemporal t WHERE t.idEspecialidadTraslado = :idEspecialidad");
        q.setParameter("idEspecialidad", id);
        System.out.println("id de la Especialidad : " + id);
        if (q.getResultList().isEmpty()) {
            return new ArrayList();
        } else {
            return q.getResultList();
        }

    }

    @Override
    public int totalTrasladosPendientes(Integer id) {
        Query q = em.createQuery("SELECT t FROM TrasladoTemporal t WHERE t.idEspecialidadTraslado = :idEspecialidad and t.estado = :estado");
        q.setParameter("idEspecialidad", id);
        q.setParameter("estado", 'P');
        return q.getResultList().size();

    }

    @Override
    public List<Opcion> findOpcionesNotUssedByUsser(UsuarioSistema usuario) {
        Query query = em.createNativeQuery("select o.* from opcion o except (select op.* from opcion op inner join usuario_opcion uo on op.id_opcion=uo.id_opcion where uo.id_usuario=:idUsuario)", Opcion.class);
        query.setParameter("idUsuario", new UsuarioSistema(usuario.getIdUsuario()));
        return query.getResultList();
    }

    @Override
    public void deleteUsuarioOpcionByIdUsser(UsuarioSistema idUsuario) {
        Query query = em.createQuery("DELETE FROM UsuarioOpcion uo WHERE uo.idUsuario=:idUsuario");
        query.setParameter("idUsuario", new UsuarioSistema(idUsuario.getIdUsuario()));
        query.executeUpdate();
    }

    @Override
    public List<UsuarioOpcion> opcionesByUsser(UsuarioSistema usuario) {
        Query query = em.createQuery("SELECT uo FROM UsuarioOpcion uo WHERE uo.idUsuario=:idUsuario");
        query.setParameter("idUsuario", new UsuarioSistema(usuario.getIdUsuario()));
        return query.getResultList();
    }

    @Override
    public RolOpcion findByIdRolOpcion(Integer Id) {
        RolOpcion ro = new RolOpcion();
        Query query = em.createNamedQuery("RolOpcion.findByIdRolOpcion");
        query.setParameter("idRolOpcion", Id);
        ro = (RolOpcion) query.getSingleResult();
        return ro;
    }

    @Override
    public UsuarioOpcion findByIdUsuarioOpcion(Integer Id) {
        UsuarioOpcion uo = new UsuarioOpcion();
        Query query = em.createNamedQuery("UsuarioOpcion.findByIdUsuarioOpcion");
        query.setParameter("idUsuarioOpcion", Id);
        uo = (UsuarioOpcion) query.getSingleResult();
        return uo;
    }

    @Override
    public boolean checkParentesco(String dato) {
        Query q = em.createNamedQuery("Parentesco.findByDescripcionParentesco");
        q.setParameter("descripcionParentesco", dato);
        return q.getResultList().isEmpty();
    }

    @Override
    public boolean checkApoderado(String dato) {
        Query q = em.createNamedQuery("Apoderado.findByRunApoderado");
        Integer Aux = Integer.parseInt(dato);
        q.setParameter("runApoderado", Aux);
        return q.getResultList().isEmpty();
    }

    @Override
    public Apoderado findApoderadoById(Integer id) {
        Query q = em.createNamedQuery("Apoderado.findByIdApoderado");
        q.setParameter("idApoderado", id);
        if (q.getSingleResult() != null) {
            return (Apoderado) q.getSingleResult();
        } else {
            return null;
        }
    }

    @Override
    public Integer findIdAsignacionMaxPaciente(Integer id) {
        Query q = em.createQuery("SELECT MAX(a.idAsignacion) FROM AsignacionCama a WHERE a.idPaciente.idPaciente = :idPaciente ");
        q.setParameter("idPaciente", id);
        if (q.getSingleResult() != null) {
            return (Integer) q.getSingleResult();
        } else {
            return 0;
        }
    }

    @Override
    public AsignacionCama findUltimaAsignacionDelPaciente(Integer id) {
        Integer idAsig = findIdAsignacionMaxPaciente(id);
        Query q = em.createQuery("SELECT ac FROM AsignacionCama AS ac WHERE ac.idAsignacion = :idAsignacion");
        q.setParameter("idAsignacion", idAsig);
        if (q.getSingleResult() != null) {
            return (AsignacionCama) q.getSingleResult();
        } else {
            return null;
        }
    }

    @Override
    public EstadoCama findEstadoCamaById(Integer id) {
        Query q = em.createQuery("SELECT e FROM EstadoCama e WHERE e.idEstadoCama = :idestado");
        q.setParameter("idestado", id);
        if (q.getSingleResult() != null) {
            return (EstadoCama) q.getSingleResult();
        } else {
            return null;
        }
    }

    public Integer getIdTrasladoTemporalMax(Integer id) {
        Query q = em.createQuery("SELECT MAX(t.idTrasladoTemporal) FROM TrasladoTemporal t WHERE t.idPaciente.idPaciente = :idPaciente");
        q.setParameter("idPaciente", id);
        if (q.getSingleResult() == null) {
            return 0;
        } else {
            return (Integer) q.getSingleResult();
        }
    }

    @Override
    public TrasladoTemporal findUltimoTrasladoTemporalByIdPaciente(Integer id) {
        Integer Aux = getIdTrasladoTemporalMax(id);
        if (Aux == 0) {
            return null;
        } else {
            Query q = em.createQuery("SELECT t FROM TrasladoTemporal t WHERE t.idTrasladoTemporal = :idMax");
            q.setParameter("idMax", Aux);
            if (q.getSingleResult() != null) {
                TrasladoTemporal aux = (TrasladoTemporal) q.getSingleResult();
                return (TrasladoTemporal) q.getSingleResult();
            } else {
                return null;
            }
        }
    }

    @Override
    public TipoEgreso findTipoEgresoByID(Integer id) {
        Query q = em.createNamedQuery("TipoEgreso.findByIdTipoEgreso");
        q.setParameter("idTipoEgreso", id);
        if (q.getSingleResult() != null) {
            return (TipoEgreso) q.getSingleResult();
        } else {
            return null;
        }
    }

    @Override
    public AsignacionCama findAsignacionCamaByPacienteFENull(Paciente paciente) {
        Query query = em.createQuery("SELECT a FROM AsignacionCama a WHERE a.idPaciente=:idPaciente AND a.fechaEgreso IS NULL");
        query.setParameter("idPaciente", new Paciente(paciente.getIdPaciente()));
        if (query.getSingleResult() != null) {
            return (AsignacionCama) query.getSingleResult();
        } else {
            return null;
        }
    }
    @Override
    public AsignacionCama findAsignacionCamaByPacienteFENull2(Paciente paciente) {
      Query q = em.createQuery("SELECT ac FROM AsignacionCama ac WHERE ac.fechaEgreso IS NULL AND ac.idPaciente.idPaciente = :idPaciente");
        System.out.println("id paciente registrado" + paciente.getIdPaciente());
        q.setParameter("idPaciente", paciente.getIdPaciente());
        if (!q.getResultList().isEmpty()) {
            System.out.println("cantidad de datos "+q.getResultList().size());
            AsignacionCama aux = (AsignacionCama) q.getSingleResult();
            System.out.println("id asig "+aux.getIdAsignacion());
            System.out.println("otro dato "+aux.getIdEstadoPaciente().getDescripcionEstadoPaciente());
            return aux;
        } else {
           return null;
        }
    }

    //Get ubicacion Actual por String
    @Override
    public String getUbicacionActual(Integer id) {
        Query q = em.createQuery("SELECT ac FROM AsignacionCama ac WHERE ac.fechaEgreso IS NULL AND ac.idPaciente.idPaciente = :idPaciente");
        q.setParameter("idPaciente", id);
        if (!q.getResultList().isEmpty()) {
            AsignacionCama aux = (AsignacionCama) q.getResultList().get(0);
            return "Especialidad: " + aux.getIdCama().getIdSala().getIdEspecialidad().getNombreEspecialidad()
                    + " Sala: " + aux.getIdCama().getIdSala().getNombreSala() + " Cama: " + aux.getIdCama().getNumeroCama();
        } else {
            return "NoAsignada";
        }
    }

    @Override
    public List<TipoEgreso> listaDeEgresosHospital() {
        Query q = em.createQuery("SELECT te FROM TipoEgreso te WHERE te.idTipoEgreso <> 5");
        return (List<TipoEgreso>) q.getResultList();
    }

    @Override
    public Apoderado getApoderado(String dato) {
        Query q = em.createNamedQuery("Apoderado.findByRunApoderado");
        Integer Aux = Integer.parseInt(dato);
        q.setParameter("runApoderado", Aux);
        if (q.getSingleResult() != null) {
            return (Apoderado) q.getSingleResult();
        } else {
            return null;
        }
    }

    @Override
    public Parentesco getParentescoByID(Integer id) {
        Query q = em.createQuery("SELECT p FROM Parentesco p WHERE p.idParentesco = :idParentesco");
        q.setParameter("idParentesco", id);
        if (q.getSingleResult() != null) {
            return (Parentesco) q.getSingleResult();
        } else {
            return null;
        }
    }

    @Override
    public List<AsignacionCama> listAllHospitalizados() {
        Query query = em.createQuery("SELECT ac FROM AsignacionCama ac WHERE ac.fechaEgreso IS NULL");
        return query.getResultList();
    }

    @Override
    public List<AsignacionCama> listHospitalizadosByEspecialidad(Especialidad especialidad) {
        Query query = em.createQuery("SELECT ac FROM AsignacionCama ac WHERE ac.fechaEgreso IS NULL AND ac.idCama.idSala.idEspecialidad.idEspecialidad=:especialidad");
        query.setParameter("especialidad", especialidad.getIdEspecialidad());
        return query.getResultList();
    }

    @Override
    public List<AsignacionCama> listAllEctopicos() {
        Query query = em.createQuery("SELECT ac FROM AsignacionCama ac WHERE ac.fechaEgreso IS NULL AND ac.ectopico <> '0'");
        return query.getResultList();
    }

    @Override
    public List<AsignacionCama> listEctopicosByEspecialidad(Especialidad especialidad) {
        Query query = em.createQuery("SELECT ac FROM AsignacionCama ac WHERE ac.fechaEgreso IS NULL AND ac.idCama.idSala.idEspecialidad.idEspecialidad=:especialidad AND ac.ectopico <> '0'");
        query.setParameter("especialidad", especialidad.getIdEspecialidad());
        return query.getResultList();
    }

    //Reportes de Merica :'(
    @Override
    public List<IngresoHospitalizados> TodosLosIngresoDelPaciente(Integer id) {
        Query q = em.createQuery("SELECT ih FROM IngresoHospitalizados ih WHERE ih.idPaciente.idPaciente = :id ORDER BY IH.idIngreso ASC");
        q.setParameter("id", id);
        return q.getResultList();
    }

    @Override
    public List<EgresoHospitalizados> TodosLosEgresosDelPaciente(Integer id) {
        Query q = em.createQuery("SELECT eh FROM EgresoHospitalizados eh WHERE eh.idPaciente.idPaciente = :id ORDER BY eh.idEgreso ASC");
        q.setParameter("id", id);
        return q.getResultList();
    }

    @Override
    public ArrayList<AsignacionCama> getAsignacionesCamasPorFechasYIdPaciente(Date ih, Date eh, Integer id) {
        Integer year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        Integer month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        Integer day = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        Date date1 = new java.sql.Date(year, month, day);
        ArrayList<AsignacionCama> asignacionCamas = new ArrayList<AsignacionCama>();
        //PRIMERO OBTENTGO EL ULTIMO INGRESO DEL PACIENTE
        //SEGUNDO OBTENGO EL ULTIMO EGRESO DEL PACIENTE 
        //TERCERO VALIDO QUE LA FECHA DE EGRESO SEA MAYOR A LA DE INGRESO, DE LO CONTRARIO 
        if (ih == null && eh == null) {
            System.out.println("ambos son nulos");
        } else if (ih == null) {
            System.out.println("Ingreso es Nulo");
        } else if (eh == null) {
            //si el egreso es nulo se debe comparar con la fecha de hoy.
            System.out.println("Egreso es Nulo: " + "SELECT ac from  AsignacionCama ac WHERE ac.idPaciente.idPaciente=" + id + " AND "
                    + "ac.fechaAsignacion>=" + ih + " AND ac.fechaAsignacion<=" + date1);
            Query query2 = em.createQuery("SELECT ac from  AsignacionCama ac WHERE ac.idPaciente.idPaciente = :idPaciente AND "
                    + "ac.fechaAsignacion>=:fechaAsignacion AND ac.fechaAsignacion <= :fechaEgreso");
            query2.setParameter("idPaciente", id);
            query2.setParameter("fechaAsignacion", ih);
            query2.setParameter("fechaEgreso", date1);
            asignacionCamas = (ArrayList<AsignacionCama>) query2.getResultList();

        } else {
            //aqui pregunto si la fecha de egreso es mayor a la de ingreso
            if (eh.before(ih)) {
                System.out.println("fecha egreso es menor");
                //Si la fecha es menor significa que la fecha no corresponde a ese ingreso 
                //hospitalario y se debera traer los datos hasta la fecha de hoy 
                Query query2 = em.createQuery("SELECT ac from  AsignacionCama ac WHERE ac.idPaciente.idPaciente=:idPaciente AND "
                        + "ac.fechaAsignacion>=:fechaAsignacion AND ac.fechaAsignacion<=:fechaEgreso");
                query2.setParameter("idPaciente", id);
                query2.setParameter("fechaAsignacion", ih);
                query2.setParameter("fechaEgreso", date1);
                asignacionCamas = (ArrayList<AsignacionCama>) query2.getResultList();
            } else {
                System.out.println("fecha egreso es mayor");
                //Como la fecha es mayor se debera traer todas las asignaciones de cama
                //desde la fecha de ingreso, hasta la fecha de egreso
                Query query2 = em.createQuery("SELECT ac from  AsignacionCama ac WHERE ac.idPaciente.idPaciente=:idPaciente AND "
                        + "ac.fechaAsignacion>=:fechaAsignacion AND ac.fechaAsignacion<=:fechaEgreso");
                query2.setParameter("idPaciente", id);
                query2.setParameter("fechaAsignacion", ih);
                query2.setParameter("fechaEgreso", eh);
                asignacionCamas = (ArrayList<AsignacionCama>) query2.getResultList();
            }
        }

        return asignacionCamas;
    }

    //Reporte de Egresados
    //Lista de ID de egresos para obtenerlos dentro de una fechas
    @Override
    public List<Integer> getListaIdPacientesRangoFechas(Date fecha1, Date fecha2) {
        Query q = em.createQuery("SELECT DISTINCT eh.idPaciente FROM EgresoHospitalizados eh where eh.fechaEgreso BETWEEN :fecha1 AND :fecha2");
        q.setParameter("fecha1", fecha1);
        q.setParameter("fecha2", fecha2);
        return q.getResultList();
    }
    @Override
    public ArrayList<EgresoHospitalizados> getEgresos(Date fecha1, Date fecha2) {
        Query q = em.createNativeQuery("SELECT distinct id_paciente from egreso_hospitalizados where fecha_egreso between :fecha1 and :fecha2");
        q.setParameter("fecha1", fecha1);
        q.setParameter("fecha2", fecha2);
        List<Integer> lista1 = q.getResultList();
        ArrayList<EgresoHospitalizados> eh = new ArrayList<EgresoHospitalizados>();
        for (Integer idPaciente : lista1) {
            Query q2 = em.createNativeQuery("select max(id_egreso) from egreso_hospitalizados where id_paciente = "+idPaciente);
            if (!q2.getResultList().isEmpty()) {
                Integer id = (Integer) q2.getSingleResult();
                Query q3 = em.createQuery("SELECT eh FROM EgresoHospitalizados eh WHERE eh.idEgreso = :idEgreso");
                q3.setParameter("idEgreso", id);
                EgresoHospitalizados eh2 = (EgresoHospitalizados) q3.getSingleResult();
                eh.add(eh2);
            }
        }
        return eh;
    }

    @Override
    public boolean existeNN(Integer correlativoNN) {
        Query q = em.createQuery("Select p From Paciente p WHERE p.runPaciente=:correlativoNN");
        q.setParameter("correlativoNN", correlativoNN);
        return q.getResultList().isEmpty();
    }

}
