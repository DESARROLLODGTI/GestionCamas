/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.beans;

import cl.hblt.ModelosReportes.ReportPorRut2;
import cl.hblt.ModelosReportes.ReportePorRut;
import cl.hblt.entities.AsignacionCama;
import cl.hblt.entities.EgresoHospitalizados;
import cl.hblt.entities.IngresoHospitalizados;
import cl.hblt.entities.Paciente;
import cl.hblt.sessions.BussinessFacade;
import cl.hblt.sessions.BussinessFacadeLocal;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ComponentSystemEvent;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import utils.appBean;

/**
 *
 * @author AndresEduardo
 */
@ManagedBean
@RequestScoped
public class reportesBean {

    @EJB
    private final BussinessFacadeLocal bussinessFacade;

    /**
     * Creates a new instance of reportesBean
     */
    private String rut;
    private Paciente paciente;
    private ArrayList<ReportePorRut> reportePorRut;
    private ArrayList<ReportPorRut2> reportePorRut2;
    private Date fecha1;
    private Date fecha2;
    private ArrayList<EgresoHospitalizados> reporteEgreso;
    private ArrayList<EgresoHospitalizados> filterReporteEgresos;

    public reportesBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        bussinessFacade = new BussinessFacade();
        if (extContext.getSessionMap().get("ReportPorRut") != null) {
            reportePorRut = (ArrayList<ReportePorRut>) extContext.getSessionMap().get("ReportPorRut");
            extContext.getSessionMap().put("ReportPorRut", null);
        } else {
            reportePorRut = new ArrayList<ReportePorRut>();
        }
        if (extContext.getSessionMap().get("ReportPorRut2") != null) {
            reportePorRut2 = (ArrayList<ReportPorRut2>) extContext.getSessionMap().get("ReportPorRut2");
            //extContext.getSessionMap().put("ReportPorRut2", null);
        } else {
            reportePorRut2 = new ArrayList<ReportPorRut2>();
        }
        if(extContext.getSessionMap().get("resultadoEgreso")!=null){
            reporteEgreso = (ArrayList<EgresoHospitalizados>) extContext.getSessionMap().get("resultadoEgreso");
        }else{
            reporteEgreso = new ArrayList<EgresoHospitalizados>();
        }
    }

    public void buscarPorRut2() throws IOException {
        rut = rut.replace("-", "");
        rut = rut.replace(".", "");
        String rut1 = rut.substring(0, rut.length() - 1);
        paciente = bussinessFacade.findPacienteByRun(Integer.parseInt(rut1));
        if (paciente != null) {
            List<IngresoHospitalizados> ihlist = bussinessFacade.TodosLosIngresoDelPaciente(paciente.getIdPaciente());
            List<EgresoHospitalizados> ehlist = bussinessFacade.TodosLosEgresosDelPaciente(paciente.getIdPaciente());
            int maxIndexIh = ihlist.size() - 1;
            int maxIndexEh = ehlist.size() - 1;
            int index;
            ArrayList<ReportPorRut2> reporteDePaciente = new ArrayList<ReportPorRut2>();
            for (index = 0; index <= maxIndexIh; index++) {
                if (index <= maxIndexEh) {
                    String aux = "Hospitalización  " + index;
                    ReportPorRut2 auxRepoDato = new ReportPorRut2(aux, ihlist.get(index), ehlist.get(index), paciente);
                    reporteDePaciente.add(auxRepoDato);
                } else {
                    String aux = "Hospitalización " + index;
                    ReportPorRut2 auxRepoDato = new ReportPorRut2(aux, ihlist.get(index), null, paciente);
                    reporteDePaciente.add(auxRepoDato);
                }
            }
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext extContext = context.getExternalContext();
            extContext.getSessionMap().put("ReportPorRut2", reporteDePaciente);
            extContext.redirect("resultReportPorRut.xhtml");
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rut No Posee Informacion de Hospitalización", "Rut  No valido"));
        }

    }

    public List<AsignacionCama> getListAsignacion(ReportPorRut2 datos) {
        if (datos.getEh() != null) {
            return bussinessFacade.getAsignacionesCamasPorFechasYIdPaciente(datos.getIh().getFechaIngreso(), datos.getEh().getFechaEgreso(),
                    datos.getPaciente().getIdPaciente());
        } else {
            return bussinessFacade.getAsignacionesCamasPorFechasYIdPaciente(datos.getIh().getFechaIngreso(), new Date(),
                    datos.getPaciente().getIdPaciente());
        }
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
        } else if (eh.equals(ih)) {
            return "Mismo Dia de Ingreso a otro servicio";
        } else {
            diferencia = (eh.getTime() - ih.getTime()) / appBean.MILLSECS_PER_DAY;
            return diferencia + " Días";
        }
    }

    public void crearExcel() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet Datos = workbook.createSheet();
        HSSFRow row = Datos.createRow(0);
        HSSFCell cell = row.createCell(0);
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        reportePorRut2 = (ArrayList<ReportPorRut2>) extContext.getSessionMap().get("ReportPorRut2");
        cell.setCellValue("Hospitalización");
        cell = row.createCell(1);
        cell.setCellValue("Fecha Ingreso");
        cell = row.createCell(2);
        cell.setCellValue("Fecha Egreso");
        cell = row.createCell(3);
        cell.setCellValue("Total Dias");
        int index = 0;
        DateFormat fechaHora = new SimpleDateFormat("dd-MM-yyyy");
        for (ReportPorRut2 aux : reportePorRut2) {
            //Cabezera Excel
            // Inidice de Fila en 1 para 2° Fila
            if (index > 0) {
                index++;
                index++;
                row = Datos.createRow(index);
                cell = row.createCell(0);
                cell.setCellValue("Hospitalización");
                cell = row.createCell(1);
                cell.setCellValue("Fecha Ingreso");
                cell = row.createCell(2);
                cell.setCellValue("Fecha Egreso");
                cell = row.createCell(3);
                cell.setCellValue("Total Dias");

            }
            index++;
            row = Datos.createRow(index);
            cell = row.createCell(0);
            cell.setCellValue(aux.getPerido());
            cell = row.createCell(1);
            cell.setCellValue(fechaHora.format(aux.getIh().getFechaIngreso()));
            cell = row.createCell(2);
            if (aux.getEh() != null) {
                cell.setCellValue(fechaHora.format(aux.getEh().getFechaEgreso()));
            } else {
                cell.setCellValue("No Registrada");
            }
            cell = row.createCell(3);
            if (aux.getEh() != null) {
                cell.setCellValue(calculaTotalDias(aux.getIh().getFechaIngreso(), aux.getEh().getFechaEgreso()));
            } else {
                cell.setCellValue(calculaTotalDias(aux.getIh().getFechaIngreso(), new Date()));
            }
            index++;
            row = Datos.createRow(index);
            cell = row.createCell(0);
            cell.setCellValue("Cama");
            cell = row.createCell(1);
            cell.setCellValue("Sala");
            cell = row.createCell(2);
            cell.setCellValue("Especialidad");
            cell = row.createCell(3);
            cell.setCellValue("Fecha Ingreso a la Cama");
            cell = row.createCell(4);
            cell.setCellValue("Fecha Egreso de la Cama");
            cell = row.createCell(5);
            cell.setCellValue("Total Dias");

            List<AsignacionCama> aux2 = getListAsignacion(aux);
            for (AsignacionCama asig : aux2) {
                index++;
                row = Datos.createRow(index);
                cell = row.createCell(0);
                cell.setCellValue("Cama " + asig.getIdCama().getIdCama());
                cell = row.createCell(1);
                cell.setCellValue(asig.getIdCama().getIdSala().getNombreSala());
                cell = row.createCell(2);
                cell.setCellValue(asig.getIdCama().getIdSala().getIdEspecialidad().getNombreEspecialidad());
                cell = row.createCell(3);
                cell.setCellValue(fechaHora.format(asig.getFechaAsignacion()));
                cell = row.createCell(4);
                if (asig.getFechaEgreso() != null) {
                    cell.setCellValue(fechaHora.format(asig.getFechaEgreso()));
                } else {
                    cell.setCellValue("NO Registrada");
                }
                cell = row.createCell(5);
                if (asig.getFechaEgreso() != null) {
                    cell.setCellValue(calculaTotalDias(asig.getFechaAsignacion(), asig.getFechaEgreso()));
                } else {
                    cell.setCellValue(calculaTotalDias(asig.getFechaAsignacion(), new Date()));
                }
            }

        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType(
                "application/vnd.ms-excel");
        externalContext.setResponseHeader(
                "Content-Disposition", "attachment; filename=\"ReportePorRut.xls\"");

        workbook.write(externalContext.getResponseOutputStream());
        facesContext.responseComplete();
    }

    public void onFormateaRut() {
        this.setRut(appBean.FormateaRut(rut));
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

    public void crearReporteEgresados() throws IOException {
        reporteEgreso = bussinessFacade.getEgresos(fecha1, fecha2);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("resultadoEgreso", reporteEgreso);
        FacesContext.getCurrentInstance().getExternalContext().redirect("resultEgresos.xhtml");
    }

    public void pullValuesFromFlash(ComponentSystemEvent e) {
        Flash flash = FacesContext.getCurrentInstance().
                getExternalContext().getFlash();
        reporteEgreso = (ArrayList<EgresoHospitalizados>) flash.get("datosResultEgresos");

    }
    
    public String getNombreApoderado(Paciente paciente){
        IngresoHospitalizados ih = bussinessFacade.obtenerIngresoHospitalizado(paciente);
        return ih.getIdApoderado().getNombreApoderado()+" "+ih.getIdApoderado().getPapellidoApoderado()+" " +
                ih.getIdApoderado().getSapellidoApoderado();
    }
    public String getNumeroContacto(Paciente paciente){
        IngresoHospitalizados ih = bussinessFacade.obtenerIngresoHospitalizado(paciente);
        return ih.getIdApoderado().getNumContactoApoderado();
    }

    public ArrayList<ReportePorRut> getReportePorRut() {
        return reportePorRut;
    }

    public void setReportePorRut(ArrayList<ReportePorRut> reportePorRut) {
        this.reportePorRut = reportePorRut;
    }

    public ArrayList<ReportPorRut2> getReportePorRut2() {
        return reportePorRut2;
    }

    public void setReportePorRut2(ArrayList<ReportPorRut2> reportePorRut2) {
        this.reportePorRut2 = reportePorRut2;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }

    public ArrayList<EgresoHospitalizados> getReporteEgreso() {
        return reporteEgreso;
    }

    public ArrayList<EgresoHospitalizados> getFilterReporteEgresos() {
        return filterReporteEgresos;
    }

    public void setFilterReporteEgresos(ArrayList<EgresoHospitalizados> filterReporteEgresos) {
        this.filterReporteEgresos = filterReporteEgresos;
    }
    

}
