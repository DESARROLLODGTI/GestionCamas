/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AndresEduardo
 */
@Entity
@Table(name = "estado_paciente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoPaciente.findAll", query = "SELECT e FROM EstadoPaciente e"),
    @NamedQuery(name = "EstadoPaciente.findByIdEstadoPaciente", query = "SELECT e FROM EstadoPaciente e WHERE e.idEstadoPaciente = :idEstadoPaciente"),
    @NamedQuery(name = "EstadoPaciente.findByDescripcionEstadoPaciente", query = "SELECT e FROM EstadoPaciente e WHERE e.descripcionEstadoPaciente = :descripcionEstadoPaciente"),
    @NamedQuery(name = "EstadoPaciente.findByIdTipoCama", query = "SELECT e FROM EstadoPaciente e WHERE e.idTipoCama = :idTipoCama")})
public class EstadoPaciente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estado_paciente")
    private Integer idEstadoPaciente;
    @Size(max = 2147483647)
    @Column(name = "descripcion_estado_paciente")
    private String descripcionEstadoPaciente;
    @Column(name = "id_tipo_cama")
    private Integer idTipoCama;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstadoPaciente")
    private List<AsignacionCama> asignacionCamaList;

    public EstadoPaciente() {
    }

    public EstadoPaciente(Integer idEstadoPaciente) {
        this.idEstadoPaciente = idEstadoPaciente;
    }

    public Integer getIdEstadoPaciente() {
        return idEstadoPaciente;
    }

    public void setIdEstadoPaciente(Integer idEstadoPaciente) {
        this.idEstadoPaciente = idEstadoPaciente;
    }

    public String getDescripcionEstadoPaciente() {
        return descripcionEstadoPaciente;
    }

    public void setDescripcionEstadoPaciente(String descripcionEstadoPaciente) {
        this.descripcionEstadoPaciente = descripcionEstadoPaciente;
    }

    public Integer getIdTipoCama() {
        return idTipoCama;
    }

    public void setIdTipoCama(Integer idTipoCama) {
        this.idTipoCama = idTipoCama;
    }

    @XmlTransient
    public List<AsignacionCama> getAsignacionCamaList() {
        return asignacionCamaList;
    }

    public void setAsignacionCamaList(List<AsignacionCama> asignacionCamaList) {
        this.asignacionCamaList = asignacionCamaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoPaciente != null ? idEstadoPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoPaciente)) {
            return false;
        }
        EstadoPaciente other = (EstadoPaciente) object;
        if ((this.idEstadoPaciente == null && other.idEstadoPaciente != null) || (this.idEstadoPaciente != null && !this.idEstadoPaciente.equals(other.idEstadoPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.EstadoPaciente[ idEstadoPaciente=" + idEstadoPaciente + " ]";
    }
    
}
