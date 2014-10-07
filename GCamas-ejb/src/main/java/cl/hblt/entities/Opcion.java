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
@Table(name = "opcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Opcion.findAll", query = "SELECT o FROM Opcion o"),
    @NamedQuery(name = "Opcion.findByIdOpcion", query = "SELECT o FROM Opcion o WHERE o.idOpcion = :idOpcion"),
    @NamedQuery(name = "Opcion.findByNombreOpcion", query = "SELECT o FROM Opcion o WHERE o.nombreOpcion = :nombreOpcion"),
    @NamedQuery(name = "Opcion.findByPaginaOpcion", query = "SELECT o FROM Opcion o WHERE o.paginaOpcion = :paginaOpcion"),
    @NamedQuery(name = "Opcion.findByIcon", query = "SELECT o FROM Opcion o WHERE o.icon = :icon")})
public class Opcion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_opcion")
    private Integer idOpcion;
    @Size(max = 2147483647)
    @Column(name = "nombre_opcion")
    private String nombreOpcion;
    @Size(max = 2147483647)
    @Column(name = "pagina_opcion")
    private String paginaOpcion;
    @Size(max = 2147483647)
    @Column(name = "icon")
    private String icon;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOpcion")
    private List<RolOpcion> rolOpcionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOpcion")
    private List<UsuarioOpcion> usuarioOpcionList;
    @JoinColumn(name = "id_menu", referencedColumnName = "id_menu")
    @ManyToOne
    private Menu idMenu;

    public Opcion() {
        this.idMenu = new Menu();
    }

    public Opcion(Integer idOpcion) {
        this.idOpcion = idOpcion;
        this.idMenu = new Menu();
    }

    public Integer getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }

    public String getPaginaOpcion() {
        return paginaOpcion;
    }

    public void setPaginaOpcion(String paginaOpcion) {
        this.paginaOpcion = paginaOpcion;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @XmlTransient
    public List<RolOpcion> getRolOpcionList() {
        return rolOpcionList;
    }

    public void setRolOpcionList(List<RolOpcion> rolOpcionList) {
        this.rolOpcionList = rolOpcionList;
    }

    @XmlTransient
    public List<UsuarioOpcion> getUsuarioOpcionList() {
        return usuarioOpcionList;
    }

    public void setUsuarioOpcionList(List<UsuarioOpcion> usuarioOpcionList) {
        this.usuarioOpcionList = usuarioOpcionList;
    }

    public Menu getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Menu idMenu) {
        this.idMenu = idMenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOpcion != null ? idOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Opcion)) {
            return false;
        }
        Opcion other = (Opcion) object;
        if ((this.idOpcion == null && other.idOpcion != null) || (this.idOpcion != null && !this.idOpcion.equals(other.idOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.hblt.entities.Opcion[ idOpcion=" + idOpcion + " ]";
    }
    
}
