/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Entitys;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Julian
 */
@Entity
@Table(name = "personalveterinario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personalveterinario.findAll", query = "SELECT p FROM Personalveterinario p"),
    @NamedQuery(name = "Personalveterinario.findByDniPersonal", query = "SELECT p FROM Personalveterinario p WHERE p.dniPersonal = :dniPersonal"),
    @NamedQuery(name = "Personalveterinario.findByCargo", query = "SELECT p FROM Personalveterinario p WHERE p.cargo = :cargo"),
    @NamedQuery(name = "Personalveterinario.findByHorarioEntrada", query = "SELECT p FROM Personalveterinario p WHERE p.horarioEntrada = :horarioEntrada"),
    @NamedQuery(name = "Personalveterinario.findByHorarioSalida", query = "SELECT p FROM Personalveterinario p WHERE p.horarioSalida = :horarioSalida")})
public class Personalveterinario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dniPersonal")
    private Integer dniPersonal;
    @Size(max = 30)
    @Column(name = "cargo")
    private String cargo;
    @Column(name = "horarioEntrada")
    @Temporal(TemporalType.TIME)
    private Date horarioEntrada;
    @Column(name = "horarioSalida")
    @Temporal(TemporalType.TIME)
    private Date horarioSalida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVeterinario")
    private Collection<Citas> citasCollection;
    @JoinColumn(name = "dniPersonal", referencedColumnName = "cedula", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuarios usuarios;
    @OneToMany(mappedBy = "dniPersonalVeterinario")
    private Collection<Turnos> turnosCollection;

    public Personalveterinario() {
    }

    public Personalveterinario(Integer dniPersonal) {
        this.dniPersonal = dniPersonal;
    }

    public Integer getDniPersonal() {
        return dniPersonal;
    }

    public void setDniPersonal(Integer dniPersonal) {
        this.dniPersonal = dniPersonal;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(Date horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public Date getHorarioSalida() {
        return horarioSalida;
    }

    public void setHorarioSalida(Date horarioSalida) {
        this.horarioSalida = horarioSalida;
    }

    @XmlTransient
    public Collection<Citas> getCitasCollection() {
        return citasCollection;
    }

    public void setCitasCollection(Collection<Citas> citasCollection) {
        this.citasCollection = citasCollection;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @XmlTransient
    public Collection<Turnos> getTurnosCollection() {
        return turnosCollection;
    }

    public void setTurnosCollection(Collection<Turnos> turnosCollection) {
        this.turnosCollection = turnosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dniPersonal != null ? dniPersonal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personalveterinario)) {
            return false;
        }
        Personalveterinario other = (Personalveterinario) object;
        if ((this.dniPersonal == null && other.dniPersonal != null) || (this.dniPersonal != null && !this.dniPersonal.equals(other.dniPersonal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.nowvet.Entitys.Personalveterinario[ dniPersonal=" + dniPersonal + " ]";
    }
    
}
