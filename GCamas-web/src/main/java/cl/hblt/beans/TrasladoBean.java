package cl.hblt.beans;

import cl.hblt.entities.AsignacionCama;
import cl.hblt.entities.Cama;
import cl.hblt.entities.EgresoHospitalizados;
import cl.hblt.entities.Especialidad;
import cl.hblt.entities.IngresoHospitalizados;
import cl.hblt.entities.Paciente;
import cl.hblt.entities.TrasladoTemporal;
import cl.hblt.entities.UsuarioSistema;
import cl.hblt.enums.EstadoTrasladoEnum;
import cl.hblt.sessions.AsignacionCamaFacade;
import cl.hblt.sessions.AsignacionCamaFacadeLocal;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.CamaFacade;
import cl.hblt.sessions.CamaFacadeLocal;
import cl.hblt.sessions.EgresoHospitalizadosFacade;
import cl.hblt.sessions.EgresoHospitalizadosFacadeLocal;
import cl.hblt.sessions.EspecialidadFacade;
import cl.hblt.sessions.EspecialidadFacadeLocal;
import cl.hblt.sessions.TrasladoTemporalFacade;
import cl.hblt.sessions.TrasladoTemporalFacadeLocal;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import utils.appBean;

/**
 * @author Edwin_Guaman
 * @version 1.0
 */
public class TrasladoBean {

    @EJB
    private final CamaFacadeLocal camaFacade;
    @EJB
    private final AsignacionCamaFacadeLocal asignacionCamaFacade;
    @EJB
    private final EgresoHospitalizadosFacadeLocal egresoHospitalizadosFacade;
    @EJB
    private final BussinessFacadeLocal bussinessFacade;
    @EJB
    private final TrasladoTemporalFacadeLocal trasladoTemporalFacade;
    @EJB
    private final EspecialidadFacadeLocal especialidadFacade;
    private EgresoHospitalizados egresoH;
    private Paciente paciente;
    private List<Especialidad> listEspecialidades;
    private Especialidad especialidad;
    private TrasladoTemporal trasladoTemporal;

    public void trasladar(ActionEvent event) {
        trasladoTemporal.setEstado(EstadoTrasladoEnum.ACEPTADO);
        trasladoTemporal.setIdPaciente(paciente);
        //Se obtiene del contexto externo de sesion los datos del usuario que se asignana a las variables.
        FacesContext context2 = FacesContext.getCurrentInstance();
        ExternalContext extContext = context2.getExternalContext();
        Especialidad especialidadUsuario = (Especialidad) extContext.getSessionMap().get("Especialidad");
        trasladoTemporal.setIdEspecialidadProcedencia(especialidadUsuario.getIdEspecialidad());
        trasladoTemporal.setIdEspecialidadTraslado(especialidad.getIdEspecialidad());
        trasladoTemporalFacade.create(trasladoTemporal);
        //Ingreso en Duro del id que contiene el tipo de traslado Traslado a otra especialidad
//        TipoEgreso tipoEgreso = bussinessFacade.findTipoEgresoByID(5);
//        egresoH.setFechaEgreso(new Date());
//        egresoH.setIdPaciente(paciente);
//        egresoH.setIdTipoEgreso(tipoEgreso);
//        egresoH.setMotivoEgreso("Traslado a Especialidad: " + especialidad.getNombreEspecialidad());
//        egresoHospitalizadosFacade.create(egresoH);
        //Asignar Fecha de Egreso a Asignacion Cama
        AsignacionCama asignacionCama = bussinessFacade.findAsignacionCamaByPacienteFENull(paciente);
        if (asignacionCama != null) {
            asignacionCama.setFechaEgreso(trasladoTemporal.getFecha());
            asignacionCamaFacade.edit(asignacionCama);
            //Liberacion de Cama
            Cama cama = bussinessFacade.findCamaById(asignacionCama.getIdCama().getIdCama());
            cama.setIdEstadoCama(bussinessFacade.findEstadoCamaById(2));
            camaFacade.edit(cama);
        }

        //Redireccion del metodo a otra vista
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful", "Previsión Guardada Exitosamente: "));
        appBean app = new appBean();
        context.addCallbackParam("url2", app.getBaseUrl() + "/Mantenedores/Pacientes/Pacientes.xhtml");
    }

    /**
     * *
     * metodo que es llamado por ajax por el evento OnChange, que me permite
     * cambiar setear el objeto completo de la especialidad.
     */
    public void onEspecialidadTraslado() {
        Especialidad aux = new Especialidad();
        for (Especialidad e : listEspecialidades) {
            if (e.getIdEspecialidad() == especialidad.getIdEspecialidad().intValue()) {
                especialidad.setIdEspecialidad(e.getIdEspecialidad());
                especialidad.setNombreEspecialidad(e.getNombreEspecialidad());
                especialidad.setIndActivo(e.getIndActivo());
            }
        }
    }

    private List<Especialidad> cargaEspacialidad() {
        List<Especialidad> le = especialidadFacade.findAll();
        FacesContext context2 = FacesContext.getCurrentInstance();
        ExternalContext extContext = context2.getExternalContext();
        UsuarioSistema user = (UsuarioSistema) extContext.getSessionMap().get("ObjetoUsuario");
        for (Especialidad especialidad1 : le) {
            if (user.getIdEspecialidad().equals(especialidad1)) {
                le.remove(especialidad1);
            }
        }
        return le;
    }

    public TrasladoBean() {
        //inicializo facade
        especialidadFacade = new EspecialidadFacade();
        trasladoTemporalFacade = new TrasladoTemporalFacade();
        bussinessFacade = new BussinessFacade();
        egresoHospitalizadosFacade = new EgresoHospitalizadosFacade();
        asignacionCamaFacade = new AsignacionCamaFacade();
        camaFacade = new CamaFacade();
        //inicializo clases
        especialidad = new Especialidad();
        trasladoTemporal = new TrasladoTemporal();
        paciente = new Paciente();
        egresoH = new EgresoHospitalizados();
    }

    @PostConstruct
    public void myInit() {
        listEspecialidades = especialidadFacade.findAll();
    }

//////GETTERS Y SETTER
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Especialidad> getListEspecialidades() {
        return listEspecialidades;
    }

    public void setListEspecialidades(List<Especialidad> listEspecialidades) {
        this.listEspecialidades = listEspecialidades;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public TrasladoTemporal getTrasladoTemporal() {
        return trasladoTemporal;
    }

    public void setTrasladoTemporal(TrasladoTemporal trasladoTemporal) {
        this.trasladoTemporal = trasladoTemporal;
    }

    public void setEgresoH(EgresoHospitalizados egresoH) {
        this.egresoH = egresoH;
    }

    public EgresoHospitalizados getEgresoH() {
        return egresoH;
    }

}
