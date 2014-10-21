/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hblt.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author termiwum
 */
@Entity
@Table(name = "cama")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cama.findAll", query = "SELECT c FROM Cama c"),
    @NamedQuery(name = "Cama.findByIdSala", query = "SELECT c FROM Cama c WHERE c.idSala = :idSala"),
    @NamedQuery(name = "Cama.findByIdCama", query = "SELECT c FROM Cama c WHERE c.idCama = :idCama"),
    @NamedQuery(name = "Cama.findByNumeroCama", query = "SELECT c FROM Cama c WHERE c.numeroCama = :numeroCama"),
    @NamedQuery(name = "Cama.findByIndActivo", query = "SELECT c FROM Cama c WHERE c.indActivo = :indActivo")})
public class Cama implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cama")
    private Integer idCama;
    @Column(name = "numero_cama")
    private BigInteger numeroCama;
    @Column(name = "ind_activo")
    private Short indActivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCama")
    private List<AsignacionCama> asignacionCamaList;
    @JoinColumn(name = "id_tipo_cama", referencedColumnName = "id_tipo_cama")
    @ManyToOne(optional = false)
    private TipoCama idTipoCama;
    @JoinColumn(name = "id_sala", referencedColumnName = "id_sala")
    @ManyToOne(optional = false)
    private Sala idSala;
    @JoinColumn(name = "id_estado_cama", referencedColumnName = "id_estado_cama")
    @ManyToOne(optional = false)
    private EstadoCama idEstadoCama;

    public Cama() {
        this.idSala = new Sala();
        this.idTipoCama = new TipoCama();
    }

    public Cama(Integer idCama) {
        this.idCama = idCama;
        this.idSala = new Sala();
        this.idTipoCama = new TipoCama();
    }

    public Integer getIdCama() {
        return idCama;
    }

    public void setIdCama(Integer idCama) {
        this.idCama = idCama;
    }

    public BigInteger getNumeroCama() {
        return numeroCama;
    }

    public void setNumeroCama(BigInteger numeroCama) {
        this.numeroCama = numeroCama;
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }

    @XmlTransient
    public List<AsignacionCama> getAsignacionCamaList() {
        return asignacionCamaList;
    }

    public void setAsignacionCamaList(List<AsignacionCama> asignacionCamaList) {
        this.asignacionCamaList = asignacionCamaList;
    }

    public TipoCama getIdTipoCama() {
        return idTipoCama;
    }

    public void setIdTipoCama(TipoCama idTipoCama) {
        this.idTipoCama = idTipoCama;
    }

    public Sala getIdSala() {
        return idSala;
    }

    public void setIdSala(Sala idSala) {
        this.idSala = idSala;
    }

    public EstadoCama getIdEstadoCama() {
        return idEstadoCama;
    }

    public void setIdEstadoCama(EstadoCama idEstadoCama) {
        this.idEstadoCama = idEstadoCama;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCama != null ? idCama.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cama)) {
            return false;
        }
        Cama other = (Cama) object;
        if ((this.idCama == null && other.idCama != null) || (this.idCama != null && !this.idCama.equals(other.idCama))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.Cama[ idCama=" + idCama + " ]";
    }
    
}
