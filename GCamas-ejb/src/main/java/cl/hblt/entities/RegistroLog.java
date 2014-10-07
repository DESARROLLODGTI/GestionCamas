/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cl.hblt.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AndresEduardo
 */
@Entity
@Table(name = "registro_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroLog.findAll", query = "SELECT r FROM RegistroLog r"),
    @NamedQuery(name = "RegistroLog.findByIdRegistroLog", query = "SELECT r FROM RegistroLog r WHERE r.idRegistroLog = :idRegistroLog"),
    @NamedQuery(name = "RegistroLog.findByFechaModificacion", query = "SELECT r FROM RegistroLog r WHERE r.fechaModificacion = :fechaModificacion"),
    @NamedQuery(name = "RegistroLog.findByTabla", query = "SELECT r FROM RegistroLog r WHERE r.tabla = :tabla"),
    @NamedQuery(name = "RegistroLog.findByCampo", query = "SELECT r FROM RegistroLog r WHERE r.campo = :campo"),
    @NamedQuery(name = "RegistroLog.findByValorAnterior", query = "SELECT r FROM RegistroLog r WHERE r.valorAnterior = :valorAnterior"),
    @NamedQuery(name = "RegistroLog.findByValorNuevo", query = "SELECT r FROM RegistroLog r WHERE r.valorNuevo = :valorNuevo")})
public class RegistroLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_registro_log")
    private Integer idRegistroLog;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaModificacion;
    @Size(max = 2147483647)
    @Column(name = "tabla")
    private String tabla;
    @Size(max = 2147483647)
    @Column(name = "campo")
    private String campo;
    @Size(max = 2147483647)
    @Column(name = "valor_anterior")
    private String valorAnterior;
    @Size(max = 2147483647)
    @Column(name = "valor_nuevo")
    private String valorNuevo;
    @JoinColumn(name = "id_control_acceso", referencedColumnName = "id_control_acceso")
    @ManyToOne(optional = false)
    private ControlAcceso idControlAcceso;

    public RegistroLog() {
    }

    public RegistroLog(Integer idRegistroLog) {
        this.idRegistroLog = idRegistroLog;
    }

    public Integer getIdRegistroLog() {
        return idRegistroLog;
    }

    public void setIdRegistroLog(Integer idRegistroLog) {
        this.idRegistroLog = idRegistroLog;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(String valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public String getValorNuevo() {
        return valorNuevo;
    }

    public void setValorNuevo(String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    public ControlAcceso getIdControlAcceso() {
        return idControlAcceso;
    }

    public void setIdControlAcceso(ControlAcceso idControlAcceso) {
        this.idControlAcceso = idControlAcceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistroLog != null ? idRegistroLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroLog)) {
            return false;
        }
        RegistroLog other = (RegistroLog) object;
        if ((this.idRegistroLog == null && other.idRegistroLog != null) || (this.idRegistroLog != null && !this.idRegistroLog.equals(other.idRegistroLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.RegistroLog[ idRegistroLog=" + idRegistroLog + " ]";
    }
    
}
