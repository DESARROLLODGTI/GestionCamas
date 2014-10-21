/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.entities.AsignacionCama;
import cl.hblt.entities.Especialidad;
import cl.hblt.entities.IngresoHospitalizados;
import cl.hblt.entities.Paciente;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import cl.hblt.sessions.EspecialidadFacade;
import cl.hblt.sessions.EspecialidadFacadeLocal;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import utils.appBean;

/**
 *
 * @author termiwum
 */
@ManagedBean
@RequestScoped
public class ReportBean {

    @EJB
    private BussinessFacadeLocal bussinessFacade;
    @EJB
    private EspecialidadFacadeLocal especialidadFacade;

    private FacesContext fc;
    private Boolean allServicios;
    private List<Especialidad> listaEspecialidades;
    private Especialidad especialidad;
    private List<AsignacionCama> listHospitalizados;

    /**
     * Creates a new instance of ReportBean
     */
    public ReportBean() {
        fc = FacesContext.getCurrentInstance();
        allServicios = false;
        especialidadFacade = new EspecialidadFacade();
        bussinessFacade = new BussinessFacade();
        especialidad = new Especialidad();
    }

    public Boolean getAllServicios() {
        return allServicios;
    }

    public void setAllServicios(Boolean allServicios) {
        this.allServicios = allServicios;
    }

    public List<Especialidad> getListaEspecialidades() {
        return listaEspecialidades;
    }

    public void setListaEspecialidades(List<Especialidad> listaEspecialidades) {
        this.listaEspecialidades = listaEspecialidades;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public List<AsignacionCama> getListHospitalizados() {
        return listHospitalizados;
    }

    public void setListHospitalizados(List<AsignacionCama> listHospitalizados) {
        this.listHospitalizados = listHospitalizados;
    }

    public String generate() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        if (allServicios) {
            listHospitalizados = bussinessFacade.listAllHospitalizados();
            extContext.getSessionMap().put("RepoHosp", listHospitalizados);
            return "resultHospitalizados.xhtml";
        } else {
            Especialidad aux = especialidadFacade.find(especialidad.getIdEspecialidad());
            listHospitalizados = bussinessFacade.listHospitalizadosByEspecialidad(aux);
            extContext.getSessionMap().put("RepoHosp", listHospitalizados);
            return "resultHospitalizados.xhtml";
        }
    }
    public String generateEctopico() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        if (allServicios) {
            listHospitalizados = bussinessFacade.listAllEctopicos();
            extContext.getSessionMap().put("RepoHosp", listHospitalizados);
            return "resultEctopicos.xhtml";
        } else {
            Especialidad aux = especialidadFacade.find(especialidad.getIdEspecialidad());
            listHospitalizados = bussinessFacade.listEctopicosByEspecialidad(aux);
            extContext.getSessionMap().put("RepoHosp", listHospitalizados);
            return "resultEctopicos.xhtml";
        }
    }
    
     public String generateUrgenciaDiaNoche() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        if (allServicios) {
            listHospitalizados = bussinessFacade.listAllEctopicos();
            extContext.getSessionMap().put("RepoHosp", listHospitalizados);
            return "resultEctopicos.xhtml";
        } else {
            Especialidad aux = especialidadFacade.find(especialidad.getIdEspecialidad());
            listHospitalizados = bussinessFacade.listEctopicosByEspecialidad(aux);
            extContext.getSessionMap().put("RepoHosp", listHospitalizados);
            return "resultEctopicos.xhtml";
        }
    }
    
    public Date fechaDeIngreso(Paciente paciente){
        IngresoHospitalizados aux = bussinessFacade.obtenerIngresoHospitalizado(paciente);
        return aux.getFechaIngreso();
    }
    
     public Date horaDeIngreso(Paciente paciente){
        IngresoHospitalizados aux = bussinessFacade.obtenerIngresoHospitalizado(paciente);
        return aux.getHoraIngreso();
    }
    
    public String calculaTotalDias(Date ih, Date eh) {
        Date date = new Date();
        long diferencia;
        if (eh == null) {
            diferencia = (date.getTime() - ih.getTime()) / appBean.MILLSECS_PER_DAY;
            return diferencia + " Días";
        } else if (eh.before(ih)) {
            diferencia = (date.getTime() - ih.getTime()) / appBean.MILLSECS_PER_DAY;
            return diferencia + " Días";
        }else if(eh.equals(ih)){
            return 1 + " Días";
        } else {
            diferencia = (eh.getTime() - ih.getTime()) / appBean.MILLSECS_PER_DAY;
            return diferencia + " Días";
        }
    }

    public String getServEctopicoById(Integer id) {
        try {
            return especialidadFacade.find(id).getNombreEspecialidad();
        } catch (Exception e) {
            return "NO APLICA";
        }
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    @PostConstruct
    public void myInitMethod() {
        this.listaEspecialidades = especialidadFacade.findAll();
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        if (extContext.getSessionMap().get("RepoHosp") != null) {
            listHospitalizados = (List<AsignacionCama>) extContext.getSessionMap().get("RepoHosp");
        }
    }

}
