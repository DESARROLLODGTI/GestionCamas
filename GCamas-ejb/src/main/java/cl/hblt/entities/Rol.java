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
@Table(name = "rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
    @NamedQuery(name = "Rol.findByIdRol", query = "SELECT r FROM Rol r WHERE r.idRol = :idRol"),
    @NamedQuery(name = "Rol.findByDescripcionRol", query = "SELECT r FROM Rol r WHERE r.descripcionRol = :descripcionRol"),
    @NamedQuery(name = "Rol.findByIndActivo", query = "SELECT r FROM Rol r WHERE r.indActivo = :indActivo")})
public class Rol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol")
    private Integer idRol;
    @Size(max = 2147483647)
    @Column(name = "descripcion_rol")
    private String descripcionRol;
    @Column(name = "ind_activo")
    private Short indActivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRol")
    private List<RolOpcion> rolOpcionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRol")
    private List<UsuarioSistema> usuarioSistemaList;

    public Rol() {
    }

    public Rol(Integer idRol) {
        this.idRol = idRol;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }

    @XmlTransient
    public List<RolOpcion> getRolOpcionList() {
        return rolOpcionList;
    }

    public void setRolOpcionList(List<RolOpcion> rolOpcionList) {
        this.rolOpcionList = rolOpcionList;
    }

    @XmlTransient
    public List<UsuarioSistema> getUsuarioSistemaList() {
        return usuarioSistemaList;
    }

    public void setUsuarioSistemaList(List<UsuarioSistema> usuarioSistemaList) {
        this.usuarioSistemaList = usuarioSistemaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.Rol[ idRol=" + idRol + " ]";
    }
    
}
