/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author termiwum
 */
@Entity
@Table(name = "control_acceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ControlAcceso.findAll", query = "SELECT c FROM ControlAcceso c"),
    @NamedQuery(name = "ControlAcceso.findByIdControlAcceso", query = "SELECT c FROM ControlAcceso c WHERE c.idControlAcceso = :idControlAcceso"),
    @NamedQuery(name = "ControlAcceso.findByFechaInicioAcceso", query = "SELECT c FROM ControlAcceso c WHERE c.fechaInicioAcceso = :fechaInicioAcceso")})
public class ControlAcceso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_control_acceso")
    private Integer idControlAcceso;
    @Column(name = "fecha_inicio_acceso")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioAcceso;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private UsuarioSistema idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idControlAcceso")
    private List<RegistroLog> registroLogList;

    public ControlAcceso() {
    }

    public ControlAcceso(Integer idControlAcceso) {
        this.idControlAcceso = idControlAcceso;
    }

    public Integer getIdControlAcceso() {
        return idControlAcceso;
    }

    public void setIdControlAcceso(Integer idControlAcceso) {
        this.idControlAcceso = idControlAcceso;
    }

    public Date getFechaInicioAcceso() {
        return fechaInicioAcceso;
    }

    public void setFechaInicioAcceso(Date fechaInicioAcceso) {
        this.fechaInicioAcceso = fechaInicioAcceso;
    }

    public UsuarioSistema getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UsuarioSistema idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<RegistroLog> getRegistroLogList() {
        return registroLogList;
    }

    public void setRegistroLogList(List<RegistroLog> registroLogList) {
        this.registroLogList = registroLogList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idControlAcceso != null ? idControlAcceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlAcceso)) {
            return false;
        }
        ControlAcceso other = (ControlAcceso) object;
        if ((this.idControlAcceso == null && other.idControlAcceso != null) || (this.idControlAcceso != null && !this.idControlAcceso.equals(other.idControlAcceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.ControlAcceso[ idControlAcceso=" + idControlAcceso + " ]";
    }
    
}
