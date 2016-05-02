/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Entitys;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Movimiento Rastafari
 */
@Entity
@Table(name = "internacionalizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Internacionalizacion.findAll", query = "SELECT i FROM Internacionalizacion i"),
    @NamedQuery(name = "Internacionalizacion.findByIdInternacionalizacion", query = "SELECT i FROM Internacionalizacion i WHERE i.idInternacionalizacion = :idInternacionalizacion"),
    @NamedQuery(name = "Internacionalizacion.findByAbreviatura", query = "SELECT i FROM Internacionalizacion i WHERE i.abreviatura = :abreviatura"),
    @NamedQuery(name = "Internacionalizacion.findByPalabra", query = "SELECT i FROM Internacionalizacion i WHERE i.palabra = :palabra"),
    @NamedQuery(name = "Internacionalizacion.findByIdioma", query = "SELECT i FROM Internacionalizacion i WHERE i.idioma = :idioma")})
public class Internacionalizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idInternacionalizacion")
    private Integer idInternacionalizacion;
    @Size(max = 5)
    @Column(name = "abreviatura")
    private String abreviatura;
    @Size(max = 25)
    @Column(name = "palabra")
    private String palabra;
    @Size(max = 25)
    @Column(name = "idioma")
    private String idioma;

    public Internacionalizacion() {
    }

    public Internacionalizacion(Integer idInternacionalizacion) {
        this.idInternacionalizacion = idInternacionalizacion;
    }

    public Integer getIdInternacionalizacion() {
        return idInternacionalizacion;
    }

    public void setIdInternacionalizacion(Integer idInternacionalizacion) {
        this.idInternacionalizacion = idInternacionalizacion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInternacionalizacion != null ? idInternacionalizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Internacionalizacion)) {
            return false;
        }
        Internacionalizacion other = (Internacionalizacion) object;
        if ((this.idInternacionalizacion == null && other.idInternacionalizacion != null) || (this.idInternacionalizacion != null && !this.idInternacionalizacion.equals(other.idInternacionalizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.nowvet.Entitys.Internacionalizacion[ idInternacionalizacion=" + idInternacionalizacion + " ]";
    }
    
}
