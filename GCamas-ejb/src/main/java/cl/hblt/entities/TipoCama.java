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
@Table(name = "tipo_cama")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCama.findAll", query = "SELECT t FROM TipoCama t"),
    @NamedQuery(name = "TipoCama.findByIdTipoCama", query = "SELECT t FROM TipoCama t WHERE t.idTipoCama = :idTipoCama"),
    @NamedQuery(name = "TipoCama.findByDescripcionTipoCama", query = "SELECT t FROM TipoCama t WHERE t.descripcionTipoCama = :descripcionTipoCama")})
public class TipoCama implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_cama")
    private Integer idTipoCama;
    @Size(max = 2147483647)
    @Column(name = "descripcion_tipo_cama")
    private String descripcionTipoCama;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoCama")
    private List<Cama> camaList;

    public TipoCama() {
    }

    public TipoCama(Integer idTipoCama) {
        this.idTipoCama = idTipoCama;
    }

    public Integer getIdTipoCama() {
        return idTipoCama;
    }

    public void setIdTipoCama(Integer idTipoCama) {
        this.idTipoCama = idTipoCama;
    }

    public String getDescripcionTipoCama() {
        return descripcionTipoCama;
    }

    public void setDescripcionTipoCama(String descripcionTipoCama) {
        this.descripcionTipoCama = descripcionTipoCama;
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
        hash += (idTipoCama != null ? idTipoCama.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCama)) {
            return false;
        }
        TipoCama other = (TipoCama) object;
        if ((this.idTipoCama == null && other.idTipoCama != null) || (this.idTipoCama != null && !this.idTipoCama.equals(other.idTipoCama))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.TipoCama[ idTipoCama=" + idTipoCama + " ]";
    }
    
}
