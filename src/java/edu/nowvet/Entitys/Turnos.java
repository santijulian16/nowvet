/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Movimiento Rastafari
 */
@Entity
@Table(name = "turnos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Turnos.findAll", query = "SELECT t FROM Turnos t"),
    @NamedQuery(name = "Turnos.findByIdTurno", query = "SELECT t FROM Turnos t WHERE t.idTurno = :idTurno"),
    @NamedQuery(name = "Turnos.findByFechaInicio", query = "SELECT t FROM Turnos t WHERE t.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Turnos.findByFechaFin", query = "SELECT t FROM Turnos t WHERE t.fechaFin = :fechaFin"),
    @NamedQuery(name = "Turnos.findByEstado", query = "SELECT t FROM Turnos t WHERE t.estado = :estado")})
public class Turnos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTurno")
    private Integer idTurno;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Size(max = 45)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "dniPersonalVeterinario", referencedColumnName = "dniPersonal")
    @ManyToOne
    private Personalveterinario dniPersonalVeterinario;

    public Turnos() {
    }

    public Turnos(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Personalveterinario getDniPersonalVeterinario() {
        return dniPersonalVeterinario;
    }

    public void setDniPersonalVeterinario(Personalveterinario dniPersonalVeterinario) {
        this.dniPersonalVeterinario = dniPersonalVeterinario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTurno != null ? idTurno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turnos)) {
            return false;
        }
        Turnos other = (Turnos) object;
        if ((this.idTurno == null && other.idTurno != null) || (this.idTurno != null && !this.idTurno.equals(other.idTurno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.nowvet.Entitys.Turnos[ idTurno=" + idTurno + " ]";
    }
    
}
