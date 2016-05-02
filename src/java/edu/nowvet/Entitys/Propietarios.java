/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Entitys;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Movimiento Rastafari
 */
@Entity
@Table(name = "propietarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propietarios.findAll", query = "SELECT p FROM Propietarios p"),
    @NamedQuery(name = "Propietarios.findByDniPropietario", query = "SELECT p FROM Propietarios p WHERE p.dniPropietario = :dniPropietario"),
    @NamedQuery(name = "Propietarios.findByBarrio", query = "SELECT p FROM Propietarios p WHERE p.barrio = :barrio")})
public class Propietarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dniPropietario")
    private Integer dniPropietario;
    @Size(max = 30)
    @Column(name = "barrio")
    private String barrio;
    @JoinColumn(name = "dniPropietario", referencedColumnName = "cedula", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuarios usuarios;
    @OneToMany(mappedBy = "codigoPropietario")
    private Collection<Servicios> serviciosCollection;
    @OneToMany(mappedBy = "codigoPropietario")
    private Collection<Mascotas> mascotasCollection;

    public Propietarios() {
    }

    public Propietarios(Integer dniPropietario) {
        this.dniPropietario = dniPropietario;
    }

    public Integer getDniPropietario() {
        return dniPropietario;
    }

    public void setDniPropietario(Integer dniPropietario) {
        this.dniPropietario = dniPropietario;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @XmlTransient
    public Collection<Servicios> getServiciosCollection() {
        return serviciosCollection;
    }

    public void setServiciosCollection(Collection<Servicios> serviciosCollection) {
        this.serviciosCollection = serviciosCollection;
    }

    @XmlTransient
    public Collection<Mascotas> getMascotasCollection() {
        return mascotasCollection;
    }

    public void setMascotasCollection(Collection<Mascotas> mascotasCollection) {
        this.mascotasCollection = mascotasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dniPropietario != null ? dniPropietario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propietarios)) {
            return false;
        }
        Propietarios other = (Propietarios) object;
        if ((this.dniPropietario == null && other.dniPropietario != null) || (this.dniPropietario != null && !this.dniPropietario.equals(other.dniPropietario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.nowvet.Entitys.Propietarios[ dniPropietario=" + dniPropietario + " ]";
    }
    
}
