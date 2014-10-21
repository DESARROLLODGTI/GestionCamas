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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author termiwum
 */
@Entity
@Table(name = "traslado_temporal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrasladoTemporal.findAll", query = "SELECT t FROM TrasladoTemporal t"),
    @NamedQuery(name = "TrasladoTemporal.findByIdTrasladoTemporal", query = "SELECT t FROM TrasladoTemporal t WHERE t.idTrasladoTemporal = :idTrasladoTemporal"),
    @NamedQuery(name = "TrasladoTemporal.findByMotivo", query = "SELECT t FROM TrasladoTemporal t WHERE t.motivo = :motivo"),
    @NamedQuery(name = "TrasladoTemporal.findByFecha", query = "SELECT t FROM TrasladoTemporal t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "TrasladoTemporal.findByHora", query = "SELECT t FROM TrasladoTemporal t WHERE t.hora = :hora"),
    @NamedQuery(name = "TrasladoTemporal.findByEstado", query = "SELECT t FROM TrasladoTemporal t WHERE t.estado = :estado"),
    @NamedQuery(name = "TrasladoTemporal.findByIdEspecialidadProcedencia", query = "SELECT t FROM TrasladoTemporal t WHERE t.idEspecialidadProcedencia = :idEspecialidadProcedencia"),
    @NamedQuery(name = "TrasladoTemporal.findByIdEspecialidadTraslado", query = "SELECT t FROM TrasladoTemporal t WHERE t.idEspecialidadTraslado = :idEspecialidadTraslado")})
public class TrasladoTemporal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_traslado_temporal")
    private Integer idTrasladoTemporal;
    @Size(max = 2147483647)
    @Column(name = "motivo")
    private String motivo;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private Character estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_especialidad_procedencia")
    private int idEspecialidadProcedencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_especialidad_traslado")
    private int idEspecialidadTraslado;
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente")
    @ManyToOne(optional = false)
    private Paciente idPaciente;

    public TrasladoTemporal() {
    this.idPaciente= new Paciente();
    }

    public TrasladoTemporal(Integer idTrasladoTemporal) {
        this.idTrasladoTemporal = idTrasladoTemporal;
    this.idPaciente= new Paciente();
    }

    public TrasladoTemporal(Integer idTrasladoTemporal, Character estado, int idEspecialidadProcedencia, int idEspecialidadTraslado) {
        this.idTrasladoTemporal = idTrasladoTemporal;
        this.estado = estado;
        this.idEspecialidadProcedencia = idEspecialidadProcedencia;
        this.idEspecialidadTraslado = idEspecialidadTraslado;
    this.idPaciente= new Paciente();
    }

    public Integer getIdTrasladoTemporal() {
        return idTrasladoTemporal;
    }

    public void setIdTrasladoTemporal(Integer idTrasladoTemporal) {
        this.idTrasladoTemporal = idTrasladoTemporal;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public int getIdEspecialidadProcedencia() {
        return idEspecialidadProcedencia;
    }

    public void setIdEspecialidadProcedencia(int idEspecialidadProcedencia) {
        this.idEspecialidadProcedencia = idEspecialidadProcedencia;
    }

    public int getIdEspecialidadTraslado() {
        return idEspecialidadTraslado;
    }

    public void setIdEspecialidadTraslado(int idEspecialidadTraslado) {
        this.idEspecialidadTraslado = idEspecialidadTraslado;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTrasladoTemporal != null ? idTrasladoTemporal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrasladoTemporal)) {
            return false;
        }
        TrasladoTemporal other = (TrasladoTemporal) object;
        if ((this.idTrasladoTemporal == null && other.idTrasladoTemporal != null) || (this.idTrasladoTemporal != null && !this.idTrasladoTemporal.equals(other.idTrasladoTemporal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.TrasladoTemporal[ idTrasladoTemporal=" + idTrasladoTemporal + " ]";
    }
    
}
