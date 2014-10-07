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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AndresEduardo
 */
@Entity
@Table(name = "egreso_hospitalizados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EgresoHospitalizados.findAll", query = "SELECT e FROM EgresoHospitalizados e"),
    @NamedQuery(name = "EgresoHospitalizados.findByIdEgreso", query = "SELECT e FROM EgresoHospitalizados e WHERE e.idEgreso = :idEgreso"),
    @NamedQuery(name = "EgresoHospitalizados.findByFechaEgreso", query = "SELECT e FROM EgresoHospitalizados e WHERE e.fechaEgreso = :fechaEgreso")})
public class EgresoHospitalizados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_egreso")
    private Integer idEgreso;
    @Column(name = "fecha_egreso")
    @Temporal(TemporalType.DATE)
    private Date fechaEgreso;
    @Column(name = "motivo_egreso")
    private String motivoEgreso;
    @JoinColumn(name = "id_tipo_egreso", referencedColumnName = "id_tipo_egreso")
    @ManyToOne(optional = false)
    private TipoEgreso idTipoEgreso;
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente")
    @ManyToOne(optional = false)
    private Paciente idPaciente;

    public EgresoHospitalizados() {
    }

    public EgresoHospitalizados(Integer idEgreso) {
        this.idEgreso = idEgreso;
    }

    public Integer getIdEgreso() {
        return idEgreso;
    }

    public void setIdEgreso(Integer idEgreso) {
        this.idEgreso = idEgreso;
    }

    public String getMotivoEgreso() {
        return motivoEgreso;
    }

    public void setMotivoEgreso(String motivoEgreso) {
        this.motivoEgreso = motivoEgreso;
    }
    

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public TipoEgreso getIdTipoEgreso() {
        return idTipoEgreso;
    }

    public void setIdTipoEgreso(TipoEgreso idTipoEgreso) {
        this.idTipoEgreso = idTipoEgreso;
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
        hash += (idEgreso != null ? idEgreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EgresoHospitalizados)) {
            return false;
        }
        EgresoHospitalizados other = (EgresoHospitalizados) object;
        if ((this.idEgreso == null && other.idEgreso != null) || (this.idEgreso != null && !this.idEgreso.equals(other.idEgreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.EgresoHospitalizados[ idEgreso=" + idEgreso + " ]";
    }

}
