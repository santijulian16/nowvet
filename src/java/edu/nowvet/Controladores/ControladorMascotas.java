/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Controladores;

import edu.nowvet.Entitys.Citasclinicas;
import edu.nowvet.Entitys.Mascotas;
import edu.nowvet.Entitys.Propietarios;
import edu.nowvet.Entitys.Usuarios;
import edu.nowvet.Facade.CitasclinicasFacade;
import edu.nowvet.Facade.MascotasFacade;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Julian
 */
@Named(value = "controladorMascotas")
@SessionScoped
public class ControladorMascotas implements Serializable {
private String estado;
private Mascotas mascotaTemp;
private Mascotas mascotaReg;//Este objeto es para registra una mascota nueva
HttpServletRequest miSesion = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
String rol= (String) miSesion.getSession().getAttribute("rol");
private Propietarios prop;
    /**
     * Creates a new instance of ControladorMascotas
     */
    @Inject
    MascotasFacade mf;
    @Inject
    CitasclinicasFacade ccf;
    @Inject
    ControladorUsuario cu;
    public ControladorMascotas() {
        this.mascotaReg=new Mascotas();
        this.estado="0";
    }
    
    public List <Mascotas> mostrar(){
        List <Mascotas> mas2;
        mas2=this.mf.mostarMascotas(this.cu.getUsuLogin().getCedula());
        this.estado="0";
        return mas2;
    }
    
    public List <Mascotas> mostrarTemp(){
        List <Mascotas> mas2;
        mas2=this.mf.mostarMascotas(this.cu.getUsuTemp().getCedula());
        this.estado="0";
        return mas2;
    }
    
    public void inactivarMascota(){
        FacesContext faces = FacesContext.getCurrentInstance();
        ExternalContext externalContext = faces.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        int idmas= Integer.parseInt((String) params.get("idmas"));
        Mascotas mas=this.mf.find(idmas);
        mas.setEstado("Inactiva");
        this.mf.edit(mas);
    }
    
    public void activarMascota(){
        FacesContext faces = FacesContext.getCurrentInstance();
        ExternalContext externalContext = faces.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        int idmas= Integer.parseInt((String) params.get("idmas2"));
        Mascotas mas=this.mf.find(idmas);
        mas.setEstado("Activa");
        this.mf.edit(mas);
    }
    
    public void mostrarMas(Mascotas mascota){
        this.mascotaTemp=mascota;
       FacesContext contex = FacesContext.getCurrentInstance();
       
    try {
        if (rol.equals("Administrador-Veterinario")) {
            contex.getExternalContext().redirect( "/NowVet/faces/administrador/perfiles/mascota.xhtml");
        }else if(rol.equals("Cliente")){
            contex.getExternalContext().redirect( "/NowVet/faces/cliente/perfil/mascota.xhtml");
        }
        
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    }
    
    public List<Citasclinicas> listarHistorialClinico(){
        List<Citasclinicas> historial;
        historial=this.ccf.listarHistorial(this.mascotaTemp.getCodigoMascota());
        return historial;
    }
    
    public void modificarMascota(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        String raza = (String) params.get("raza");
        this.mascotaTemp.setRaza(raza);
        this.mf.edit(mascotaTemp);
        this.estado="1";
    }
    
    public String registrarMascota(Propietarios prop2){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        String especie = ((String) params.get("especie"));
        String raza = ((String) params.get("raza"));
        String razaG = ((String) params.get("raza2"));
        if(especie.equals("Perro")){
            this.mascotaReg.setRaza(raza);
        }
        else{
            this.mascotaReg.setRaza(razaG);
        }
        this.mascotaReg.setEspecie(especie);
        this.mascotaReg.setEstado("Activa");
        this.mascotaReg.setCodigoPropietario(prop2);
        this.mf.create(this.mascotaReg);
        this.mascotaReg=new Mascotas();
        this.estado="3";
        String retorno="";
        if (rol.equals("Administrador-Veterinario")) {
            retorno="perfil";
        }else if(rol.equals("Cliente")){
            retorno="miperfil";
        }
        return retorno;
    }
    //registrar Mascota lado cliente
    public String registrarMascota(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest miSesion = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Usuarios usuTemp= (Usuarios) miSesion.getSession().getAttribute("usuario");
        Propietarios prop2=usuTemp.getPropietarios();
        Map params = externalContext.getRequestParameterMap();
        String especie = ((String) params.get("especie"));
        String raza = ((String) params.get("raza"));
        String razaG = ((String) params.get("raza2"));
        if(especie.equals("Perro")){
            this.mascotaReg.setRaza(raza);
        }
        else{
            this.mascotaReg.setRaza(razaG);
        }
        this.mascotaReg.setEspecie(especie);
        this.mascotaReg.setEstado("Activa");
        this.mascotaReg.setCodigoPropietario(prop2);
        this.mf.create(this.mascotaReg);
        this.mascotaReg=new Mascotas();
        this.estado="3";
        String retorno="";
        if (rol.equals("Administrador-Veterinario")) {
            retorno="perfil";
        }else if(rol.equals("Cliente")){
            retorno="miperfil";
        }
        return retorno;
    }

    public Mascotas getMascotaTemp() {
        return mascotaTemp;
    }

    public void setMascotaTemp(Mascotas mascotaTemp) {
        this.mascotaTemp = mascotaTemp;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Mascotas getMascotaReg() {
        return mascotaReg;
    }

    public void setMascotaReg(Mascotas mascotaReg) {
        this.mascotaReg = mascotaReg;
    }

    public Propietarios getProp() {
        return prop;
    }

    public void setProp(Propietarios prop) {
        this.prop = prop;
    }
    
    
}
