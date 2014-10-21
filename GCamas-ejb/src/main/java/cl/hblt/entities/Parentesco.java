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
@Table(name = "parentesco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parentesco.findAll", query = "SELECT p FROM Parentesco p"),
    @NamedQuery(name = "Parentesco.findByIdParentesco", query = "SELECT p FROM Parentesco p WHERE p.idParentesco = :idParentesco"),
    @NamedQuery(name = "Parentesco.findByDescripcionParentesco", query = "SELECT p FROM Parentesco p WHERE p.descripcionParentesco = :descripcionParentesco")})
public class Parentesco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parentesco")
    private Integer idParentesco;
    @Size(max = 2147483647)
    @Column(name = "descripcion_parentesco")
    private String descripcionParentesco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idParentesco")
    private List<Apoderado> apoderadoList;

    public Parentesco() {
    }

    public Parentesco(Integer idParentesco) {
        this.idParentesco = idParentesco;
    }

    public Integer getIdParentesco() {
        return idParentesco;
    }

    public void setIdParentesco(Integer idParentesco) {
        this.idParentesco = idParentesco;
    }

    public String getDescripcionParentesco() {
        return descripcionParentesco;
    }

    public void setDescripcionParentesco(String descripcionParentesco) {
        this.descripcionParentesco = descripcionParentesco;
    }

    @XmlTransient
    public List<Apoderado> getApoderadoList() {
        return apoderadoList;
    }

    public void setApoderadoList(List<Apoderado> apoderadoList) {
        this.apoderadoList = apoderadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParentesco != null ? idParentesco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parentesco)) {
            return false;
        }
        Parentesco other = (Parentesco) object;
        if ((this.idParentesco == null && other.idParentesco != null) || (this.idParentesco != null && !this.idParentesco.equals(other.idParentesco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.Parentesco[ idParentesco=" + idParentesco + " ]";
    }
    
}
