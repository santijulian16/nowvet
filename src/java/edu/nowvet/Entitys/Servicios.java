/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Entitys;

import java.io.Serializable;
import java.util.Collection;
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
 * @author Movimiento Rastafari
 */
@Entity
@Table(name = "servicios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicios.findAll", query = "SELECT s FROM Servicios s"),
    @NamedQuery(name = "Servicios.findByCodgoServicio", query = "SELECT s FROM Servicios s WHERE s.codgoServicio = :codgoServicio"),
    @NamedQuery(name = "Servicios.findByDescripcionServicio", query = "SELECT s FROM Servicios s WHERE s.descripcionServicio = :descripcionServicio"),
    @NamedQuery(name = "Servicios.findByTipo", query = "SELECT s FROM Servicios s WHERE s.tipo = :tipo"),
    @NamedQuery(name = "Servicios.findByValor", query = "SELECT s FROM Servicios s WHERE s.valor = :valor")})
public class Servicios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codgoServicio")
    private Integer codgoServicio;
    @Size(max = 250)
    @Column(name = "descripcionServicio")
    private String descripcionServicio;
    @Size(max = 17)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "valor")
    private Integer valor;
    @JoinColumn(name = "codigoPropietario", referencedColumnName = "dniPropietario")
    @ManyToOne
    private Propietarios codigoPropietario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idServicio")
    private Collection<Citas> citasCollection;

    public Servicios() {
    }

    public Servicios(Integer codgoServicio) {
        this.codgoServicio = codgoServicio;
    }

    public Integer getCodgoServicio() {
        return codgoServicio;
    }

    public void setCodgoServicio(Integer codgoServicio) {
        this.codgoServicio = codgoServicio;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Propietarios getCodigoPropietario() {
        return codigoPropietario;
    }

    public void setCodigoPropietario(Propietarios codigoPropietario) {
        this.codigoPropietario = codigoPropietario;
    }

    @XmlTransient
    public Collection<Citas> getCitasCollection() {
        return citasCollection;
    }

    public void setCitasCollection(Collection<Citas> citasCollection) {
        this.citasCollection = citasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codgoServicio != null ? codgoServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicios)) {
            return false;
        }
        Servicios other = (Servicios) object;
        if ((this.codgoServicio == null && other.codgoServicio != null) || (this.codgoServicio != null && !this.codgoServicio.equals(other.codgoServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.nowvet.Entitys.Servicios[ codgoServicio=" + codgoServicio + " ]";
    }
    
}
