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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Movimiento Rastafari
 */
@Entity
@Table(name = "citaspeluqueria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Citaspeluqueria.findAll", query = "SELECT c FROM Citaspeluqueria c"),
    @NamedQuery(name = "Citaspeluqueria.findByIdCita", query = "SELECT c FROM Citaspeluqueria c WHERE c.idCita = :idCita"),
    @NamedQuery(name = "Citaspeluqueria.findByServiciosAplicados", query = "SELECT c FROM Citaspeluqueria c WHERE c.serviciosAplicados = :serviciosAplicados")})
public class Citaspeluqueria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCita")
    private Integer idCita;
    @Size(max = 21)
    @Column(name = "serviciosAplicados")
    private String serviciosAplicados;
    @JoinColumn(name = "idCita", referencedColumnName = "idCita", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Citas citas;

    public Citaspeluqueria() {
    }

    public Citaspeluqueria(Integer idCita) {
        this.idCita = idCita;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getServiciosAplicados() {
        return serviciosAplicados;
    }

    public void setServiciosAplicados(String serviciosAplicados) {
        this.serviciosAplicados = serviciosAplicados;
    }

    public Citas getCitas() {
        return citas;
    }

    public void setCitas(Citas citas) {
        this.citas = citas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCita != null ? idCita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Citaspeluqueria)) {
            return false;
        }
        Citaspeluqueria other = (Citaspeluqueria) object;
        if ((this.idCita == null && other.idCita != null) || (this.idCita != null && !this.idCita.equals(other.idCita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.nowvet.Entitys.Citaspeluqueria[ idCita=" + idCita + " ]";
    }
    
}
