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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Edwin_Guaman
 */
@Local
public interface BussinessFacadeLocal {

  public boolean findByDescripcionPrevision(Prevision prevision);
  public boolean findByDescripcionCargo(Cargo cargo);
  public Especialidad findEspecialidadByName(Object nombre);
  public Integer findIdEspecialidadByName(Object name);
  public List<Especialidad> findByIndActivo(Short indActivo);
  public Boolean existeSalaEnServicioByNombreServicioIdEspecialidad(Object name, Integer id);
  public Integer findIdSalaByName(Object name);
  public List<Sala> findSalasByName(Object name);
  public Sala findSalaByName(Object name);
  public List<Sala> findSalasByIdEspecialidad(Integer id);
  public List<Cama> findCamasByIdSala(Integer id);
  public Integer IdByDato(String nombreEntidad, String nombreQuery, String nombreParametro, Object dato);
  public boolean FindByDato(String nombreEntidad, String nombreQuery, String nombreParametro, Object dato);
  public Integer IdByDato2(Object dato);
  public Especialidad findByName(Object nombre);
  public Integer IdByName(Object name);
  public boolean findByDescripcionTipoPrevision(TipoPrevision tipoPrevision);
  public List<TipoPrevision> findByIdPrevision(Prevision prevision);
  //Metodos relacionados a las Camas
  public Boolean existeCamaEnSala(BigInteger numeroCama, Sala sala);
  public Cama findCamaById(Integer idCama);
  public Cama findCamaByNumeroCama(BigInteger numeroCama);
  //Metodos desarrollados por andres
  public boolean findByRutVd(Integer rut, String dv);
  /**
   * *
   * Metodo que permite buscar si el rut del usuario existe o no en nuestro
   * sistema
   *
   * @param rut resive el Rut (Usuario.getRutUsuario)
   * @return retorna un true si existe el paciente, retorna el false si no
   * encuentra el paciente
   */
  public boolean findByRutUsuario(int rut);
  public boolean findByEmailUsuario(String email);
  //Metodos Relacionados a Asignacion de Camas
  public List<AsignacionCama> findAsigCamasEnCurso();
  public Paciente findPacienteByRun(Integer run);
  /**
   * *
   * Metodo que permite buscar si el rut del paciente existe o no en nuestro
   * sistema
   *
   * @param rut resive el Rut (paciente.getRunPaciente)
   * @return retorna un true si existe el paciente, retorna el false si no
   * encuentra el paciente
   */
  public boolean findByRutPaciente(int rut);
  /**
   * *
   * Metodo que busca si el Número de ficha ingresado se encuentra registrado
   * en el sistema
   *
   * @param nroFicha valor String ingresado por el usuario
   * @return retorna true si se encuentra el nro de ficha en el sistema, falso
   * si no lo encuentra
   */
  public boolean findByNroFicha(String nroFicha);
  /**
   * *
   * Metodo que me permite listar las salas activas de mi especialidad
   *
   * @param especialidad Object tipo Especialidad
   * @return Retorna una lista de Salas Activas
   */
  public List<Sala> listSalasActivasByEspecialidad(Especialidad especialidad);
  /**
   * *
   * metodo que me permite listar las camas activas con estado "Desocupado"
   *
   * @param sala Object tipo Sala
   * @return retorna una lista de Camas Desocupadas Activas
   */
  public List<Cama> listCamasActivasDesocupadasBySala(Sala sala);
  /**
   * *
   * metodo que permite saber si un paciente actualente esta hospitalizado
   *
   * @param paciente Objeto tipo Paciente
   * @return retorna True en caso de estar hospitalizado, en caso contrario
   * False
   */
  public boolean pacienteHospitalizado(Paciente paciente);
  /**
   * *
   * metodo indica si existen camas disponibles y activas dentro de una
   * especialidad activa
   *
   * @param especialidad
   * @return retorna True en caso de existir camas desocupadas en la
   * especialidad, caso contrario retorna False
   */
  public boolean camasDisponiblesEnEspecialidad(Especialidad especialidad);
  /**
   * *
   * Metodo que busca el paciente por número e ficha
   *
   * @param nroFicha numero de ficha asociado al paciente, ingresado por el
   * usuario
   * @return Retorna el paciente su es encontrado, caso contrario retorna NULL
   */
  public Paciente findPacienteByNroFicha(String nroFicha);
  //Metodos para Creacion de Menus
  public boolean BuscarNombreMenu(String nombre);
  public List<Opcion> findByIdMenu(Menu menu);
  public List<RolOpcion> opcionesByRol(Rol rol);
  public List<Opcion> findOpcionesNotUssedByRol(Rol rol);
  public List<Opcion> findOpcionesNotUssedByUsser(UsuarioSistema usuario);
  public void deleteRolOpcionByIdRol(Rol idRol);
  public void deleteUsuarioOpcionByIdUsser(UsuarioSistema idUsuario);
  public List<Opcion> findAllOpciones();
  public List<Cama> listCamasActivasDesocupadasByEstadoCama(Sala idSala, TipoCama idTipoCama);
  /***
   * Metodo que lista el estado del paciente dependiendo del tipo de cama seleccionado
   * @param idTipoCama Objeto TipoCama
   * @return Retorna El lista con los estados del paciente
   */
  public List<EstadoPaciente> listEstadoPacienteByTipoCama(TipoCama idTipoCama);
  /***
   * Metodo que se utiliza para saber si el paciente tiene traslados pendientes
   * @param paciente Objeto paciente
   * @return retorna un true o un false dependiendo si tiene o no traslados
   */
  public boolean trasladosPendientes(Paciente paciente);
  //Traslados temporales
  public Especialidad getEspecialidad(int id);
  /***
   * Metodo que me devuelve el ingreso de hospitalizados por paciente, siempre devuelve el ultimo registro
   * @return retorna una lista de ingreso de hospitalizados
   */
  public List<IngresoHospitalizados> listIngresos();
  /***
  * Metodo que me permite traer el registro por paciente de todas las camas por 
  * las que a sido asignado.
  * @param paciente
  * @return retorna una lista de las camas a las cuales a sido asignado el paciente
  */
  public List<AsignacionCama> listCamasAsignadasByPaciente(Paciente paciente);
  public IngresoHospitalizados obtenerIngresoHospitalizado(Paciente paciente);
  public EgresoHospitalizados obtenerEgresoHospitalizado(Paciente paciente);
  public List<TrasladoTemporal> getTrasladosTemporalesByIdEsp(Integer id);
  public int totalTrasladosPendientes(Integer id);
  //opcionesUsuario
  public List<UsuarioOpcion> getOpcionesUsuario(Integer id);
  public boolean checkMenu(Integer id,Integer id2);
  /***
   * Metodo obtiene una lista de UsuarioOpcion por usuario de sistema
   * @param usuario
   * @return 
   */
  public List<UsuarioOpcion> opcionesByUsser(UsuarioSistema usuario);
  public RolOpcion findByIdRolOpcion(Integer Id);
  public UsuarioOpcion findByIdUsuarioOpcion(Integer Id);
  public UsuarioSistema findByLoginUsuario(Integer rut);
  public boolean checkParentesco(String dato);
  public boolean checkApoderado(String dato);
  public Apoderado findApoderadoById(Integer id);
  public Integer findIdAsignacionMaxPaciente(Integer id);
  public AsignacionCama findUltimaAsignacionDelPaciente(Integer id);
  public EstadoCama findEstadoCamaById(Integer id);
  //Traslado Temporal para llenar ubicacion del paciente
  public TrasladoTemporal findUltimoTrasladoTemporalByIdPaciente(Integer id);
  public TipoEgreso findTipoEgresoByID(Integer id);
  //get Asignacion cama de paciente con fecha null
  public AsignacionCama findAsignacionCamaByPacienteFENull(Paciente paciente);
   public AsignacionCama findAsignacionCamaByPacienteFENull2(Paciente paciente);
  //Crear nuevo registro de ingreso si es que existe un egreso para el
  public boolean existeCierreDeIngreso(Paciente paciente);
  
  //Metotodos para Completar ubicacionde Personas dentro del hostpital, utilizada
  //en la vista PacientesHospitalizados.
  public String getUbicacionActual(Integer id);
  //Lista con los pacientes que estan hospitalizados por el Flag de la tabla.
  public List<TipoEgreso> listaDeEgresosHospital();
  //get para los datos del apoderado, para llenado rapido de datos en las vistas de la oirs
  public Apoderado getApoderado(String dato);
  public Parentesco getParentescoByID(Integer id);

  public List<AsignacionCama> listAllHospitalizados();
  public List<AsignacionCama> listHospitalizadosByEspecialidad(Especialidad especialidad);
  public List<AsignacionCama> listAllEctopicos();
  public List<AsignacionCama> listEctopicosByEspecialidad(Especialidad especialidad);
  //reportes merica
  public List<IngresoHospitalizados> TodosLosIngresoDelPaciente(Integer id);
  public List<EgresoHospitalizados> TodosLosEgresosDelPaciente(Integer id);
  public ArrayList<AsignacionCama> getAsignacionesCamasPorFechasYIdPaciente(Date ih, Date eh, Integer id);
  //editar camas
  public Cama findCamaByID(BigInteger numeroCama);
  public Sala findSalaById(Integer id);
  public EstadoPaciente finEstadoById(Integer id);
  // Rerporte Egresados
   public List<Integer> getListaIdPacientesRangoFechas(Date fecha1, Date fecha2);
   public ArrayList<EgresoHospitalizados> getEgresos(Date fecha1, Date fecha2);

}
