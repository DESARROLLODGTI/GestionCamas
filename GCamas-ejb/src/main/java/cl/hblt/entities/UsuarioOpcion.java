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
@Table(name = "usuario_opcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioOpcion.findAll", query = "SELECT u FROM UsuarioOpcion u"),
    @NamedQuery(name = "UsuarioOpcion.findByIdUsuarioOpcion", query = "SELECT u FROM UsuarioOpcion u WHERE u.idUsuarioOpcion = :idUsuarioOpcion"),
    @NamedQuery(name = "UsuarioOpcion.findByOpcionAg", query = "SELECT u FROM UsuarioOpcion u WHERE u.opcionAg = :opcionAg"),
    @NamedQuery(name = "UsuarioOpcion.findByOpcionEd", query = "SELECT u FROM UsuarioOpcion u WHERE u.opcionEd = :opcionEd"),
    @NamedQuery(name = "UsuarioOpcion.findByOpcionAc", query = "SELECT u FROM UsuarioOpcion u WHERE u.opcionAc = :opcionAc"),
    @NamedQuery(name = "UsuarioOpcion.findByOpcionTp", query = "SELECT u FROM UsuarioOpcion u WHERE u.opcionTp = :opcionTp"),
    @NamedQuery(name = "UsuarioOpcion.findByOpcionEp", query = "SELECT u FROM UsuarioOpcion u WHERE u.opcionEp = :opcionEp"),
    @NamedQuery(name = "UsuarioOpcion.findByOpcionMep", query = "SELECT u FROM UsuarioOpcion u WHERE u.opcionMep = :opcionMep"),
    @NamedQuery(name = "UsuarioOpcion.findByOpcionAa", query = "SELECT u FROM UsuarioOpcion u WHERE u.opcionAa = :opcionAa")})
public class UsuarioOpcion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario_opcion")
    private Integer idUsuarioOpcion;
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
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private UsuarioSistema idUsuario;
    @JoinColumn(name = "id_opcion", referencedColumnName = "id_opcion")
    @ManyToOne(optional = false)
    private Opcion idOpcion;

    public UsuarioOpcion() {
    }

    public UsuarioOpcion(Integer idUsuarioOpcion) {
        this.idUsuarioOpcion = idUsuarioOpcion;
    }

    public Integer getIdUsuarioOpcion() {
        return idUsuarioOpcion;
    }

    public void setIdUsuarioOpcion(Integer idUsuarioOpcion) {
        this.idUsuarioOpcion = idUsuarioOpcion;
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

    public UsuarioSistema getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UsuarioSistema idUsuario) {
        this.idUsuario = idUsuario;
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
        hash += (idUsuarioOpcion != null ? idUsuarioOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioOpcion)) {
            return false;
        }
        UsuarioOpcion other = (UsuarioOpcion) object;
        if ((this.idUsuarioOpcion == null && other.idUsuarioOpcion != null) || (this.idUsuarioOpcion != null && !this.idUsuarioOpcion.equals(other.idUsuarioOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.UsuarioOpcion[ idUsuarioOpcion=" + idUsuarioOpcion + " ]";
    }
    
}
