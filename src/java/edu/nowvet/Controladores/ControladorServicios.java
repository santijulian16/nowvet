/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Controladores;

import edu.nowvet.Entitys.Citas;
import edu.nowvet.Entitys.Propietarios;
import edu.nowvet.Entitys.Servicios;
import edu.nowvet.Entitys.Usuarios;
import edu.nowvet.Facade.ServiciosFacade;
import edu.nowvet.Facade.UsuariosFacade;


import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Movimiento Rastafari
 */
@Named(value = "controladorServicios")
@SessionScoped
public class ControladorServicios implements Serializable {


    private Integer codgoServicio;
    private String descripcionServicio;
    private String tipo;
    private Integer valor;
    private Collection<Citas> citasCollection;
    private Propietarios codigoPropietario;
    Usuarios usuario;
    
    @Inject
    ServiciosFacade serviciosFacade;

    public ControladorServicios() {
    }
    
    public String agendarCitaClinica(){
        HttpServletRequest miSesion = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
        .getRequest();
        this.usuario = (Usuarios) miSesion.getSession().getAttribute("usuario");
        Servicios misServicios = new Servicios();    
        misServicios.setCodigoPropietario(this.usuario.getPropietarios());
        misServicios.setDescripcionServicio("Revisión general de la mascota");
        misServicios.setTipo("Consulta Clínica");
        misServicios.setValor(25000);
        serviciosFacade.create(misServicios);       
        return "indexCliente.xhtml";
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

    public ServiciosFacade getServiciosFacade() {
        return serviciosFacade;
    }

    public void setServiciosFacade(ServiciosFacade serviciosFacade) {
        this.serviciosFacade = serviciosFacade;
    }
    
    
}
