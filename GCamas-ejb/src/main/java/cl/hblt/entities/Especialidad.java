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
@Table(name = "especialidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Especialidad.findAll", query = "SELECT e FROM Especialidad e"),
    @NamedQuery(name = "Especialidad.findByIdEspecialidad", query = "SELECT e FROM Especialidad e WHERE e.idEspecialidad = :idEspecialidad"),
    @NamedQuery(name = "Especialidad.findByNombreEspecialidad", query = "SELECT e FROM Especialidad e WHERE e.nombreEspecialidad = :nombreEspecialidad"),
    @NamedQuery(name = "Especialidad.findByIndActivo", query = "SELECT e FROM Especialidad e WHERE e.indActivo = :indActivo")})
public class Especialidad implements Serializable {
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEspecialidad")
 
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_especialidad")
    private Integer idEspecialidad;
    @Size(max = 2147483647)
    @Column(name = "nombre_especialidad")
    private String nombreEspecialidad;
    @Column(name = "ind_activo")
    private Short indActivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEspecialidad")
    private List<UsuarioSistema> usuarioSistemaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEspecialidad")
    private List<Sala> salaList;

    public Especialidad() {
    }

    public Especialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }

    @XmlTransient
    public List<UsuarioSistema> getUsuarioSistemaList() {
        return usuarioSistemaList;
    }

    public void setUsuarioSistemaList(List<UsuarioSistema> usuarioSistemaList) {
        this.usuarioSistemaList = usuarioSistemaList;
    }

    @XmlTransient
    public List<Sala> getSalaList() {
        return salaList;
    }

    public void setSalaList(List<Sala> salaList) {
        this.salaList = salaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEspecialidad != null ? idEspecialidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Especialidad)) {
            return false;
        }
        Especialidad other = (Especialidad) object;
        if ((this.idEspecialidad == null && other.idEspecialidad != null) || (this.idEspecialidad != null && !this.idEspecialidad.equals(other.idEspecialidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.Especialidad[ idEspecialidad=" + idEspecialidad + " ]";
    }

  
    
}
