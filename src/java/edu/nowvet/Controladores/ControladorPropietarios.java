/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Controladores;

import edu.nowvet.Entitys.Mascotas;
import edu.nowvet.Entitys.Servicios;
import edu.nowvet.Entitys.Usuarios;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Movimiento Rastafari
 */
@Named(value = "controladorPropietarios")
@SessionScoped
public class ControladorPropietarios implements Serializable {

    /**
     * Creates a new instance of ControladorPropietarios
     */

    private Integer dniPropietario;
    private String barrio;
    private Usuarios usuarios;
    private Collection<Servicios> serviciosCollection;
    private Collection<Mascotas> mascotasCollection;
    
    public ControladorPropietarios() {
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

    public Collection<Servicios> getServiciosCollection() {
        return serviciosCollection;
    }

    public void setServiciosCollection(Collection<Servicios> serviciosCollection) {
        this.serviciosCollection = serviciosCollection;
    }

    public Collection<Mascotas> getMascotasCollection() {
        return mascotasCollection;
    }

    public void setMascotasCollection(Collection<Mascotas> mascotasCollection) {
        this.mascotasCollection = mascotasCollection;
    }
    
}
