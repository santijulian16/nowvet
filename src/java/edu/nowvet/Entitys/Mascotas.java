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
 * @author Julian
 */
@Entity
@Table(name = "mascotas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mascotas.findAll", query = "SELECT m FROM Mascotas m"),
    @NamedQuery(name = "Mascotas.findByCodigoMascota", query = "SELECT m FROM Mascotas m WHERE m.codigoMascota = :codigoMascota"),
    @NamedQuery(name = "Mascotas.findByNombre", query = "SELECT m FROM Mascotas m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Mascotas.findByEspecie", query = "SELECT m FROM Mascotas m WHERE m.especie = :especie"),
    @NamedQuery(name = "Mascotas.findBySexo", query = "SELECT m FROM Mascotas m WHERE m.sexo = :sexo"),
    @NamedQuery(name = "Mascotas.findByColor", query = "SELECT m FROM Mascotas m WHERE m.color = :color"),
    @NamedQuery(name = "Mascotas.findByRaza", query = "SELECT m FROM Mascotas m WHERE m.raza = :raza"),
    @NamedQuery(name = "Mascotas.findByEstado", query = "SELECT m FROM Mascotas m WHERE m.estado = :estado")})
public class Mascotas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoMascota")
    private Integer codigoMascota;
    @Size(max = 25)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 6)
    @Column(name = "especie")
    private String especie;
    @Size(max = 6)
    @Column(name = "sexo")
    private String sexo;
    @Size(max = 100)
    @Column(name = "color")
    private String color;
    @Size(max = 40)
    @Column(name = "raza")
    private String raza;
    @Size(max = 8)
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMascota")
    private Collection<Citas> citasCollection;
    @JoinColumn(name = "codigoPropietario", referencedColumnName = "dniPropietario")
    @ManyToOne
    private Propietarios codigoPropietario;

    public Mascotas() {
    }

    public Mascotas(Integer codigoMascota) {
        this.codigoMascota = codigoMascota;
    }

    public Integer getCodigoMascota() {
        return codigoMascota;
    }

    public void setCodigoMascota(Integer codigoMascota) {
        this.codigoMascota = codigoMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Citas> getCitasCollection() {
        return citasCollection;
    }

    public void setCitasCollection(Collection<Citas> citasCollection) {
        this.citasCollection = citasCollection;
    }

    public Propietarios getCodigoPropietario() {
        return codigoPropietario;
    }

    public void setCodigoPropietario(Propietarios codigoPropietario) {
        this.codigoPropietario = codigoPropietario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoMascota != null ? codigoMascota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mascotas)) {
            return false;
        }
        Mascotas other = (Mascotas) object;
        if ((this.codigoMascota == null && other.codigoMascota != null) || (this.codigoMascota != null && !this.codigoMascota.equals(other.codigoMascota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.nowvet.Entitys.Mascotas[ codigoMascota=" + codigoMascota + " ]";
    }
    
}
