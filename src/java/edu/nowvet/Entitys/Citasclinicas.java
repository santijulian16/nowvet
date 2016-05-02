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
import javax.persistence.Lob;
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
@Table(name = "citasclinicas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Citasclinicas.findAll", query = "SELECT c FROM Citasclinicas c"),
    @NamedQuery(name = "Citasclinicas.findByIdCita", query = "SELECT c FROM Citasclinicas c WHERE c.idCita = :idCita"),
    @NamedQuery(name = "Citasclinicas.findByMotivo", query = "SELECT c FROM Citasclinicas c WHERE c.motivo = :motivo"),
    @NamedQuery(name = "Citasclinicas.findByAlimento", query = "SELECT c FROM Citasclinicas c WHERE c.alimento = :alimento"),
    @NamedQuery(name = "Citasclinicas.findByFormulaMedica", query = "SELECT c FROM Citasclinicas c WHERE c.formulaMedica = :formulaMedica")})
public class Citasclinicas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCita")
    private Integer idCita;
    @Lob
    @Size(max = 65535)
    @Column(name = "diagnostico")
    private String diagnostico;
    @Size(max = 200)
    @Column(name = "motivo")
    private String motivo;
    @Size(max = 30)
    @Column(name = "alimento")
    private String alimento;
    @Size(max = 200)
    @Column(name = "formulaMedica")
    private String formulaMedica;
    @Lob
    @Size(max = 65535)
    @Column(name = "sintomas")
    private String sintomas;
    @JoinColumn(name = "idCita", referencedColumnName = "idCita", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Citas citas;

    public Citasclinicas() {
    }

    public Citasclinicas(Integer idCita) {
        this.idCita = idCita;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    public String getFormulaMedica() {
        return formulaMedica;
    }

    public void setFormulaMedica(String formulaMedica) {
        this.formulaMedica = formulaMedica;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
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
        if (!(object instanceof Citasclinicas)) {
            return false;
        }
        Citasclinicas other = (Citasclinicas) object;
        if ((this.idCita == null && other.idCita != null) || (this.idCita != null && !this.idCita.equals(other.idCita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.nowvet.Entitys.Citasclinicas[ idCita=" + idCita + " ]";
    }
    
}
