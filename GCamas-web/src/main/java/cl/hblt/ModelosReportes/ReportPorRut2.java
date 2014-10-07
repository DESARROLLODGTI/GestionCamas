/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.ModelosReportes;

import cl.hblt.entities.EgresoHospitalizados;
import cl.hblt.entities.IngresoHospitalizados;
import cl.hblt.entities.Paciente;

/**
 *
 * @author AndresEduardo
 */
public class ReportPorRut2 {
   private String Perido;
   private IngresoHospitalizados ih;
   private EgresoHospitalizados eh;
   private Paciente paciente;

    public ReportPorRut2() {
    }

    public ReportPorRut2(String Perido, IngresoHospitalizados ih, EgresoHospitalizados eh, Paciente paciente) {
        this.Perido = Perido;
        this.ih = ih;
        this.eh = eh;
        this.paciente = paciente;
    }


    public String getPerido() {
        return Perido;
    }

    public void setPerido(String Perido) {
        this.Perido = Perido;
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

    
   
   
}
