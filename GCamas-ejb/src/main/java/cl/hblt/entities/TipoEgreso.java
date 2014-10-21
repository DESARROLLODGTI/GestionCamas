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
 * @author termiwum
 */
@Entity
@Table(name = "tipo_egreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEgreso.findAll", query = "SELECT t FROM TipoEgreso t"),
    @NamedQuery(name = "TipoEgreso.findByIdTipoEgreso", query = "SELECT t FROM TipoEgreso t WHERE t.idTipoEgreso = :idTipoEgreso"),
    @NamedQuery(name = "TipoEgreso.findByDescripcion", query = "SELECT t FROM TipoEgreso t WHERE t.descripcion = :descripcion")})
public class TipoEgreso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_egreso")
    private Integer idTipoEgreso;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoEgreso")
    private List<EgresoHospitalizados> egresoHospitalizadosList;

    public TipoEgreso() {
    }

    public TipoEgreso(Integer idTipoEgreso) {
        this.idTipoEgreso = idTipoEgreso;
    }

    public Integer getIdTipoEgreso() {
        return idTipoEgreso;
    }

    public void setIdTipoEgreso(Integer idTipoEgreso) {
        this.idTipoEgreso = idTipoEgreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<EgresoHospitalizados> getEgresoHospitalizadosList() {
        return egresoHospitalizadosList;
    }

    public void setEgresoHospitalizadosList(List<EgresoHospitalizados> egresoHospitalizadosList) {
        this.egresoHospitalizadosList = egresoHospitalizadosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoEgreso != null ? idTipoEgreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEgreso)) {
            return false;
        }
        TipoEgreso other = (TipoEgreso) object;
        if ((this.idTipoEgreso == null && other.idTipoEgreso != null) || (this.idTipoEgreso != null && !this.idTipoEgreso.equals(other.idTipoEgreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.TipoEgreso[ idTipoEgreso=" + idTipoEgreso + " ]";
    }
    
}
