package cl.hblt.beans;

import cl.hblt.entities.AsignacionCama;
import cl.hblt.entities.Cama;
import cl.hblt.entities.EgresoHospitalizados;
import cl.hblt.entities.Especialidad;
import cl.hblt.entities.EstadoPaciente;
import cl.hblt.entities.Paciente;
import cl.hblt.entities.Sala;
import cl.hblt.entities.TipoCama;
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
import cl.hblt.sessions.TipoCamaFacade;
import cl.hblt.sessions.TipoCamaFacadeLocal;
import cl.hblt.sessions.TrasladoTemporalFacade;
import cl.hblt.sessions.TrasladoTemporalFacadeLocal;
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
    private TipoCamaFacadeLocal tipoCamaFacade;
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
    private AsignacionCama asignacionCama;
    private AsignacionCama asignacionCamaFutura;
    private Sala sala;
    private Cama cama;
    private List<Sala> listaSalasPorEspecialidad;
    private List<TipoCama> listTipoCama;
    private Boolean ectopico = false;
    private Boolean isOirs = false;
    private List<Cama> listaCamasPorEspecialidad;
    private List<EstadoPaciente> listEstadoPacientes;

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
        asignacionCama = new AsignacionCama();
        asignacionCamaFutura = new AsignacionCama();
        tipoCamaFacade = new TipoCamaFacade();
    }

    @PostConstruct
    public void myInit() {
        listEspecialidades = especialidadFacade.findAll();
        FacesContext context3 = FacesContext.getCurrentInstance();
        ExternalContext extContext = context3.getExternalContext();
        UsuarioSistema user = (UsuarioSistema) extContext.getSessionMap().get("ObjetoUsuario");
        Especialidad esp = (Especialidad) extContext.getSessionMap().get("Especialidad");
        if (user.getIdRol().getIdRol().equals(3)) {
            isOirs = true;
        } else {
            listaSalasPorEspecialidad
                    = bussinessFacade.listSalasActivasByEspecialidad(esp);
        }
    }

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

    public void trasladarDentroUnidad() {
        trasladoTemporal.setEstado(EstadoTrasladoEnum.ACEPTADO);
        trasladoTemporal.setIdPaciente(paciente);
        trasladoTemporal.setIdEspecialidadProcedencia(asignacionCamaFutura.getIdServicioProcedencia());
        trasladoTemporal.setIdEspecialidadTraslado(asignacionCama.getIdCama().getIdSala().getIdEspecialidad().getIdEspecialidad());
        trasladoTemporalFacade.create(trasladoTemporal);
        AsignacionCama asignacionCama = bussinessFacade.findAsignacionCamaByPacienteFENull(paciente);
        if (asignacionCama != null) {
            asignacionCama.setFechaEgreso(trasladoTemporal.getFecha());
            asignacionCamaFacade.edit(asignacionCama);
            //Liberacion de Cama
            Cama cama = bussinessFacade.findCamaById(asignacionCama.getIdCama().getIdCama());
            cama.setIdEstadoCama(bussinessFacade.findEstadoCamaById(2));
            camaFacade.edit(cama);
        }
        System.out.println("Depues de trasladar");

        asignacionCamaFutura.setFechaAsignacion(trasladoTemporal.getFecha());
        asignacionCamaFutura.setIdPaciente(asignacionCama.getIdPaciente());
        short aux = 1;
        if (!ectopico) {
            aux = 0;
        }
        asignacionCamaFutura.setEctopico(aux);
        asignacionCamaFacade.create(asignacionCamaFutura);
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

    public List<TipoCama> getTiposCamas() {
        listTipoCama = tipoCamaFacade.findAll();
        return listTipoCama;
    }

    public List<Sala> getSalasByEspecialidad(Especialidad e) {
        listaSalasPorEspecialidad = bussinessFacade.findSalasByIdEspecialidad(e.getIdEspecialidad());
        return listaSalasPorEspecialidad;
    }

    public void onSalaChange() {
        listaCamasPorEspecialidad
                = bussinessFacade.
                listCamasActivasDesocupadasBySala(
                        asignacionCamaFutura.getIdCama().getIdSala());
    }

    public void onCamaChange() {
        asignacionCamaFutura.getIdCama().
                getIdSala().
                setIdSala(asignacionCamaFutura.getIdCama().getIdSala().getIdSala());
    }

    public void onTipoCamaChange() {
        listaCamasPorEspecialidad
                = bussinessFacade.
                listCamasActivasDesocupadasByEstadoCama(
                        asignacionCamaFutura.getIdCama().getIdSala(),
                        asignacionCamaFutura.getIdCama().getIdTipoCama());
        listEstadoPacientes
                = bussinessFacade.listEstadoPacienteByTipoCama(
                        asignacionCamaFutura.getIdCama().getIdTipoCama());
    }

    public void onEspecialidadChange() {
        listaSalasPorEspecialidad
                = bussinessFacade.
                listSalasActivasByEspecialidad(asignacionCamaFutura.getIdCama().getIdSala().getIdEspecialidad());
    }

    public void onCamaChangePrecarga() {
        asignacionCamaFutura.setIdPaciente(asignacionCama.getIdPaciente());
        asignacionCamaFutura.getIdCama()
                .getIdSala()
                .getIdEspecialidad()
                .setNombreEspecialidad(asignacionCama.getIdCama().getIdSala().getIdEspecialidad().getNombreEspecialidad());
        for (Sala sala1 : listaSalasPorEspecialidad) {
            if (sala1.getIdSala().equals(asignacionCamaFutura.getIdCama().getIdSala().getIdSala())) {
                asignacionCamaFutura.getIdCama().getIdSala().setNombreSala(sala1.getNombreSala());
            }
        }
        for (Cama cama1 : listaCamasPorEspecialidad) {
            if (cama1.getIdCama().equals(asignacionCamaFutura.getIdCama().getIdCama())) {
                asignacionCamaFutura.getIdCama().setNumeroCama(cama1.getNumeroCama());
            }
        }
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

    public AsignacionCama getAsignacionCama() {
        return asignacionCama;
    }

    public void setAsignacionCama(AsignacionCama asignacionCama) {
        this.asignacionCama = asignacionCama;
    }

    public AsignacionCama getAsignacionCamaFutura() {
        return asignacionCamaFutura;
    }

    public void setAsignacionCamaFutura(AsignacionCama asignacionCamaFutura) {
        this.asignacionCamaFutura = asignacionCamaFutura;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Cama getCama() {
        return cama;
    }

    public void setCama(Cama cama) {
        this.cama = cama;
    }

    public List<Sala> getListaSalasPorEspecialidad() {
        return listaSalasPorEspecialidad;
    }

    public void setListaSalasPorEspecialidad(List<Sala> listaSalasPorEspecialidad) {
        this.listaSalasPorEspecialidad = listaSalasPorEspecialidad;
    }

    public List<TipoCama> getListTipoCama() {
        return listTipoCama;
    }

    public void setListTipoCama(List<TipoCama> listTipoCama) {
        this.listTipoCama = listTipoCama;
    }

    public Boolean getEctopico() {
        return ectopico;
    }

    public void setEctopico(Boolean ectopico) {
        this.ectopico = ectopico;
    }

    public Boolean getIsOirs() {
        return isOirs;
    }

    public void setIsOirs(Boolean isOirs) {
        this.isOirs = isOirs;
    }

    public List<Cama> getListaCamasPorEspecialidad() {
        return listaCamasPorEspecialidad;
    }

    public void setListaCamasPorEspecialidad(List<Cama> listaCamasPorEspecialidad) {
        this.listaCamasPorEspecialidad = listaCamasPorEspecialidad;
    }

    public List<EstadoPaciente> getListEstadoPacientes() {
        return listEstadoPacientes;
    }

    public void setListEstadoPacientes(List<EstadoPaciente> listEstadoPacientes) {
        this.listEstadoPacientes = listEstadoPacientes;
    }
    

}
