/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Apoderado;
import cl.hblt.entities.AsignacionCama;
import cl.hblt.entities.EgresoHospitalizados;
import cl.hblt.entities.EstadoPaciente;
import cl.hblt.entities.IngresoHospitalizados;
import cl.hblt.entities.Paciente;
import cl.hblt.entities.TrasladoTemporal;
import cl.hblt.sessions.AsignacionCamaFacade;
import cl.hblt.sessions.AsignacionCamaFacadeLocal;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.TrasladoTemporalFacade;
import cl.hblt.sessions.TrasladoTemporalFacadeLocal;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import utils.appBean;

/**
 * @author Edwin_Guaman
 */
public class AsignacionCamaListBean {

    @EJB
    private final AsignacionCamaFacadeLocal asignacionCamaFacade;

    @EJB
    private final TrasladoTemporalFacadeLocal trasladoTemporalFacade;

    @EJB
    private final BussinessFacadeLocal bussinessFacade;

    private List<AsignacionCama> listAsigCamasEnCurso, listAsignacionCamas;
    private List<IngresoHospitalizados> listIngresohospitalizados, filteredIngresoghospitalizados;
    private IngresoHospitalizados ih;
    private EgresoHospitalizados eh;
    private Paciente paciente;
    private Apoderado apoderado;
    private String edad;
    private String prevision;
    private AsignacionCama asignacionCama;
    private Integer idEstadoPaciente;
    private List<EstadoPaciente> listEstadoPaciente;
    private IngresoHospitalizados selectedIngresoHospitalizado;
    //Cargar datos tabla TrasladoTemporal
    private TrasladoTemporal trasladoTempora;

    public AsignacionCamaListBean() {
        this.bussinessFacade = new BussinessFacade();
        this.apoderado = new Apoderado();
        this.trasladoTempora = new TrasladoTemporal();
        trasladoTemporalFacade = new TrasladoTemporalFacade();
        this.asignacionCamaFacade = new AsignacionCamaFacade();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext extContext = facesContext.getExternalContext();
        if (extContext.getSessionMap().get("Paciente") != null && extContext.getSessionMap().get("asignacionCamaEnModificacion") != null) {
            paciente = (Paciente) extContext.getSessionMap().get("Paciente");
            asignacionCama = (AsignacionCama) extContext.getSessionMap().get("asignacionCamaEnModificacion");
        } else {
            this.paciente = new Paciente();
            this.asignacionCama = new AsignacionCama();
        }
    }

    @PostConstruct
    public void init() {
        listIngresohospitalizados = this.bussinessFacade.listIngresos();
        listAsigCamasEnCurso = this.bussinessFacade.findAsigCamasEnCurso();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext extContext = facesContext.getExternalContext();
        if (extContext.getSessionMap().get("Paciente") != null && extContext.getSessionMap().get("asignacionCamaEnModificacion") != null) {
            listEstadoPaciente = bussinessFacade.listEstadoPacienteByTipoCama(asignacionCama.getIdCama().getIdTipoCama());
        }

    }

    public String calculaTotalDias(Paciente paciente) {
        Date date = new Date();
        ih = bussinessFacade.obtenerIngresoHospitalizado(paciente);
        eh = bussinessFacade.obtenerEgresoHospitalizado(paciente);
        long diferencia;
        if (eh == null) {
            diferencia = (date.getTime() - ih.getFechaIngreso().getTime()) / appBean.MILLSECS_PER_DAY;
            return diferencia + " Días";
        } else if (eh.getFechaEgreso().before(ih.getFechaIngreso())) {
            diferencia = (date.getTime() - ih.getFechaIngreso().getTime()) / appBean.MILLSECS_PER_DAY;
            return diferencia + " Días";
        } else {
            diferencia = (eh.getFechaEgreso().getTime() - ih.getFechaIngreso().getTime()) / appBean.MILLSECS_PER_DAY;
            return diferencia + " Días";
        }
    }

    public String cargaDatos() {

        if (selectedIngresoHospitalizado == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar una fila", null));
        } else {
            paciente = selectedIngresoHospitalizado.getIdPaciente();
            apoderado = selectedIngresoHospitalizado.getIdApoderado();
            listAsignacionCamas = bussinessFacade.listCamasAsignadasByPaciente(paciente);
            if (bussinessFacade.findUltimoTrasladoTemporalByIdPaciente(paciente.getIdPaciente()) != null) {
                this.trasladoTempora = bussinessFacade.findUltimoTrasladoTemporalByIdPaciente(paciente.getIdPaciente());
            } else {
                this.trasladoTempora.setEstado('A');
                this.trasladoTempora.setFecha(new Date());
                FacesContext context = FacesContext.getCurrentInstance();
                ExternalContext extContext = context.getExternalContext();
                Integer idEspecialidad = (Integer) extContext.getSessionMap().get("idEspecialidad");
                this.trasladoTempora.setIdEspecialidadTraslado(idEspecialidad);
                this.trasladoTempora.setIdPaciente(paciente);
                this.trasladoTempora.setMotivo("Traslado inicial a la especialidad: " + bussinessFacade.getEspecialidad(idEspecialidad).getNombreEspecialidad());
                this.trasladoTempora.setIdEspecialidadProcedencia(idEspecialidad);
                trasladoTemporalFacade.create(trasladoTempora);
            }
            Date today = Calendar.getInstance().getTime();
            this.edad = appBean.CalculaEdad(today, paciente.getFechaNacimiento());
            this.prevision = paciente.getIdTipoPrevision().getIdPrevision().getDescripcionPrevision() + " " + paciente.getIdTipoPrevision().getDescripcionTipoPrevision();
            return "InformacionPaciente.xhtml";
        }
        return "";
    }

    //CAMBIAR ESTADO PACIENTE cargando datos del paciente  
    public void modificarEstado() throws IOException {

        if (selectedIngresoHospitalizado == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar una fila", null));
        } else {
            paciente = selectedIngresoHospitalizado.getIdPaciente();
            FacesContext context = FacesContext.getCurrentInstance();
            if (bussinessFacade.findAsignacionCamaByPacienteFENull2(paciente) == null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No existe Asignacion del Paciente", null));
            } else {
                asignacionCama = bussinessFacade.findAsignacionCamaByPacienteFENull2(paciente);
                System.out.println("dato asignado a asignacionCama " + asignacionCama.getIdEstadoPaciente().getDescripcionEstadoPaciente());
                System.out.println("id asignado a asignacionCama " + asignacionCama.getIdAsignacion());
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ExternalContext extContext = facesContext.getExternalContext();
                extContext.getSessionMap().put("Paciente", paciente);
                extContext.getSessionMap().put("asignacionCamaEnModificacion", asignacionCama);
                extContext.redirect("modificarEstadoPaciente.xhtml");
            }
        }
    }

    //ASIGNAR APODERADO RESPONSABLE
    public void asignarApoderado() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        if (selectedIngresoHospitalizado == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar una fila", null));
        } else {
            if (selectedIngresoHospitalizado.getIdApoderado().getRunApoderado().equals(1)) {
                extContext.redirect("IngresoApoderadoCreate.xhtml");
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Paciente ya tiene apoderado asociado", null));
            }
        }
    }

    public void guardarModificacionEstado() throws IOException {
        EstadoPaciente estado = bussinessFacade.finEstadoById(idEstadoPaciente);
        asignacionCama.setIdEstadoPaciente(estado);
        asignacionCamaFacade.edit(asignacionCama);
        FacesContext.getCurrentInstance().getExternalContext().redirect("PacientesHospitalizados.xhtml");
    }

    public String getDiagnostico(Integer id) {
        AsignacionCama asig = bussinessFacade.findUltimaAsignacionDelPaciente(id);
        return asig.getDiagnosticoSala();
    }

    //Getter and Setter
    public String getNombreEspecialidadById(int id) {
        return bussinessFacade.getEspecialidad(id).getNombreEspecialidad();
    }

    public List<AsignacionCama> getListAsigCamasEnCurso() {
        return listAsigCamasEnCurso;
    }

    public void setListAsigCamasEnCurso(List<AsignacionCama> listAsigCamasEnCurso) {
        this.listAsigCamasEnCurso = listAsigCamasEnCurso;
    }

    public List<IngresoHospitalizados> getListIngresohospitalizados() {
        return listIngresohospitalizados;
    }

    public void setListIngresohospitalizados(List<IngresoHospitalizados> listIngresohospitalizados) {
        this.listIngresohospitalizados = listIngresohospitalizados;
    }

    public List<AsignacionCama> getListAsignacionCamas() {
        return listAsignacionCamas;
    }

    public void setListAsignacionCamas(List<AsignacionCama> listAsignacionCamas) {
        this.listAsignacionCamas = listAsignacionCamas;
    }

    public IngresoHospitalizados getIh() {
        return ih;
    }

    public void setIh(IngresoHospitalizados ih) {
        this.ih = ih;
    }

    public EgresoHospitalizados getEh() {
        return eh;
    }

    public void setEh(EgresoHospitalizados eh) {
        this.eh = eh;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getPrevision() {
        return prevision;
    }

    public void setPrevision(String prevision) {
        this.prevision = prevision;
    }

    public Apoderado getApoderado() {
        return apoderado;
    }

    public void setApoderado(Apoderado apoderado) {
        this.apoderado = apoderado;
    }

    public TrasladoTemporal getTrasladoTempora() {
        return trasladoTempora;
    }

    public void setTrasladoTempora(TrasladoTemporal trasladoTempora) {
        this.trasladoTempora = trasladoTempora;
    }

    public AsignacionCama getAsignacionCama() {
        return asignacionCama;
    }

    public void setAsignacionCama(AsignacionCama asignacionCama) {
        this.asignacionCama = asignacionCama;
    }

    public Integer getIdEstadoPaciente() {
        return idEstadoPaciente;
    }

    public void setIdEstadoPaciente(Integer idEstadoPaciente) {
        this.idEstadoPaciente = idEstadoPaciente;
    }

    public List<EstadoPaciente> getListEstadoPaciente() {
        return listEstadoPaciente;
    }

    public IngresoHospitalizados getSelectedIngresoHospitalizado() {
        return selectedIngresoHospitalizado;
    }

    public void setSelectedIngresoHospitalizado(IngresoHospitalizados selectedIngresoHospitalizado) {
        this.selectedIngresoHospitalizado = selectedIngresoHospitalizado;
    }

    public List<IngresoHospitalizados> getFilteredIngresoghospitalizados() {
        return filteredIngresoghospitalizados;
    }

    public void setFilteredIngresoghospitalizados(List<IngresoHospitalizados> filteredIngresoghospitalizados) {
        this.filteredIngresoghospitalizados = filteredIngresoghospitalizados;
    }

}
