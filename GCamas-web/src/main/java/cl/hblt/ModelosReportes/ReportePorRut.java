/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.ModelosReportes;

import cl.hblt.entities.AsignacionCama;
import java.util.ArrayList;



public class ReportePorRut {
    private String periodo;
    private ArrayList<AsignacionCama> asignacion;

    public ReportePorRut() {
    }

    public ReportePorRut(String periodo, ArrayList<AsignacionCama> asignacion) {
        this.periodo = periodo;
        this.asignacion = asignacion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public ArrayList<AsignacionCama> getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(ArrayList<AsignacionCama> asignacion) {
        this.asignacion = asignacion;
    }

    
    

   
    
     
}
