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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author termiwum
 */
@Entity
@Table(name = "paciente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paciente.findAll", query = "SELECT p FROM Paciente p"),
    @NamedQuery(name = "Paciente.findByIdPaciente", query = "SELECT p FROM Paciente p WHERE p.idPaciente = :idPaciente"),
    @NamedQuery(name = "Paciente.findByRunPaciente", query = "SELECT p FROM Paciente p WHERE p.runPaciente = :runPaciente"),
    @NamedQuery(name = "Paciente.findByDvPaciente", query = "SELECT p FROM Paciente p WHERE p.dvPaciente = :dvPaciente"),
    @NamedQuery(name = "Paciente.findByNombre", query = "SELECT p FROM Paciente p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Paciente.findByPapellidoPaciente", query = "SELECT p FROM Paciente p WHERE p.papellidoPaciente = :papellidoPaciente"),
    @NamedQuery(name = "Paciente.findBySapellidoPaciente", query = "SELECT p FROM Paciente p WHERE p.sapellidoPaciente = :sapellidoPaciente"),
    @NamedQuery(name = "Paciente.findByFechaNacimiento", query = "SELECT p FROM Paciente p WHERE p.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Paciente.findByNumeroFicha", query = "SELECT p FROM Paciente p WHERE p.numeroFicha = :numeroFicha")})
public class Paciente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_paciente")
    private Integer idPaciente;
    @Column(name = "run_paciente")
    private Integer runPaciente;
    @Size(max = 2147483647)
    @Column(name = "dv_paciente")
    private String dvPaciente;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "papellido_paciente")
    private String papellidoPaciente;
    @Size(max = 2147483647)
    @Column(name = "sapellido_paciente")
    private String sapellidoPaciente;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Size(max = 2147483647)
    @Column(name = "numero_ficha")
    private String numeroFicha;
    @JoinColumn(name = "id_tipo_prevision", referencedColumnName = "id_tipo_prevision")
    @ManyToOne(optional = false)
    private TipoPrevision idTipoPrevision;
    @JoinColumn(name = "id_sexo", referencedColumnName = "id_sexo")
    @ManyToOne(optional = false)
    private Sexo idSexo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente")
    private List<IngresoHospitalizados> ingresoHospitalizadosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente")
    private List<AsignacionCama> asignacionCamaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente")
    private List<EgresoHospitalizados> egresoHospitalizadosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaciente")
    private List<TrasladoTemporal> trasladoTemporalList;

    public Paciente() {
        this.idTipoPrevision = new TipoPrevision();
        this.idSexo = new Sexo();
    }

    public Paciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
        this.idTipoPrevision = new TipoPrevision();
        this.idSexo = new Sexo();
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getRunPaciente() {
        return runPaciente;
    }

    public void setRunPaciente(Integer runPaciente) {
        this.runPaciente = runPaciente;
    }

    public String getDvPaciente() {
        return dvPaciente;
    }

    public void setDvPaciente(String dvPaciente) {
        this.dvPaciente = dvPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPapellidoPaciente() {
        return papellidoPaciente;
    }

    public void setPapellidoPaciente(String papellidoPaciente) {
        this.papellidoPaciente = papellidoPaciente;
    }

    public String getSapellidoPaciente() {
        return sapellidoPaciente;
    }

    public void setSapellidoPaciente(String sapellidoPaciente) {
        this.sapellidoPaciente = sapellidoPaciente;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNumeroFicha() {
        return numeroFicha;
    }

    public void setNumeroFicha(String numeroFicha) {
        this.numeroFicha = numeroFicha;
    }

    public TipoPrevision getIdTipoPrevision() {
        return idTipoPrevision;
    }

    public void setIdTipoPrevision(TipoPrevision idTipoPrevision) {
        this.idTipoPrevision = idTipoPrevision;
    }

    public Sexo getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Sexo idSexo) {
        this.idSexo = idSexo;
    }

    @XmlTransient
    public List<IngresoHospitalizados> getIngresoHospitalizadosList() {
        return ingresoHospitalizadosList;
    }

    public void setIngresoHospitalizadosList(List<IngresoHospitalizados> ingresoHospitalizadosList) {
        this.ingresoHospitalizadosList = ingresoHospitalizadosList;
    }

    @XmlTransient
    public List<AsignacionCama> getAsignacionCamaList() {
        return asignacionCamaList;
    }

    public void setAsignacionCamaList(List<AsignacionCama> asignacionCamaList) {
        this.asignacionCamaList = asignacionCamaList;
    }

    @XmlTransient
    public List<EgresoHospitalizados> getEgresoHospitalizadosList() {
        return egresoHospitalizadosList;
    }

    public void setEgresoHospitalizadosList(List<EgresoHospitalizados> egresoHospitalizadosList) {
        this.egresoHospitalizadosList = egresoHospitalizadosList;
    }

    @XmlTransient
    public List<TrasladoTemporal> getTrasladoTemporalList() {
        return trasladoTemporalList;
    }

    public void setTrasladoTemporalList(List<TrasladoTemporal> trasladoTemporalList) {
        this.trasladoTemporalList = trasladoTemporalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaciente != null ? idPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paciente)) {
            return false;
        }
        Paciente other = (Paciente) object;
        if ((this.idPaciente == null && other.idPaciente != null) || (this.idPaciente != null && !this.idPaciente.equals(other.idPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.Paciente[ idPaciente=" + idPaciente + " ]";
    }
    
}
