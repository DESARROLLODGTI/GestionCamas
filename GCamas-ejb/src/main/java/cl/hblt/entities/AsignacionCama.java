/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author termiwum
 */
@Entity
@Table(name = "asignacion_cama")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsignacionCama.findAll", query = "SELECT a FROM AsignacionCama a"),
    @NamedQuery(name = "AsignacionCama.findByIdAsignacion", query = "SELECT a FROM AsignacionCama a WHERE a.idAsignacion = :idAsignacion"),
    @NamedQuery(name = "AsignacionCama.findByFechaAsignacion", query = "SELECT a FROM AsignacionCama a WHERE a.fechaAsignacion = :fechaAsignacion"),
    @NamedQuery(name = "AsignacionCama.findByHoraAsignacion", query = "SELECT a FROM AsignacionCama a WHERE a.horaAsignacion = :horaAsignacion"),
    @NamedQuery(name = "AsignacionCama.findByFechaEgreso", query = "SELECT a FROM AsignacionCama a WHERE a.fechaEgreso = :fechaEgreso"),
    @NamedQuery(name = "AsignacionCama.findByHoraEgreso", query = "SELECT a FROM AsignacionCama a WHERE a.horaEgreso = :horaEgreso"),
    @NamedQuery(name = "AsignacionCama.findByEctopico", query = "SELECT a FROM AsignacionCama a WHERE a.ectopico = :ectopico"),
    @NamedQuery(name = "AsignacionCama.findByIdServicioProcedencia", query = "SELECT a FROM AsignacionCama a WHERE a.idServicioProcedencia = :idServicioProcedencia"),
    @NamedQuery(name = "AsignacionCama.findByIdServEctopico", query = "SELECT a FROM AsignacionCama a WHERE a.idServEctopico = :idServEctopico"),
    @NamedQuery(name = "AsignacionCama.findByDiagnosticoSala", query = "SELECT a FROM AsignacionCama a WHERE a.diagnosticoSala = :diagnosticoSala")})
public class AsignacionCama implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_asignacion")
    private Integer idAsignacion;
    @Column(name = "fecha_asignacion")
    @Temporal(TemporalType.DATE)
    private Date fechaAsignacion;
    @Column(name = "hora_asignacion")
    @Temporal(TemporalType.TIME)
    private Date horaAsignacion;
    @Column(name = "fecha_egreso")
    @Temporal(TemporalType.DATE)
    private Date fechaEgreso;
    @Column(name = "hora_egreso")
    @Temporal(TemporalType.TIME)
    private Date horaEgreso;
    @Column(name = "ectopico")
    private Short ectopico;
    @Column(name = "id_servicio_procedencia")
    private Integer idServicioProcedencia;
    @Column(name = "id_serv_ectopico")
    private Integer idServEctopico;
    @Size(max = 2147483647)
    @Column(name = "diagnostico_sala")
    private String diagnosticoSala;
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente")
    @ManyToOne(optional = false)
    private Paciente idPaciente;
    @JoinColumn(name = "id_estado_paciente", referencedColumnName = "id_estado_paciente")
    @ManyToOne(optional = false)
    private EstadoPaciente idEstadoPaciente;
    @JoinColumn(name = "id_cama", referencedColumnName = "id_cama")
    @ManyToOne(optional = false)
    private Cama idCama;

    public AsignacionCama() {
        this.idCama = new Cama();
        this.idPaciente = new Paciente();
        this.idEstadoPaciente = new EstadoPaciente();
    }

    public AsignacionCama(Integer idAsignacion) {
        this.idCama = new Cama();
        this.idPaciente = new Paciente();
        this.idAsignacion = idAsignacion;
    }

    public Integer getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(Integer idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getHoraAsignacion() {
        return horaAsignacion;
    }

    public void setHoraAsignacion(Date horaAsignacion) {
        this.horaAsignacion = horaAsignacion;
    }

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public Date getHoraEgreso() {
        return horaEgreso;
    }

    public void setHoraEgreso(Date horaEgreso) {
        this.horaEgreso = horaEgreso;
    }

    public Short getEctopico() {
        return ectopico;
    }

    public void setEctopico(Short ectopico) {
        this.ectopico = ectopico;
    }

    public Integer getIdServicioProcedencia() {
        return idServicioProcedencia;
    }

    public void setIdServicioProcedencia(Integer idServicioProcedencia) {
        this.idServicioProcedencia = idServicioProcedencia;
    }

    public Integer getIdServEctopico() {
        return idServEctopico;
    }

    public void setIdServEctopico(Integer idServEctopico) {
        this.idServEctopico = idServEctopico;
    }

    public String getDiagnosticoSala() {
        return diagnosticoSala;
    }

    public void setDiagnosticoSala(String diagnosticoSala) {
        this.diagnosticoSala = diagnosticoSala;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    public EstadoPaciente getIdEstadoPaciente() {
        return idEstadoPaciente;
    }

    public void setIdEstadoPaciente(EstadoPaciente idEstadoPaciente) {
        this.idEstadoPaciente = idEstadoPaciente;
    }

    public Cama getIdCama() {
        return idCama;
    }

    public void setIdCama(Cama idCama) {
        this.idCama = idCama;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsignacion != null ? idAsignacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsignacionCama)) {
            return false;
        }
        AsignacionCama other = (AsignacionCama) object;
        if ((this.idAsignacion == null && other.idAsignacion != null) || (this.idAsignacion != null && !this.idAsignacion.equals(other.idAsignacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.AsignacionCama[ idAsignacion=" + idAsignacion + " ]";
    }
    
}
