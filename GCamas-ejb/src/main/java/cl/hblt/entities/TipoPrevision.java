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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tipo_prevision")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPrevision.findAll", query = "SELECT t FROM TipoPrevision t"),
    @NamedQuery(name = "TipoPrevision.findByIdTipoPrevision", query = "SELECT t FROM TipoPrevision t WHERE t.idTipoPrevision = :idTipoPrevision"),
    @NamedQuery(name = "TipoPrevision.findByDescripcionTipoPrevision", query = "SELECT t FROM TipoPrevision t WHERE t.descripcionTipoPrevision = :descripcionTipoPrevision")})
public class TipoPrevision implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_prevision")
    private Integer idTipoPrevision;
    @Size(max = 2147483647)
    @Column(name = "descripcion_tipo_prevision")
    private String descripcionTipoPrevision;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoPrevision")
    private List<Paciente> pacienteList;
    @JoinColumn(name = "id_prevision", referencedColumnName = "id_prevision")
    @ManyToOne(optional = false)
    private Prevision idPrevision;

    public TipoPrevision() {
        this.idPrevision = new Prevision();
    }

    public TipoPrevision(Integer idTipoPrevision) {
        this.idPrevision = new Prevision();
        this.idTipoPrevision = idTipoPrevision;
    }

    public Integer getIdTipoPrevision() {
        return idTipoPrevision;
    }

    public void setIdTipoPrevision(Integer idTipoPrevision) {
        this.idTipoPrevision = idTipoPrevision;
    }

    public String getDescripcionTipoPrevision() {
        return descripcionTipoPrevision;
    }

    public void setDescripcionTipoPrevision(String descripcionTipoPrevision) {
        this.descripcionTipoPrevision = descripcionTipoPrevision;
    }

    @XmlTransient
    public List<Paciente> getPacienteList() {
        return pacienteList;
    }

    public void setPacienteList(List<Paciente> pacienteList) {
        this.pacienteList = pacienteList;
    }

    public Prevision getIdPrevision() {
        return idPrevision;
    }

    public void setIdPrevision(Prevision idPrevision) {
        this.idPrevision = idPrevision;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPrevision != null ? idTipoPrevision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPrevision)) {
            return false;
        }
        TipoPrevision other = (TipoPrevision) object;
        if ((this.idTipoPrevision == null && other.idTipoPrevision != null) || (this.idTipoPrevision != null && !this.idTipoPrevision.equals(other.idTipoPrevision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.TipoPrevision[ idTipoPrevision=" + idTipoPrevision + " ]";
    }
    
}
