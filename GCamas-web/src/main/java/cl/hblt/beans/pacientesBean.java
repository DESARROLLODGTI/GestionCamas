package cl.hblt.beans;

import cl.hblt.entities.AsignacionCama;
import cl.hblt.entities.Cama;
import cl.hblt.entities.EgresoHospitalizados;
import cl.hblt.entities.IngresoHospitalizados;
import cl.hblt.entities.Paciente;
import cl.hblt.entities.Prevision;
import cl.hblt.entities.Sexo;
import cl.hblt.entities.TipoEgreso;
import cl.hblt.entities.TipoPrevision;
import cl.hblt.entities.UsuarioSistema;
import cl.hblt.sessions.AsignacionCamaFacade;
import cl.hblt.sessions.AsignacionCamaFacadeLocal;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.CamaFacade;
import cl.hblt.sessions.CamaFacadeLocal;
import cl.hblt.sessions.EgresoHospitalizadosFacade;
import cl.hblt.sessions.EgresoHospitalizadosFacadeLocal;
import cl.hblt.sessions.IngresoHospitalizadosFacade;
import cl.hblt.sessions.IngresoHospitalizadosFacadeLocal;
import cl.hblt.sessions.PacienteFacade;
import cl.hblt.sessions.PacienteFacadeLocal;
import cl.hblt.sessions.PrevisionFacade;
import cl.hblt.sessions.PrevisionFacadeLocal;
import cl.hblt.sessions.SexoFacade;
import cl.hblt.sessions.SexoFacadeLocal;
import cl.hblt.sessions.TipoPrevisionFacadeLocal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import utils.appBean;

/**
 *
 * @author Edwin_Guaman
 * @version 1.2
 */
public class pacientesBean {

    @EJB
    private final IngresoHospitalizadosFacadeLocal ingresoHospitalizadosFacade;

    @EJB
    private final CamaFacadeLocal camaFacade;
    @EJB
    private final AsignacionCamaFacadeLocal asignacionCamaFacade;
    @EJB
    private final EgresoHospitalizadosFacadeLocal egresoHospitalizadosFacade;
    @EJB
    private final BussinessFacadeLocal bussinessFacade;
    @EJB
    private TipoPrevisionFacadeLocal tipoPrevisionFacade;
    @EJB
    private final SexoFacadeLocal sexoFacade;
    @EJB
    private final PrevisionFacadeLocal previsionFacade;
    @EJB
    private final PacienteFacadeLocal pacienteFacade;
    private EgresoHospitalizados egresoH;
    private Paciente paciente;
    private List<Paciente> listPacientes;
    private List<Paciente> filteredPacientes;
    private List<Sexo> listSexo;
    private List<Prevision> listPrevision;
    private List<TipoPrevision> listTipoPrevision;
    private List<TipoPrevision> listTipoPrevisionCombo;
    private String rut;
    private String edad;
    private Boolean pacienteHospitalizado;
    private List<TipoEgreso> listaEgresos;

    ///////////////////// INICIO METODOS //////////////////////
    /**
     * *
     * Devuelve todos los strings en mayuscula pertenecientes a la objeto
     * paciente
     */
    public void camposMayuscula() {
        paciente.setNombre(paciente.getNombre().toUpperCase());
        paciente.setPapellidoPaciente(paciente.getPapellidoPaciente().toUpperCase());
        paciente.setSapellidoPaciente(paciente.getSapellidoPaciente().toUpperCase());
        paciente.setNumeroFicha(paciente.getNumeroFicha().toUpperCase());
    }

    public void create() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            rut = rut.replace("-", "");
            rut = rut.replace(".", "");
            String rut1 = rut.substring(0, rut.length() - 1);
            String dv = rut.charAt(rut.length() - 1) + "";
            camposMayuscula();
            paciente.setRunPaciente(Integer.parseInt(rut1));
            paciente.setDvPaciente(dv.toUpperCase());
            if (bussinessFacade.findByRutPaciente(paciente.getRunPaciente())) {
                if (bussinessFacade.findByNroFicha(paciente.getNumeroFicha())) {
                    if (!this.edad.equals("-1")) {
                        pacienteFacade.create(paciente);
                        this.resetData();
                        context.getExternalContext().redirect("Pacientes.xhtml");
                    } else {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe Ingresar una Fecha de Nacimiento Válida", null));
                    }
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El N° de Ficha Ingresado ya se Encuentra Asociado a un Paciente", null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El RUT del Paciente ya se encuentra Ingresado", null));
            }
        } catch (IOException e) {
            context.addMessage(null, new FacesMessage("Error", e.getMessage()));
        }
    }

    public void update() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            camposMayuscula();
            if (!bussinessFacade.findByNroFicha(paciente.getNumeroFicha())) {
                pacienteFacade.edit(paciente);
                this.resetData();
                context.getExternalContext().redirect("Pacientes.xhtml");
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El N° de Ficha Ingresado ya se Encuentra Asociado a un Paciente", null));
            }
        } catch (IOException e) {
            context.addMessage(null, new FacesMessage("Error", e.getMessage()));
        }
    }

    public void onPrevisionChange() {
        listTipoPrevisionCombo.removeAll(listTipoPrevisionCombo);
        for (TipoPrevision tp : listTipoPrevision) {
            if (tp.getIdPrevision().equals(paciente.getIdTipoPrevision().getIdPrevision())) {
                listTipoPrevisionCombo.add(tp);
            }
        }
    }

    public void onValidaRut() {
        rut = rut.replace("-", "");
        rut = rut.replace(".", "");
        String rut1 = rut.substring(0, rut.length() - 1);
        String dv = rut.charAt(rut.length() - 1) + "";
        if (!appBean.validarRut(rut1, dv)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rut Invalido", "Rut Invalido"));
        }
    }

    public void onFormateaRut() {
        this.setRut(appBean.FormateaRut(rut));
    }

    public void calculaEdad(ActionEvent event) {
        Date today = Calendar.getInstance().getTime();
        edad = utils.appBean.CalculaEdad(today, paciente.getFechaNacimiento());
    }

    public boolean hospitalizado(Paciente paciente) {
        return bussinessFacade.pacienteHospitalizado(paciente);
    }

    public void camasDisponiblesEnEspecialidad() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        UsuarioSistema user = (UsuarioSistema) extContext.getSessionMap().get("ObjetoUsuario");
        if (!bussinessFacade.camasDisponiblesEnEspecialidad(user.getIdEspecialidad())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Hay camas disponibles en especialidad", "No Hay camas disponibles en especialidad"));
            //context.getExternalContext().redirect("Pacientes.xhtml");
        } else {
            if (user.getIdRol().getIdRol().equals(3)) {
                context.getExternalContext().redirect("../RegistrosHospitalizados/RegistrarHospitalizacionOirs.xhtml");
            } else {
                context.getExternalContext().redirect("../RegistrosHospitalizados/RegistrarHospitalizacion.xhtml");
            }
        }
    }

    public void volver() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect("Pacientes.xhtml");
    }

    /**
     * *
     * Metodo que me valida que mi usuario no tenga traslados pendientes.
     *
     * @param paciente
     * @throws java.io.IOException
     */
    public void trasladosPendientes(Paciente paciente) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (bussinessFacade.trasladosPendientes(paciente)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "No puede realizar un traslado, debido a que ud ya tiene un traslado pendiente asociado a ese Paciente",
                    "No puede realizar un traslado, debido a que ud ya tiene un traslado pendiente asociado a ese Paciente"));
        } else {
            context.getExternalContext().redirect("../RegistrosHospitalizados/SolicitarTraslado.xhtml");
        }
    }

    public void egresarPaciente() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        egresoH.setIdPaciente(paciente);
        IngresoHospitalizados ih = bussinessFacade.obtenerIngresoHospitalizado(paciente);
        if (egresoH.getIdTipoEgreso().getIdTipoEgreso() != 5) {
            short aux = 1;
            ih.setEgreso(aux);
            ingresoHospitalizadosFacade.edit(ih);
            egresoHospitalizadosFacade.create(egresoH);
            egresoH = new EgresoHospitalizados();
            AsignacionCama asignacionCama = bussinessFacade.findAsignacionCamaByPacienteFENull(paciente);
            if (asignacionCama != null) {
                asignacionCama.setFechaEgreso(new Date());
                asignacionCamaFacade.edit(asignacionCama);
                //Liberacion de Cama
                Cama cama = bussinessFacade.findCamaById(asignacionCama.getIdCama().getIdCama());
                cama.setIdEstadoCama(bussinessFacade.findEstadoCamaById(2));
                camaFacade.edit(cama);
            }
            context.addMessage(null, new FacesMessage("Successful", "Paciente Egresado Exitosamente: "));
            context.getExternalContext().redirect("Pacientes.xhtml");
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Egreso no Valido", ""));
        }

    }
    //////////////////// FIN METODOS //////////////////////
    ///////////////////////////////////////////////////////
    ///////////// INICIO CONSTRUCTOR /////////////////////

    public pacientesBean() {
        pacienteFacade = new PacienteFacade();
        ingresoHospitalizadosFacade = new IngresoHospitalizadosFacade();
        asignacionCamaFacade = new AsignacionCamaFacade();
        camaFacade = new CamaFacade();
        previsionFacade = new PrevisionFacade();
        bussinessFacade = new BussinessFacade();
        egresoHospitalizadosFacade = new EgresoHospitalizadosFacade();
        listTipoPrevisionCombo = new ArrayList<TipoPrevision>();
        sexoFacade = new SexoFacade();
        egresoH = new EgresoHospitalizados();
        paciente = new Paciente();
        this.rut = "";
        this.edad = "";
    }

    @PostConstruct
    public void init() {
        this.listPacientes = pacienteFacade.findAll2("papellidoPaciente");
        this.listSexo = sexoFacade.findAll();
        this.listaEgresos = bussinessFacade.listaDeEgresosHospital();
        this.listPrevision = previsionFacade.findAll2("descripcionPrevision");
        this.listTipoPrevision = tipoPrevisionFacade.findAll();
        this.onPrevisionChange();
        egresoH = new EgresoHospitalizados();
        this.rut = "";
        this.edad = "";
        this.paciente = new Paciente();
    }

    ///////////////////////// FIN CONSTRUCTOR////////////////
    /////////////////////////////////////////////////////////
    ///////////////// INICIO GETTER Y SETTER  ///////////////
    public List<Paciente> getListPacientes() {
        return listPacientes;
    }

    public void setListPacientes(List<Paciente> listPacientes) {
        this.listPacientes = listPacientes;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<TipoPrevision> getListTipoPrevision() {
        return listTipoPrevision;
    }

    public List<Sexo> getListSexo() {
        return sexoFacade.findAll();
    }

    public void setListSexo(List<Sexo> listSexo) {
        this.listSexo = listSexo;
    }

    public List<Prevision> getListPrevision() {
        return listPrevision;
    }

    public void setListPrevision(List<Prevision> listPrevision) {
        this.listPrevision = listPrevision;
    }

    public List<TipoPrevision> getListTipoPrevisionCombo() {
        return listTipoPrevisionCombo;
    }

    public void setListTipoPrevisionCombo(List<TipoPrevision> listTipoPrevisionCombo) {
        this.listTipoPrevisionCombo = listTipoPrevisionCombo;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public List<Paciente> getFilteredPacientes() {
        return filteredPacientes;
    }

    public void setFilteredPacientes(List<Paciente> filteredPacientes) {
        this.filteredPacientes = filteredPacientes;
    }

    public void resetData() {
        paciente = null;
        paciente = new Paciente();
        init();
        this.rut = "";
        this.edad = "";
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public Boolean getPacienteHospitalizado() {
        return pacienteHospitalizado;
    }

    public void setPacienteHospitalizado(Boolean pacienteHospitalizado) {
        this.pacienteHospitalizado = pacienteHospitalizado;
    }

    public EgresoHospitalizados getEgresoH() {
        return egresoH;
    }

    public void setEgresoH(EgresoHospitalizados egresoH) {
        this.egresoH = egresoH;
    }

    public List<TipoEgreso> getListaEgresos() {
        return listaEgresos;
    }

    public void setListaEgresos(List<TipoEgreso> listaEgresos) {
        this.listaEgresos = listaEgresos;
    }

}
