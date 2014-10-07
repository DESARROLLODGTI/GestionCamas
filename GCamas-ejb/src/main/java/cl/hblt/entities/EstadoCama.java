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
@Table(name = "estado_cama")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoCama.findAll", query = "SELECT e FROM EstadoCama e"),
    @NamedQuery(name = "EstadoCama.findByIdEstadoCama", query = "SELECT e FROM EstadoCama e WHERE e.idEstadoCama = :idEstadoCama"),
    @NamedQuery(name = "EstadoCama.findByDescripcionEstadoCama", query = "SELECT e FROM EstadoCama e WHERE e.descripcionEstadoCama = :descripcionEstadoCama")})
public class EstadoCama implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estado_cama")
    private Integer idEstadoCama;
    @Size(max = 2147483647)
    @Column(name = "descripcion_estado_cama")
    private String descripcionEstadoCama;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstadoCama")
    private List<Cama> camaList;

    public EstadoCama() {
    }

    public EstadoCama(Integer idEstadoCama) {
        this.idEstadoCama = idEstadoCama;
    }

    public Integer getIdEstadoCama() {
        return idEstadoCama;
    }

    public void setIdEstadoCama(Integer idEstadoCama) {
        this.idEstadoCama = idEstadoCama;
    }

    public String getDescripcionEstadoCama() {
        return descripcionEstadoCama;
    }

    public void setDescripcionEstadoCama(String descripcionEstadoCama) {
        this.descripcionEstadoCama = descripcionEstadoCama;
    }

    @XmlTransient
    public List<Cama> getCamaList() {
        return camaList;
    }

    public void setCamaList(List<Cama> camaList) {
        this.camaList = camaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoCama != null ? idEstadoCama.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoCama)) {
            return false;
        }
        EstadoCama other = (EstadoCama) object;
        if ((this.idEstadoCama == null && other.idEstadoCama != null) || (this.idEstadoCama != null && !this.idEstadoCama.equals(other.idEstadoCama))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.EstadoCama[ idEstadoCama=" + idEstadoCama + " ]";
    }
    
}
