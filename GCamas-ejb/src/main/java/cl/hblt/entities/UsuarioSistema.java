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
@Table(name = "usuario_sistema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioSistema.findAll", query = "SELECT u FROM UsuarioSistema u"),
    @NamedQuery(name = "UsuarioSistema.findByIdUsuario", query = "SELECT u FROM UsuarioSistema u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "UsuarioSistema.findByRutUsuario", query = "SELECT u FROM UsuarioSistema u WHERE u.rutUsuario = :rutUsuario"),
    @NamedQuery(name = "UsuarioSistema.findByDvUsuario", query = "SELECT u FROM UsuarioSistema u WHERE u.dvUsuario = :dvUsuario"),
    @NamedQuery(name = "UsuarioSistema.findByNombreUsuario", query = "SELECT u FROM UsuarioSistema u WHERE u.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "UsuarioSistema.findByPapellidoUsuario", query = "SELECT u FROM UsuarioSistema u WHERE u.papellidoUsuario = :papellidoUsuario"),
    @NamedQuery(name = "UsuarioSistema.findBySapellidoUsuario", query = "SELECT u FROM UsuarioSistema u WHERE u.sapellidoUsuario = :sapellidoUsuario"),
    @NamedQuery(name = "UsuarioSistema.findByClaveAcceso", query = "SELECT u FROM UsuarioSistema u WHERE u.claveAcceso = :claveAcceso"),
    @NamedQuery(name = "UsuarioSistema.findByCorreoElectronico", query = "SELECT u FROM UsuarioSistema u WHERE u.correoElectronico = :correoElectronico"),
    @NamedQuery(name = "UsuarioSistema.findByIndActivo", query = "SELECT u FROM UsuarioSistema u WHERE u.indActivo = :indActivo")})
public class UsuarioSistema implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "rut_usuario")
    private Integer rutUsuario;
    @Size(max = 2147483647)
    @Column(name = "dv_usuario")
    private String dvUsuario;
    @Size(max = 2147483647)
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Size(max = 2147483647)
    @Column(name = "papellido_usuario")
    private String papellidoUsuario;
    @Size(max = 2147483647)
    @Column(name = "sapellido_usuario")
    private String sapellidoUsuario;
    @Size(max = 2147483647)
    @Column(name = "clave_acceso")
    private String claveAcceso;
    @Size(max = 2147483647)
    @Column(name = "correo_electronico")
    private String correoElectronico;
    @Column(name = "ind_activo")
    private Short indActivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<ControlAcceso> controlAccesoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<UsuarioOpcion> usuarioOpcionList;
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @ManyToOne(optional = false)
    private Rol idRol;
    @JoinColumn(name = "id_especialidad", referencedColumnName = "id_especialidad")
    @ManyToOne(optional = false)
    private Especialidad idEspecialidad;
    @JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo")
    @ManyToOne(optional = false)
    private Cargo idCargo;

    public UsuarioSistema() {
    }

    public UsuarioSistema(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    public String getDvUsuario() {
        return dvUsuario;
    }

    public void setDvUsuario(String dvUsuario) {
        this.dvUsuario = dvUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPapellidoUsuario() {
        return papellidoUsuario;
    }

    public void setPapellidoUsuario(String papellidoUsuario) {
        this.papellidoUsuario = papellidoUsuario;
    }

    public String getSapellidoUsuario() {
        return sapellidoUsuario;
    }

    public void setSapellidoUsuario(String sapellidoUsuario) {
        this.sapellidoUsuario = sapellidoUsuario;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }

    @XmlTransient
    public List<ControlAcceso> getControlAccesoList() {
        return controlAccesoList;
    }

    public void setControlAccesoList(List<ControlAcceso> controlAccesoList) {
        this.controlAccesoList = controlAccesoList;
    }

    @XmlTransient
    public List<UsuarioOpcion> getUsuarioOpcionList() {
        return usuarioOpcionList;
    }

    public void setUsuarioOpcionList(List<UsuarioOpcion> usuarioOpcionList) {
        this.usuarioOpcionList = usuarioOpcionList;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    public Especialidad getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Especialidad idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public Cargo getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Cargo idCargo) {
        this.idCargo = idCargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioSistema)) {
            return false;
        }
        UsuarioSistema other = (UsuarioSistema) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.UsuarioSistema[ idUsuario=" + idUsuario + " ]";
    }
    
}
