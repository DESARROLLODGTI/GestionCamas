/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.Apoderado;
import cl.hblt.entities.AsignacionCama;
import cl.hblt.entities.Cama;
import cl.hblt.entities.Especialidad;
import cl.hblt.entities.EstadoCama;
import cl.hblt.entities.EstadoPaciente;
import cl.hblt.entities.IngresoHospitalizados;
import cl.hblt.entities.Paciente;
import cl.hblt.entities.Parentesco;
import cl.hblt.entities.Sala;
import cl.hblt.entities.TipoCama;
import cl.hblt.entities.TrasladoTemporal;
import cl.hblt.entities.UsuarioSistema;
import cl.hblt.sessions.ApoderadoFacade;
import cl.hblt.sessions.ApoderadoFacadeLocal;
import cl.hblt.sessions.AsignacionCamaFacade;
import cl.hblt.sessions.AsignacionCamaFacadeLocal;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.CamaFacade;
import cl.hblt.sessions.CamaFacadeLocal;
import cl.hblt.sessions.EspecialidadFacade;
import cl.hblt.sessions.EspecialidadFacadeLocal;
import cl.hblt.sessions.EstadoPacienteFacade;
import cl.hblt.sessions.EstadoPacienteFacadeLocal;
import cl.hblt.sessions.IngresoHospitalizadosFacade;
import cl.hblt.sessions.IngresoHospitalizadosFacadeLocal;
import cl.hblt.sessions.TipoCamaFacade;
import cl.hblt.sessions.TipoCamaFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import utils.appBean;

/**
 *
 * @author termiwum
 */
@ManagedBean
@SessionScoped
public class AsignacionCamaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    private final TipoCamaFacadeLocal tipoCamaFacade;
    @EJB
    private final EstadoPacienteFacadeLocal estadoPacienteFacade;
    @EJB
    private final IngresoHospitalizadosFacadeLocal ingresoHospitalizadosFacade;
    @EJB
    private final AsignacionCamaFacadeLocal asignacionCamaFacade;
    @EJB
    private final CamaFacadeLocal camaFacade;
    @EJB
    private final EspecialidadFacadeLocal especialidadFacade;
    @EJB
    private final BussinessFacadeLocal bussinessFacade;
    @EJB
    private final ApoderadoFacadeLocal apoderadoFacade;

    private ApoderadoBean apoderadoBean;
    private AsignacionCama asignacionCama;
    private List<Sala> listaSalasPorEspecialidad;
    private Cama cama;
    private Sala sala;
    private String rut, nroFicha;
    private Paciente pacienteBuscado;
    private List<Especialidad> listaEspecialidades;
    private List<Cama> listaCamasPorEspecialidad;
    private Especialidad especialidad, especialidadUsuario;
    private Especialidad procedencia;
    private Boolean ectopico = false;
    private List<EstadoPaciente> listEstadoPacientes;
    private List<TipoCama> listTipoCama;
    private IngresoHospitalizados ih;
    private TrasladoTemporal trasladoTemporal;
    private boolean editar;
    private boolean tarjetaApoderado;
    private boolean oirs;
    private Integer idEspecialidad;
    private String Seleccionar;

    /**
     * Creates a new instance of AsignacionCamaBean
     */
    public AsignacionCamaBean() {
        bussinessFacade = new BussinessFacade();
        apoderadoFacade = new ApoderadoFacade();
        especialidadFacade = new EspecialidadFacade();
        asignacionCama = new AsignacionCama();
        camaFacade = new CamaFacade();
        asignacionCamaFacade = new AsignacionCamaFacade();
        ingresoHospitalizadosFacade = new IngresoHospitalizadosFacade();
        estadoPacienteFacade = new EstadoPacienteFacade();
        tipoCamaFacade = new TipoCamaFacade();
        ih = new IngresoHospitalizados();
    }

    @PostConstruct
    public void myInitMethod() {
        especialidad = new Especialidad();
        cama = new Cama();
        sala = new Sala();
        procedencia = new Especialidad();
        listTipoCama = tipoCamaFacade.findAll();
        listaEspecialidades = especialidadFacade.findAll();
        //Especialidad de mi usuario logueado 
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        UsuarioSistema user = (UsuarioSistema) extContext.getSessionMap().get("ObjetoUsuario");
        oirs = user.getIdRol().getIdRol().equals(3);
        especialidadUsuario = user.getIdEspecialidad();
        //listo las camas activas de mi especialidad
        listaSalasPorEspecialidad
                = bussinessFacade.listSalasActivasByEspecialidad(especialidadUsuario);
        editar = false;
        tarjetaApoderado = false;
        Seleccionar = "Seleccione...";
    }
    ///////////////////////////////////////////////
    //INICIO DE LOS METODOS

    /**
     * *
     * Busca el Paciente del paso 1 de la vista
     *
     * @param event va por defecto
     */
    public void buscaPaciente(ActionEvent event) {
        boolean encontrado = false;
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext context1 = FacesContext.getCurrentInstance();
        FacesMessage message;
        //identificar si la busqueda la realiza por rut o por numero de ficha
        pacienteBuscado = null;
        if (!rut.isEmpty()) {
            //Busqueda por RUT
            String rut1 = rut.substring(0, rut.length() - 1);
            rut1 = rut1.replace("-", "");
            rut1 = rut1.replace(".", "");
            pacienteBuscado = bussinessFacade.findPacienteByRun(Integer.parseInt(rut1));
        } else if (!nroFicha.isEmpty()) {
            //busqueda por numero de ficha
            pacienteBuscado = bussinessFacade.findPacienteByNroFicha(nroFicha);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Debe Ingresar un Número de Ficha o un Rut de Paciente",
                    "Debe Ingresar un Número de Ficha o un Rut de Paciente");
            context1.addMessage(null, message);
        }
        //
        if (pacienteBuscado != null) {
            //Si el paciente es encontrado se debe validar que el paciente no 
            //se encuentre asignado a una cama
            if (bussinessFacade.pacienteHospitalizado(pacienteBuscado)) {
                encontrado = true;
            } else {
                //El Paciente ya esta asignado a una cama
                encontrado = false;
                pacienteBuscado = null;
                pacienteBuscado = new Paciente();
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "El paciente ya esta Asignado a una cama",
                        "El Paciente ya esta Asignado a una cama");
                context1.addMessage(null, message);
            }
        } else {
            pacienteBuscado = new Paciente();
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Paciente No Encontrado", "Paciente No Encontrado");
            context1.addMessage(null, message);
        }

        context.addCallbackParam("encontrado", encontrado);
    }

    public String getEstado(Integer id) {
        AsignacionCama asig = bussinessFacade.findUltimaAsignacionDelPaciente(id);
        EstadoPaciente estado = bussinessFacade.finEstadoById(asig.getIdEstadoPaciente().getIdEstadoPaciente());
        return estado.getDescripcionEstadoPaciente();
    }

    public String getDiagnostico(Integer id) {
        AsignacionCama asig = bussinessFacade.findUltimaAsignacionDelPaciente(id);
        return asig.getDiagnosticoSala();
    }

    public void create() {
        asignacionCama.setIdPaciente(new Paciente(pacienteBuscado.getIdPaciente()));
        String formato = "yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        Integer auxDate1 = Integer.parseInt(dateFormat.format(asignacionCama.getFechaAsignacion()));
        Integer auxDateActual = Integer.parseInt(dateFormat.format(new Date()));
        if (auxDate1.equals(auxDateActual)) {
            ih.getIdPaciente().setIdPaciente(pacienteBuscado.getIdPaciente());
            ih.setFechaIngreso(asignacionCama.getFechaAsignacion());
            ih.setIdApoderado(new Apoderado(1));
            if (ectopico) {
                short aux = 1;
                asignacionCama.setEctopico(aux);
            } else {
                short aux = 0;
                asignacionCama.setEctopico(aux);
            }
            short aux = 0;
            ih.setEgreso(aux);
            ih.setUbicacion("Especialidad: " + asignacionCama.getIdCama().getIdSala().getIdEspecialidad().getNombreEspecialidad() + " Sala: "
                    + asignacionCama.getIdCama().getIdSala().getNombreSala() + " Cama: " + asignacionCama.getIdCama().getNumeroCama());
            asignacionCamaFacade.create(asignacionCama);
            if (bussinessFacade.existeCierreDeIngreso(pacienteBuscado)) {
                ingresoHospitalizadosFacade.create(ih);
            }
            cambiarEstadoCama(asignacionCama.getIdCama());
            try {
                resetData();
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("PacientesHospitalizados.xhtml");
            } catch (IOException e) {

            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error en la Fecha de Ingreso no corresponde al Año en Curso", null));
        }
    }

    private void cambiarEstadoCama(Cama idCama) {
        idCama = camaFacade.find(idCama.getIdCama());
        if (idCama == null) {

        } else {
            idCama.setIdEstadoCama(new EstadoCama(1));
            camaFacade.edit(idCama);
        }
    }

    public void onValidaRut() {
        if (!rut.isEmpty()) {
            rut = rut.replace("-", "");
            rut = rut.replace(".", "");
            String rut1 = rut.substring(0, rut.length() - 1);
            String dv = rut.charAt(rut.length() - 1) + "";
            if (!appBean.validarRut(rut1, dv)) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Rut Invalido", "Rut Invalido"));
            }
        }
    }

    public void onEspecialidadChange() {
        this.especialidadUsuario = bussinessFacade.getEspecialidad(idEspecialidad);
        listaSalasPorEspecialidad
                = bussinessFacade.listSalasActivasByEspecialidad(especialidadUsuario);
        this.listTipoCama = tipoCamaFacade.findAll();

    }

    public void onSalaChange() {
        listaCamasPorEspecialidad
                = bussinessFacade.
                listCamasActivasDesocupadasBySala(
                        asignacionCama.getIdCama().getIdSala());
    }

    public void onCamaChange() {
        asignacionCama.getIdCama().
                getIdSala().
                setIdSala(asignacionCama.getIdCama().getIdSala().getIdSala());
    }

    public void resetData() {
        this.asignacionCama = new AsignacionCama();
        this.cama = new Cama();
        this.ectopico = false;
        this.especialidad = new Especialidad();
        this.especialidadUsuario = new Especialidad();
        this.procedencia = new Especialidad();
        this.pacienteBuscado = new Paciente();
        this.rut = "";
        this.nroFicha = "";
        this.sala = new Sala();
        this.ih = new IngresoHospitalizados();
        this.myInitMethod();
    }

    public void onCamaChangePrecarga() {
        asignacionCama.setIdPaciente(new Paciente(pacienteBuscado.getIdPaciente()));
        asignacionCama.getIdCama()
                .getIdSala()
                .getIdEspecialidad()
                .setNombreEspecialidad(especialidadUsuario.getNombreEspecialidad());
        for (Sala sala1 : listaSalasPorEspecialidad) {
            if (sala1.getIdSala().equals(asignacionCama.getIdCama().getIdSala().getIdSala())) {
                asignacionCama.getIdCama().getIdSala().setNombreSala(sala1.getNombreSala());
            }
        }
        for (Cama cama1 : listaCamasPorEspecialidad) {
            if (cama1.getIdCama().equals(asignacionCama.getIdCama().getIdCama())) {
                asignacionCama.getIdCama().setNumeroCama(cama1.getNumeroCama());
            }
        }
    }

    public void onTipoCamaChange() {
        listaCamasPorEspecialidad
                = bussinessFacade.
                listCamasActivasDesocupadasByEstadoCama(
                        asignacionCama.getIdCama().getIdSala(),
                        asignacionCama.getIdCama().getIdTipoCama());
        listEstadoPacientes
                = bussinessFacade.listEstadoPacienteByTipoCama(
                        asignacionCama.getIdCama().getIdTipoCama());
    }

    //Get Ubicacion Actual del paciente
    public String getUbicacionActual(Integer idPaciente) {
        String aux = bussinessFacade.getUbicacionActual(idPaciente);
        if (aux.equalsIgnoreCase("NoAsignada")) {
            TrasladoTemporal auxTras = bussinessFacade.findUltimoTrasladoTemporalByIdPaciente(idPaciente);
            if (auxTras != null) {
                Especialidad esp = bussinessFacade.getEspecialidad(auxTras.getIdEspecialidadTraslado());
                aux = "Trasladado a: " + esp.getNombreEspecialidad();
            } else {
                aux = "No Asignada";
            }
        }
        return aux;
    }
    //Asignacion de Apoderados

    public void create2() throws IOException {
        if (!editar) {
            //asignar cuando el apoderado esta creado
            ingresoHospitalizadosFacade.edit(ih);
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("PacientesHospitalizados.xhtml");
        } else {
            Apoderado apoderado = new Apoderado();
            rut = rut.replace("-", "");
            rut = rut.replace(".", "");
            String rut1 = rut.substring(0, rut.length() - 1);
            String dv = rut.charAt(rut.length() - 1) + "";
            if (bussinessFacade.checkApoderado(rut1)) {
                apoderado.setNombreApoderado(ih.getIdApoderado().getNombreApoderado().toUpperCase());
                apoderado.setPapellidoApoderado(ih.getIdApoderado().getPapellidoApoderado().toUpperCase());
                apoderado.setSapellidoApoderado(ih.getIdApoderado().getSapellidoApoderado().toUpperCase());
                Parentesco parentesco = bussinessFacade.getParentescoByID(ih.getIdApoderado().getIdParentesco().getIdParentesco());
                apoderado.setIdParentesco(parentesco);
                apoderado.setRunApoderado(Integer.parseInt(rut1));
                apoderado.setDvApoderado(dv.toUpperCase());
                apoderado.setNumContactoApoderado(ih.getIdApoderado().getNumContactoApoderado());
                if (tarjetaApoderado) {
                    short aux = 1;
                    apoderado.setTarjetaApoderado(aux);
                } else {
                    short aux = 0;
                    apoderado.setTarjetaApoderado(aux);
                }
                apoderadoFacade.create(apoderado);
                ih.setIdApoderado(apoderado);
                ingresoHospitalizadosFacade.edit(ih);
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("PacientesHospitalizados.xhtml");
            } else {
                apoderado.setIdApoderado(ih.getIdApoderado().getIdApoderado());
                apoderado.setNombreApoderado(ih.getIdApoderado().getNombreApoderado().toUpperCase());
                apoderado.setPapellidoApoderado(ih.getIdApoderado().getPapellidoApoderado().toUpperCase());
                apoderado.setSapellidoApoderado(ih.getIdApoderado().getSapellidoApoderado().toUpperCase());
                Parentesco parentesco = bussinessFacade.getParentescoByID(ih.getIdApoderado().getIdParentesco().getIdParentesco());
                apoderado.setIdParentesco(parentesco);
                apoderado.setRunApoderado(Integer.parseInt(rut1));
                apoderado.setDvApoderado(dv.toUpperCase());
                apoderado.setNumContactoApoderado(ih.getIdApoderado().getNumContactoApoderado());
                if (tarjetaApoderado) {
                    short aux = 1;
                    apoderado.setTarjetaApoderado(aux);
                } else {
                    short aux = 0;
                    apoderado.setTarjetaApoderado(aux);
                }
                apoderadoFacade.edit(apoderado);
                ih.setIdApoderado(apoderado);
                ingresoHospitalizadosFacade.edit(ih);
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("PacientesHospitalizados.xhtml");
            }
        }
    }

    public void loadDatosApoderado() {
        String rut1 = rut.substring(0, rut.length() - 1);
        rut1 = rut1.replace("-", "");
        rut1 = rut1.replace(".", "");
        if (!bussinessFacade.checkApoderado(rut1)) {
            ih.setIdApoderado(bussinessFacade.getApoderado(rut1));
            if (ih.getIdApoderado().getTarjetaApoderado().equals(1)) {
                tarjetaApoderado = true;
            } else {
                tarjetaApoderado = false;
            }
        } else {
            editar = true;
        }
    }

    /////////////////////////////////////////////////
    //GETTERS Y SETTERS
    public Boolean getEctopico() {
        return ectopico;
    }

    public void setEctopico(Boolean ectopico) {
        this.ectopico = ectopico;
    }

    public Especialidad getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(Especialidad procedencia) {
        this.procedencia = procedencia;
    }

    public AsignacionCama getAsignacionCama() {
        return asignacionCama;
    }

    public void setAsignacionCama(AsignacionCama asignacionCama) {
        this.asignacionCama = asignacionCama;
    }

    public void onFormateaRut() {
        if (!rut.isEmpty()) {
            this.setRut(appBean.FormateaRut(rut));
        }
    }

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Paciente getPacienteBuscado() {
        return pacienteBuscado;
    }

    public void setPacienteBuscado(Paciente pacienteBuscado) {
        this.pacienteBuscado = pacienteBuscado;
    }

    public List<Especialidad> getListaEspecialidades() {
        return listaEspecialidades;
    }

    public void setListaEspecialidades(List<Especialidad> listaEspecialidades) {
        this.listaEspecialidades = listaEspecialidades;
    }

    public List<Cama> getListaCamasPorEspecialidad() {
        return listaCamasPorEspecialidad;
    }

    public void setListaCamasPorEspecialidad(List<Cama> listaCamasPorEspecialidad) {
        this.listaCamasPorEspecialidad = listaCamasPorEspecialidad;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public List<Sala> getListaSalasPorEspecialidad() {
        return listaSalasPorEspecialidad;
    }

    public void setListaSalasPorEspecialidad(List<Sala> listaSalasPorEspecialidad) {
        this.listaSalasPorEspecialidad = listaSalasPorEspecialidad;
    }

    public Cama getCama() {
        return cama;
    }

    public void setCama(Cama cama) {
        this.cama = cama;
    }

    public Especialidad getEspecialidadUsuario() {
        return especialidadUsuario;
    }

    public void setEspecialidadUsuario(Especialidad especialidadUsuario) {
        this.especialidadUsuario = especialidadUsuario;
    }

    public String getNroFicha() {
        return nroFicha;
    }

    public void setNroFicha(String nroFicha) {
        this.nroFicha = nroFicha;
    }

    public List<EstadoPaciente> getListEstadoPacientes() {
        return listEstadoPacientes;
    }

    public void setListEstadoPacientes(List<EstadoPaciente> listEstadoPacientes) {
        this.listEstadoPacientes = listEstadoPacientes;
    }

    public List<TipoCama> getListTipoCama() {
        return listTipoCama;
    }

    public void setListTipoCama(List<TipoCama> listTipoCama) {
        this.listTipoCama = listTipoCama;
    }

    public IngresoHospitalizados getIh() {
        return ih;
    }

    public void setIh(IngresoHospitalizados ih) {
        this.ih = ih;
    }

    public TrasladoTemporal getTrasladoTemporal() {
        return trasladoTemporal;
    }

    public void setTrasladoTemporal(TrasladoTemporal trasladoTemporal) {
        this.trasladoTemporal = trasladoTemporal;
    }

    public ApoderadoBean getApoderadoBean() {
        return apoderadoBean;
    }

    public void setApoderadoBean(ApoderadoBean apoderadoBean) {
        this.apoderadoBean = apoderadoBean;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public boolean isTarjetaApoderado() {
        return tarjetaApoderado;
    }

    public void setTarjetaApoderado(boolean tarjetaApoderado) {
        this.tarjetaApoderado = tarjetaApoderado;
    }

    public boolean isOirs() {
        return oirs;
    }

    public void setOirs(boolean oirs) {
        this.oirs = oirs;
    }

    public String getSeleccionar() {
        return Seleccionar;
    }

}
