/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author termiwum
 */
@Entity
@Table(name = "rol_opcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolOpcion.findAll", query = "SELECT r FROM RolOpcion r"),
    @NamedQuery(name = "RolOpcion.findByIdRolOpcion", query = "SELECT r FROM RolOpcion r WHERE r.idRolOpcion = :idRolOpcion"),
    @NamedQuery(name = "RolOpcion.findByOpcionAg", query = "SELECT r FROM RolOpcion r WHERE r.opcionAg = :opcionAg"),
    @NamedQuery(name = "RolOpcion.findByOpcionEd", query = "SELECT r FROM RolOpcion r WHERE r.opcionEd = :opcionEd"),
    @NamedQuery(name = "RolOpcion.findByOpcionAc", query = "SELECT r FROM RolOpcion r WHERE r.opcionAc = :opcionAc"),
    @NamedQuery(name = "RolOpcion.findByOpcionTp", query = "SELECT r FROM RolOpcion r WHERE r.opcionTp = :opcionTp"),
    @NamedQuery(name = "RolOpcion.findByOpcionEp", query = "SELECT r FROM RolOpcion r WHERE r.opcionEp = :opcionEp"),
    @NamedQuery(name = "RolOpcion.findByOpcionMep", query = "SELECT r FROM RolOpcion r WHERE r.opcionMep = :opcionMep"),
    @NamedQuery(name = "RolOpcion.findByOpcionAa", query = "SELECT r FROM RolOpcion r WHERE r.opcionAa = :opcionAa")})
public class RolOpcion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol_opcion")
    private Integer idRolOpcion;
    @Column(name = "opcion_ag")
    private Short opcionAg;
    @Column(name = "opcion_ed")
    private Short opcionEd;
    @Column(name = "opcion_ac")
    private Short opcionAc;
    @Column(name = "opcion_tp")
    private Short opcionTp;
    @Column(name = "opcion_ep")
    private Short opcionEp;
    @Column(name = "opcion_mep")
    private Short opcionMep;
    @Column(name = "opcion_aa")
    private Short opcionAa;
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @ManyToOne(optional = false)
    private Rol idRol;
    @JoinColumn(name = "id_opcion", referencedColumnName = "id_opcion")
    @ManyToOne(optional = false)
    private Opcion idOpcion;

    public RolOpcion() {
        this.idOpcion = new Opcion();
        this.idRol = new Rol();
    }

    public RolOpcion(Integer idRolOpcion) {
        this.idRolOpcion = idRolOpcion;
        this.idOpcion = new Opcion();
        this.idRol = new Rol();
    }

    public Integer getIdRolOpcion() {
        return idRolOpcion;
    }

    public void setIdRolOpcion(Integer idRolOpcion) {
        this.idRolOpcion = idRolOpcion;
    }

    public Short getOpcionAg() {
        return opcionAg;
    }

    public void setOpcionAg(Short opcionAg) {
        this.opcionAg = opcionAg;
    }

    public Short getOpcionEd() {
        return opcionEd;
    }

    public void setOpcionEd(Short opcionEd) {
        this.opcionEd = opcionEd;
    }

    public Short getOpcionAc() {
        return opcionAc;
    }

    public void setOpcionAc(Short opcionAc) {
        this.opcionAc = opcionAc;
    }

    public Short getOpcionTp() {
        return opcionTp;
    }

    public void setOpcionTp(Short opcionTp) {
        this.opcionTp = opcionTp;
    }

    public Short getOpcionEp() {
        return opcionEp;
    }

    public void setOpcionEp(Short opcionEp) {
        this.opcionEp = opcionEp;
    }

    public Short getOpcionMep() {
        return opcionMep;
    }

    public void setOpcionMep(Short opcionMep) {
        this.opcionMep = opcionMep;
    }

    public Short getOpcionAa() {
        return opcionAa;
    }

    public void setOpcionAa(Short opcionAa) {
        this.opcionAa = opcionAa;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    public Opcion getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Opcion idOpcion) {
        this.idOpcion = idOpcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolOpcion != null ? idRolOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolOpcion)) {
            return false;
        }
        RolOpcion other = (RolOpcion) object;
        if ((this.idRolOpcion == null && other.idRolOpcion != null) || (this.idRolOpcion != null && !this.idRolOpcion.equals(other.idRolOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.RolOpcion[ idRolOpcion=" + idRolOpcion + " ]";
    }
    
}
