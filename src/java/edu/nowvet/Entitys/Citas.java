/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Movimiento Rastafari
 */
@Entity
@Table(name = "citas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Citas.findAll", query = "SELECT c FROM Citas c"),
    @NamedQuery(name = "Citas.findByIdCita", query = "SELECT c FROM Citas c WHERE c.idCita = :idCita"),
    @NamedQuery(name = "Citas.findByEstado", query = "SELECT c FROM Citas c WHERE c.estado = :estado"),
    @NamedQuery(name = "Citas.findByFechaAsignada", query = "SELECT c FROM Citas c WHERE c.fechaAsignada = :fechaAsignada"),
    @NamedQuery(name = "Citas.findByVacunas", query = "SELECT c FROM Citas c WHERE c.vacunas = :vacunas"),
    @NamedQuery(name = "Citas.findByPeso", query = "SELECT c FROM Citas c WHERE c.peso = :peso")})
public class Citas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCita")
    private Integer idCita;
    @Size(max = 10)
    @Column(name = "Estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaAsignada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsignada;
    @Size(max = 100)
    @Column(name = "vacunas")
    private String vacunas;
    @Size(max = 15)
    @Column(name = "peso")
    private String peso;
    @Lob
    @Size(max = 65535)
    @Column(name = "antecedentes")
    private String antecedentes;
    @JoinColumn(name = "idServicio", referencedColumnName = "codgoServicio")
    @ManyToOne(optional = false)
    private Servicios idServicio;
    @JoinColumn(name = "idVeterinario", referencedColumnName = "dniPersonal")
    @ManyToOne(optional = false)
    private Personalveterinario idVeterinario;
    @JoinColumn(name = "idMascota", referencedColumnName = "codigoMascota")
    @ManyToOne(optional = false)
    private Mascotas idMascota;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "citas")
    private Citasclinicas citasclinicas;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "citas")
    private Citaspeluqueria citaspeluqueria;

    public Citas() {
    }

    public Citas(Integer idCita) {
        this.idCita = idCita;
    }

    public Citas(Integer idCita, Date fechaAsignada) {
        this.idCita = idCita;
        this.fechaAsignada = fechaAsignada;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaAsignada() {
        return fechaAsignada;
    }

    public void setFechaAsignada(Date fechaAsignada) {
        this.fechaAsignada = fechaAsignada;
    }

    public String getVacunas() {
        return vacunas;
    }

    public void setVacunas(String vacunas) {
        this.vacunas = vacunas;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public Servicios getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Servicios idServicio) {
        this.idServicio = idServicio;
    }

    public Personalveterinario getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(Personalveterinario idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public Mascotas getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Mascotas idMascota) {
        this.idMascota = idMascota;
    }

    public Citasclinicas getCitasclinicas() {
        return citasclinicas;
    }

    public void setCitasclinicas(Citasclinicas citasclinicas) {
        this.citasclinicas = citasclinicas;
    }

    public Citaspeluqueria getCitaspeluqueria() {
        return citaspeluqueria;
    }

    public void setCitaspeluqueria(Citaspeluqueria citaspeluqueria) {
        this.citaspeluqueria = citaspeluqueria;
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
        if (!(object instanceof Citas)) {
            return false;
        }
        Citas other = (Citas) object;
        if ((this.idCita == null && other.idCita != null) || (this.idCita != null && !this.idCita.equals(other.idCita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.nowvet.Entitys.Citas[ idCita=" + idCita + " ]";
    }
    
}
