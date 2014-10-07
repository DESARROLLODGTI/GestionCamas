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
 * @author AndresEduardo
 */
@Entity
@Table(name = "apoderado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apoderado.findAll", query = "SELECT a FROM Apoderado a"),
    @NamedQuery(name = "Apoderado.findByIdApoderado", query = "SELECT a FROM Apoderado a WHERE a.idApoderado = :idApoderado"),
    @NamedQuery(name = "Apoderado.findByRunApoderado", query = "SELECT a FROM Apoderado a WHERE a.runApoderado = :runApoderado"),
    @NamedQuery(name = "Apoderado.findByDvApoderado", query = "SELECT a FROM Apoderado a WHERE a.dvApoderado = :dvApoderado"),
    @NamedQuery(name = "Apoderado.findByNombreApoderado", query = "SELECT a FROM Apoderado a WHERE a.nombreApoderado = :nombreApoderado"),
    @NamedQuery(name = "Apoderado.findByPapellidoApoderado", query = "SELECT a FROM Apoderado a WHERE a.papellidoApoderado = :papellidoApoderado"),
    @NamedQuery(name = "Apoderado.findBySapellidoApoderado", query = "SELECT a FROM Apoderado a WHERE a.sapellidoApoderado = :sapellidoApoderado"),
    @NamedQuery(name = "Apoderado.findByNumContactoApoderado", query = "SELECT a FROM Apoderado a WHERE a.numContactoApoderado = :numContactoApoderado")})
public class Apoderado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_apoderado")
    private Integer idApoderado;
    @Column(name = "run_apoderado")
    private Integer runApoderado;
    @Size(max = 2147483647)
    @Column(name = "dv_apoderado")
    private String dvApoderado;
    @Column(name = "tarjeta_apoderado")
    private Short tarjetaApoderado;
    @Size(max = 2147483647)
    @Column(name = "nombre_apoderado")
    private String nombreApoderado;
    @Size(max = 2147483647)
    @Column(name = "papellido_apoderado")
    private String papellidoApoderado;
    @Size(max = 2147483647)
    @Column(name = "sapellido_apoderado")
    private String sapellidoApoderado;
    @Size(max = 2147483647)
    @Column(name = "num_contacto_apoderado")
    private String numContactoApoderado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idApoderado")
    private List<IngresoHospitalizados> ingresoHospitalizadosList;
    @JoinColumn(name = "id_parentesco", referencedColumnName = "id_parentesco")
    @ManyToOne(optional = false)
    private Parentesco idParentesco;

    public Apoderado() {
    }

    public Apoderado(Integer idApoderado) {
        this.idApoderado = idApoderado;
    }

    public Integer getIdApoderado() {
        return idApoderado;
    }

    public void setIdApoderado(Integer idApoderado) {
        this.idApoderado = idApoderado;
    }

    public Integer getRunApoderado() {
        return runApoderado;
    }

    public void setRunApoderado(Integer runApoderado) {
        this.runApoderado = runApoderado;
    }

    public String getDvApoderado() {
        return dvApoderado;
    }

    public void setDvApoderado(String dvApoderado) {
        this.dvApoderado = dvApoderado;
    }

    public String getNombreApoderado() {
        return nombreApoderado;
    }

    public void setNombreApoderado(String nombreApoderado) {
        this.nombreApoderado = nombreApoderado;
    }

    public String getPapellidoApoderado() {
        return papellidoApoderado;
    }

    public void setPapellidoApoderado(String papellidoApoderado) {
        this.papellidoApoderado = papellidoApoderado;
    }

    public String getSapellidoApoderado() {
        return sapellidoApoderado;
    }

    public void setSapellidoApoderado(String sapellidoApoderado) {
        this.sapellidoApoderado = sapellidoApoderado;
    }

    public String getNumContactoApoderado() {
        return numContactoApoderado;
    }

    public void setNumContactoApoderado(String numContactoApoderado) {
        this.numContactoApoderado = numContactoApoderado;
    }

    @XmlTransient
    public List<IngresoHospitalizados> getIngresoHospitalizadosList() {
        return ingresoHospitalizadosList;
    }

    public void setIngresoHospitalizadosList(List<IngresoHospitalizados> ingresoHospitalizadosList) {
        this.ingresoHospitalizadosList = ingresoHospitalizadosList;
    }

    public Parentesco getIdParentesco() {
        return idParentesco;
    }

    public void setIdParentesco(Parentesco idParentesco) {
        this.idParentesco = idParentesco;
    }

    public Short getTarjetaApoderado() {
        return tarjetaApoderado;
    }

    public void setTarjetaApoderado(Short tarjetaApoderado) {
        this.tarjetaApoderado = tarjetaApoderado;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idApoderado != null ? idApoderado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apoderado)) {
            return false;
        }
        Apoderado other = (Apoderado) object;
        if ((this.idApoderado == null && other.idApoderado != null) || (this.idApoderado != null && !this.idApoderado.equals(other.idApoderado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.Apoderado[ idApoderado=" + idApoderado + " ]";
    }
    
}
