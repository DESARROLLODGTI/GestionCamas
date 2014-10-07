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
 * @author AndresEduardo
 */
@Entity
@Table(name = "ingreso_hospitalizados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IngresoHospitalizados.findAll", query = "SELECT i FROM IngresoHospitalizados i"),
    @NamedQuery(name = "IngresoHospitalizados.findByIdIngreso", query = "SELECT i FROM IngresoHospitalizados i WHERE i.idIngreso = :idIngreso"),
    @NamedQuery(name = "IngresoHospitalizados.findByMotivoIngreso", query = "SELECT i FROM IngresoHospitalizados i WHERE i.motivoIngreso = :motivoIngreso"),
    @NamedQuery(name = "IngresoHospitalizados.findByFechaIngreso", query = "SELECT i FROM IngresoHospitalizados i WHERE i.fechaIngreso = :fechaIngreso")})
public class IngresoHospitalizados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ingreso")
    private Integer idIngreso;
    @Size(max = 2147483647)
    @Column(name = "motivo_ingreso")
    private String motivoIngreso;
    @Size(max = 2147483647)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Column(name = "egreso")
    private Short egreso;
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente")
    @ManyToOne(optional = false)
    private Paciente idPaciente;
    @JoinColumn(name = "id_apoderado", referencedColumnName = "id_apoderado")
    @ManyToOne(optional = false)
    private Apoderado idApoderado;

    public IngresoHospitalizados() {
        idPaciente = new Paciente();
        this.idApoderado = new Apoderado();
    }

    public IngresoHospitalizados(Integer idIngreso) {
        this.idIngreso = idIngreso;
        idPaciente = new Paciente();
        this.idApoderado = new Apoderado();
    }

    public Integer getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(Integer idIngreso) {
        this.idIngreso = idIngreso;
    }

    public String getMotivoIngreso() {
        return motivoIngreso;
    }

    public void setMotivoIngreso(String motivoIngreso) {
        this.motivoIngreso = motivoIngreso;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Paciente getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Paciente idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Apoderado getIdApoderado() {
        return idApoderado;
    }

    public void setIdApoderado(Apoderado idApoderado) {
        this.idApoderado = idApoderado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Short getEgreso() {
        return egreso;
    }

    public void setEgreso(Short egreso) {
        this.egreso = egreso;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIngreso != null ? idIngreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IngresoHospitalizados)) {
            return false;
        }
        IngresoHospitalizados other = (IngresoHospitalizados) object;
        if ((this.idIngreso == null && other.idIngreso != null) || (this.idIngreso != null && !this.idIngreso.equals(other.idIngreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.IngresoHospitalizados[ idIngreso=" + idIngreso + " ]";
    }

}
