/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.AsignacionCama;
import cl.hblt.entities.Cama;
import cl.hblt.entities.Especialidad;
import cl.hblt.entities.EstadoCama;
import cl.hblt.entities.EstadoPaciente;
import cl.hblt.entities.Paciente;
import cl.hblt.entities.Sala;
import cl.hblt.entities.TipoCama;
import cl.hblt.entities.TrasladoTemporal;
import cl.hblt.sessions.ApoderadoFacade;
import cl.hblt.sessions.ApoderadoFacadeLocal;
import cl.hblt.sessions.AsignacionCamaFacade;
import cl.hblt.sessions.AsignacionCamaFacadeLocal;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.CamaFacade;
import cl.hblt.sessions.CamaFacadeLocal;
import cl.hblt.sessions.PacienteFacade;
import cl.hblt.sessions.PacienteFacadeLocal;
import cl.hblt.sessions.TipoCamaFacade;
import cl.hblt.sessions.TipoCamaFacadeLocal;
import cl.hblt.sessions.TrasladoTemporalFacade;
import cl.hblt.sessions.TrasladoTemporalFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author AndresEduardo
 */
@ManagedBean
@ViewScoped
public class ttemporalBean implements Serializable {
    @EJB
    private final AsignacionCamaFacadeLocal asignacionCamaFacade;
    @EJB
    private final ApoderadoFacadeLocal apoderadoFacade;
    @EJB
    private final CamaFacadeLocal camaFacade;
    @EJB
    private final TipoCamaFacadeLocal tipoCamaFacade;
    @EJB
    private final PacienteFacadeLocal pacienteFacade;
    @EJB
    private final BussinessFacadeLocal bussinessFacade;
    @EJB
    private final TrasladoTemporalFacadeLocal trasladoTemporalFacade;
    private Especialidad EspecialidadTraslado;
    private TrasladoTemporal TrasladoTemp;
    private int contador;
    public List<TrasladoTemporal> filterTraslados;
    private Paciente paciente;
    private AsignacionCama AsignacionCama;
    private List<Sala> listaSalasPorEspecialidad;
    private List<Cama> listaCamasPorEspecialidad;
    private List<TipoCama> listTipoCama;
    private List<EstadoPaciente> listEstadoPacientes;
    public List<TrasladoTemporal> Traslados;
    private Boolean ectopico = false;

    /**
     * Creates a new instance of ttemporalBean Traslados Temporales
     */
    public ttemporalBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        if (extContext.getSessionMap().get("DatosAsignacion") != null) {
            TrasladoTemp = (TrasladoTemporal) extContext.getSessionMap().get("DatosAsignacion");
        } else {
            TrasladoTemp = new TrasladoTemporal();
        }
        bussinessFacade = new BussinessFacade();
        trasladoTemporalFacade = new TrasladoTemporalFacade();
        pacienteFacade = new PacienteFacade();
        tipoCamaFacade = new TipoCamaFacade();
        AsignacionCama = new AsignacionCama();
        camaFacade = new CamaFacade();
        apoderadoFacade = new ApoderadoFacade();
        asignacionCamaFacade = new AsignacionCamaFacade();
    }

    public String getEspecialidad(int aux) {
        return bussinessFacade.getEspecialidad(aux).getNombreEspecialidad();
    }

    public void cargarDatosTraslado(ActionEvent event) {

    }

    @PostConstruct
    public void myInitMethod() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        Integer id = (Integer) extContext.getSessionMap().get("idEspecialidad");
//        if(extContext.getSessionMap().get("DatosAsignacion")!=null){
//             TrasladoTemp = (TrasladoTemporal) extContext.getSessionMap().get("DatosAsignacion");
//        }else{
//         TrasladoTemp = new TrasladoTemporal();
//        }
        Traslados = bussinessFacade.getTrasladosTemporalesByIdEsp(id);
        contador = bussinessFacade.totalTrasladosPendientes(id);
        EspecialidadTraslado = (Especialidad) extContext.getSessionMap().get("Especialidad");
        //listo las camas activas de mi especialidad
        listaSalasPorEspecialidad
                = bussinessFacade.listSalasActivasByEspecialidad(EspecialidadTraslado);
        listTipoCama = tipoCamaFacade.findAll();
    }

    public void onSalaChange() {
        listaCamasPorEspecialidad
                = bussinessFacade.
                listCamasActivasDesocupadasBySala(
                        AsignacionCama.getIdCama().getIdSala());
    }

    public void onTipoCamaChange() {
        listaCamasPorEspecialidad
                = bussinessFacade.
                listCamasActivasDesocupadasByEstadoCama(
                        AsignacionCama.getIdCama().getIdSala(),
                        AsignacionCama.getIdCama().getIdTipoCama());
        listEstadoPacientes
                = bussinessFacade.listEstadoPacienteByTipoCama(
                        AsignacionCama.getIdCama().getIdTipoCama());
    }

    public void onCamaChange() {
        AsignacionCama.getIdCama().
                getIdSala().
                setIdSala(AsignacionCama.getIdCama().getIdSala().getIdSala());
    }

    public void create(ActionEvent event) throws IOException {
        if (ectopico) {
            short aux = 1;
            AsignacionCama.setEctopico(aux);
        } else {
            short aux = 0;
            AsignacionCama.setEctopico(aux);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        TrasladoTemp = (TrasladoTemporal) extContext.getSessionMap().get("DatosAsignacion");
        AsignacionCama.setIdPaciente(TrasladoTemp.getIdPaciente());
        AsignacionCama.setIdServicioProcedencia(TrasladoTemp.getIdEspecialidadProcedencia());
        Integer idAsignacionAux = bussinessFacade.findIdAsignacionMaxPaciente(TrasladoTemp.getIdPaciente().getIdPaciente());
        TrasladoTemp.setEstado('A');
        if (idAsignacionAux == 0) {
            asignacionCamaFacade.create(AsignacionCama);
            EstadoCama estado = bussinessFacade.findEstadoCamaById(1);
            Cama camaActual = bussinessFacade.findCamaById(AsignacionCama.getIdCama().getIdCama());
            camaActual.setIdEstadoCama(estado);
            camaFacade.edit(camaActual);
            trasladoTemporalFacade.edit(TrasladoTemp);
            extContext.redirect("TTemporales.xhtml");
        } else {
            AsignacionCama AsigAux = bussinessFacade.findUltimaAsignacionDelPaciente(idAsignacionAux);
            Date dia = new Date();
            AsigAux.setFechaEgreso(dia);
            asignacionCamaFacade.edit(AsigAux);
            asignacionCamaFacade.create(AsignacionCama);
            Cama camaAntigua = bussinessFacade.findCamaById(AsigAux.getIdCama().getIdCama());
            EstadoCama estado = bussinessFacade.findEstadoCamaById(2);
            camaAntigua.setIdEstadoCama(estado);
            camaFacade.edit(camaAntigua);
            estado = bussinessFacade.findEstadoCamaById(1);
            Cama camaActual = bussinessFacade.findCamaById(AsignacionCama.getIdCama().getIdCama());
            camaActual.setIdEstadoCama(estado);
            camaFacade.edit(camaActual);
            trasladoTemporalFacade.edit(TrasladoTemp);
            extContext.redirect("TTemporales.xhtml");
          }
    }

    public String getEstadoTraslado(char aux) {
        if (aux == 'P') {
            return "PENDIENTE";
        } else {
            if (aux == 'A') {
                return "ACEPTADO";
            } else {
                return "RECHAZADO";
            }
        }

    }

    public void pasoParametros() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        extContext.getSessionMap().put("DatosAsignacion", TrasladoTemp);
        String url = extContext.encodeActionURL(context.getApplication().getViewHandler().getActionURL(context, "/Application/TTemporal/AsignarCama.xhtml"));
        extContext.redirect(url);
    }

    public TrasladoTemporal getTrasladoTemp() {
        return TrasladoTemp;
    }

    public void setTrasladoTemp(TrasladoTemporal TrasladoTemp) {
        this.TrasladoTemp = TrasladoTemp;
    }

    public List<TrasladoTemporal> getTraslados() {
        return Traslados;
    }

    public void setTraslados(List<TrasladoTemporal> Traslados) {
        this.Traslados = Traslados;
    }

    public List<TrasladoTemporal> getFilterTraslados() {
        return filterTraslados;
    }

    public void setFilterTraslados(List<TrasladoTemporal> filterTraslados) {
        this.filterTraslados = filterTraslados;
    }

    public int getContador() {
        return contador;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Especialidad getEspecialidadTraslado() {
        return EspecialidadTraslado;
    }

    public void setEspecialidadTraslado(Especialidad EspecialidadTraslado) {
        this.EspecialidadTraslado = EspecialidadTraslado;
    }

    public AsignacionCama getAsignacionCama() {
        return AsignacionCama;
    }

    public void setAsignacionCama(AsignacionCama AsignacionCama) {
        this.AsignacionCama = AsignacionCama;
    }

    public List<Sala> getListaSalasPorEspecialidad() {
        return listaSalasPorEspecialidad;
    }

    public void setListaSalasPorEspecialidad(List<Sala> listaSalasPorEspecialidad) {
        this.listaSalasPorEspecialidad = listaSalasPorEspecialidad;
    }

    public List<Cama> getListaCamasPorEspecialidad() {
        return listaCamasPorEspecialidad;
    }

    public void setListaCamasPorEspecialidad(List<Cama> listaCamasPorEspecialidad) {
        this.listaCamasPorEspecialidad = listaCamasPorEspecialidad;
    }

    public List<TipoCama> getListTipoCama() {
        return listTipoCama;
    }

    public void setListTipoCama(List<TipoCama> listTipoCama) {
        this.listTipoCama = listTipoCama;
    }

    public List<EstadoPaciente> getListEstadoPacientes() {
        return listEstadoPacientes;
    }

    public void setListEstadoPacientes(List<EstadoPaciente> listEstadoPacientes) {
        this.listEstadoPacientes = listEstadoPacientes;
    }

    public Boolean getEctopico() {
        return ectopico;
    }

    public void setEctopico(Boolean ectopico) {
        this.ectopico = ectopico;
    }

}
