/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Controladores;


import edu.nowvet.Entitys.Citas;
import edu.nowvet.Entitys.Citasclinicas;
import edu.nowvet.Entitys.Citaspeluqueria;
import edu.nowvet.Entitys.Mailer;
import edu.nowvet.Entitys.Mascotas;
import edu.nowvet.Entitys.Personalveterinario;
import edu.nowvet.Entitys.Propietarios;
import edu.nowvet.Entitys.Servicios;
import edu.nowvet.Entitys.Usuarios;
import edu.nowvet.Facade.CitasFacade;
import edu.nowvet.Facade.CitasclinicasFacade;
import edu.nowvet.Facade.CitaspeluqueriaFacade;
import edu.nowvet.Facade.MascotasFacade;
import edu.nowvet.Facade.PersonalveterinarioFacade;
import edu.nowvet.Facade.ServiciosFacade;
import edu.nowvet.Facade.UsuariosFacade;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author Movimiento Rastafari
 */
@Named(value = "controladorCitas")
@SessionScoped
public class controladorCitas implements Serializable {

    @Inject
    CitasFacade citasFacade;
    @Inject
    CitasclinicasFacade citasClinicasFacade;
    @Inject
    CitaspeluqueriaFacade citasPeluqueriaFacade;
    @Inject
    MascotasFacade mascotasFacade;
    @Inject
    PersonalveterinarioFacade personalveterinarioFacade;
    @Inject
    ServiciosFacade serviciosFacade;
    @Inject
    UsuariosFacade usuariosFacade;
    
    List<Personalveterinario> listaVeterinarios = new ArrayList<>();
    List<Mascotas> listaMascotas = new ArrayList<>();
    List<Usuarios> listaCargaVeterinarios;
    private String correo;
    private Integer idCita;
    private String estado;
    private Date fechaAsignada = new Date();
    private String vacunas;
    private String peso;
    private String antecedentes;
    private Servicios idServicio;
    private Personalveterinario idVeterinario;
    private Mascotas idMascota;
    private Citasclinicas citasclinicas;
    private Citaspeluqueria citaspeluqueria;
    private Usuarios usuario;
    
    private Integer idCitaClinica;
    private String diagnostico;
    private String motivo;
    private String alimento;
    private String formulaMedica;
    private String sintomas;
    private Citas citas;
    private String estados; /*Variable para verificar disponibilidad de citas clínicas*/
    private String estadosP; /*Variable para verificar disponibilidad de citas de peluquería*/
    private String campoOculto;
    private Date fechaVerdadera;
    private String confirmar;
    
    public controladorCitas() {
        estados = "0";
        estadosP = "0";
        campoOculto = "0";
        confirmar = "0";
    }
    
    public void agendarCitaClinica() throws ParseException{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();        
        SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String fecha = ((String) params.get("fechaVerdadera"));
        Date fechaA = formato.parse(fecha);
        HttpServletRequest miSesion = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
        .getRequest();
        this.usuario = (Usuarios) miSesion.getSession().getAttribute("usuario");
        Propietarios misPropietarios = new Propietarios();
        misPropietarios.setUsuarios(this.usuario);
        misPropietarios.setDniPropietario(this.usuario.getPropietarios().getDniPropietario());
        misPropietarios.setBarrio(this.usuario.getPropietarios().getBarrio());
        
        int idMascotas=Integer.parseInt((String) params.get("mascota"));
        Mascotas misMascotas = this.mascotasFacade.find(idMascotas);
        
        int dniPersonal=Integer.parseInt((String) params.get("veterinario"));
        Personalveterinario miVeterinario = this.personalveterinarioFacade.find(dniPersonal);
        
        Servicios misServicios = new Servicios();    
        misServicios.setCodigoPropietario(this.usuario.getPropietarios());
        misServicios.setDescripcionServicio("Revisión general de la mascota");
        misServicios.setTipo("Consulta Clinica");
        misServicios.setValor(25000);
        serviciosFacade.create(misServicios);
        Citas misCitas = new Citas();
        misCitas.setEstado("Creada");

        misCitas.setFechaAsignada(fechaA);
        misCitas.setIdServicio(misServicios);
        misCitas.setIdMascota(misMascotas);
        misCitas.setIdVeterinario(miVeterinario);
        citasFacade.create(misCitas);
        Citasclinicas misCitasClinicas = new Citasclinicas();
        misCitasClinicas.setIdCita(misCitas.getIdCita());
        String motivoF = (String) params.get("motivo");
        misCitasClinicas.setMotivo(motivoF);
        String sintomasF = (String) params.get("sintomas");
        misCitasClinicas.setSintomas(sintomasF);
        citasClinicasFacade.create(misCitasClinicas);
//        Mailer.send(this.usuario.getCorreo(), "NowVet - Confirmación de Cita","<div style='display: block;'><img style='widht: 70px; height: 70px;' src='http://nowvet.co.nf/imagenes/logo.png' alt='Logo'></div><div style='font-family: arial;'><h1 style='color: #2aac7d;'>Confirmación de Consulta Clínica."+"</h1><p style='font-size: 1.5em; color: #797979;'>NowVet le informa que el servicio de consulta clínica agendado para su mascota "+misMascotas.getNombre()+" ya se encuentra en estado disponible. A continuación se presentan los datos respectivos de la consulta clínica: </p></div>"
//                + "<br/><p style='font-size: 1.5em; color: #797979;'>Servicio: "+misServicios.getTipo()
//                + "<br/>Mascota: "+misMascotas.getNombre()
//                + "<br/>Fecha Asignada: "+misCitas.getFechaAsignada().toLocaleString()
//                + "<br/>Veterinario: "+miVeterinario.getUsuarios().getNombres()+" "+miVeterinario.getUsuarios().getApellidos()+"</p>");
        FacesContext context = FacesContext.getCurrentInstance();
             try {
            context.getExternalContext().redirect("/NowVet/faces/cliente/servicios/confirmacionCitaClinica.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(controladorCitas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void agendarCitaPeluqueria() throws ParseException{
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            Map params = externalContext.getRequestParameterMap();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            String fecha = ((String) params.get("fechaVerdadera"));
            Date fechaA = formato.parse(fecha);
            
            HttpServletRequest miSesion = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                    .getRequest();
            this.usuario = (Usuarios) miSesion.getSession().getAttribute("usuario");
            Propietarios misPropietarios = new Propietarios();
            misPropietarios.setUsuarios(this.usuario);
            misPropietarios.setDniPropietario(this.usuario.getPropietarios().getDniPropietario());
            misPropietarios.setBarrio(this.usuario.getPropietarios().getBarrio());
            
            int idMascotas=Integer.parseInt((String) params.get("mascota"));
            Mascotas misMascotas = this.mascotasFacade.find(idMascotas);
            
            int dniPersonal=Integer.parseInt((String) params.get("veterinario"));
            Personalveterinario miVeterinario = this.personalveterinarioFacade.find(dniPersonal);
            
            Servicios misServicios = new Servicios();
            misServicios.setCodigoPropietario(this.usuario.getPropietarios());
            misServicios.setDescripcionServicio("Limpieza general de la mascota");
            misServicios.setTipo("Peluqueria");
            misServicios.setValor(25000);
            serviciosFacade.create(misServicios);
            Citas misCitas = new Citas();
            misCitas.setEstado("Creada");
            misCitas.setFechaAsignada(fechaA);
            misCitas.setIdServicio(misServicios);
            misCitas.setIdMascota(misMascotas);
            misCitas.setIdVeterinario(miVeterinario);
            citasFacade.create(misCitas);
            Citaspeluqueria misCitasPeluqueria = new Citaspeluqueria();
            misCitasPeluqueria.setIdCita(misCitas.getIdCita());
            String serviciosAplicados = (String) params.get("serviciosAplicados");
            misCitasPeluqueria.setServiciosAplicados(serviciosAplicados);
            citasPeluqueriaFacade.create(misCitasPeluqueria);
//            Mailer.send(this.usuario.getCorreo(), "NowVet - Confirmación de Cita","<div style='display: block;'><img style='widht: 70px; height: 70px;' src='http://nowvet.co.nf/imagenes/logo.png' alt='Logo'></div><div style='font-family: arial;'><h1 style='color: #2aac7d;'>Confirmación de Peluquería."+"</h1><p style='font-size: 1.5em; color: #797979;'>NowVet le informa que el servicio de peluquería agendado para su mascota "+misMascotas.getNombre()+" ya se encuentra en estado disponible. A continuación se presentan los datos respectivos de la consulta clínica: </p></div>"
//                + "<br/><p style='font-size: 1.5em; color: #797979;'>Servicio: "+misServicios.getTipo()
//                + "<br/>Mascota: "+misMascotas.getNombre()
//                + "<br/>Fecha Asignada: "+misCitas.getFechaAsignada().toLocaleString()
//                + "<br/>Veterinario: "+miVeterinario.getUsuarios().getNombres()+" "+miVeterinario.getUsuarios().getApellidos()
//                + "<br/>Servicios Aplicados: "+misCitasPeluqueria.getServiciosAplicados()+"</p>");
            FacesContext context = FacesContext.getCurrentInstance();
             try {
            context.getExternalContext().redirect("/NowVet/faces/cliente/servicios/confirmacionCitaDePeluqueria.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(controladorCitas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void verificarDisponibilidadCitaClinica() throws ParseException{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String fecha = ((String) params.get("fecha"));
        fecha=fecha+":00";
        Date fechaA = new Date();
        fechaA=formato.parse(fecha);
        SimpleDateFormat formatoTime = new SimpleDateFormat("HH:mm:ss");
        String fechaTime = formatoTime.format(fechaA);
        Date cambiarFechaTime = new Date();
        cambiarFechaTime = formatoTime.parse(fechaTime);
        listaCargaVeterinarios=this.usuariosFacade.buscarVeterinario(cambiarFechaTime);
        this.campoOculto = "1";
        if(!citasFacade.verificarDisponibilidad(fechaA).isEmpty()){
            this.estados="3";
        }
        else{
            this.estados="4";
            fechaVerdadera = fechaA;
        }
    }
    
    public void verificarDisponibilidadCitaPeluqueria() throws ParseException{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String fecha = ((String) params.get("fecha"));
        fecha=fecha+":00";
        Date fechaA = new Date();
        fechaA=formato.parse(fecha);
        SimpleDateFormat formatoTime = new SimpleDateFormat("HH:mm:ss");
        String fechaTime = formatoTime.format(fechaA);
        Date cambiarFechaTime = new Date();
        cambiarFechaTime = formatoTime.parse(fechaTime);
        listaCargaVeterinarios=this.usuariosFacade.buscarVeterinario(cambiarFechaTime);
        this.campoOculto = "1";
        if(!citasFacade.verificarDisponibilidad(fechaA).isEmpty()){
            this.estadosP="3";
        }
        else{
            this.estadosP="4";
            fechaVerdadera = fechaA;
        }
    }
    
    public void atenderCitaClinica() throws ParseException{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        Citas misCitasA = new Citas();
        int idCitaM = Integer.parseInt((String) params.get("idCitaA"));
        
        SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String fecha = ((String) params.get("fechaAsignadaA"));
        Date fechaAsignadaAC = new Date();
        fechaAsignadaAC=formato.parse(fecha);

        int idServicioT=Integer.parseInt((String) params.get("idServicioA"));
        Servicios idServicioM = this.serviciosFacade.find(idServicioT);
        
        int idMascotaT=Integer.parseInt((String) params.get("idMascotaA"));
        Mascotas idMascotaM = this.mascotasFacade.find(idMascotaT);
        
        int idVeterinarioT=Integer.parseInt((String) params.get("idVeterinarioA"));
        Personalveterinario idVeterinarioM = this.personalveterinarioFacade.find(idVeterinarioT);
        String vacunasM = ((String) params.get("vacunasA"));
        String pesoM = ((String) params.get("pesoA"));
        String antecedentesM = ((String) params.get("antecedentesA"));
        misCitasA.setIdCita(idCitaM);
        misCitasA.setEstado("Ejecutada");
        misCitasA.setFechaAsignada(fechaAsignadaAC);
        misCitasA.setIdServicio(idServicioM);
        misCitasA.setIdMascota(idMascotaM);
        misCitasA.setIdVeterinario(idVeterinarioM);
        misCitasA.setVacunas(vacunasM);
        misCitasA.setPeso(pesoM);
        misCitasA.setAntecedentes(antecedentesM);
        citasFacade.edit(misCitasA);
        
        Citasclinicas misCitasClinicasA = new Citasclinicas();
        int idCitaAM = Integer.parseInt((String) params.get("idCitaAC"));
        String diagnosticoM = ((String) params.get("diagnosticoA"));
        String motivoM = ((String) params.get("motivoA"));
        String alimentoM = ((String) params.get("alimentoA"));
        String formulaMedicaM = ((String) params.get("formulaMedicaA"));
        String sintomasM = ((String) params.get("sintomasA"));
        misCitasClinicasA.setIdCita(idCitaAM);
        misCitasClinicasA.setDiagnostico(diagnosticoM);
        misCitasClinicasA.setMotivo(motivoM);
        misCitasClinicasA.setAlimento(alimentoM);
        misCitasClinicasA.setFormulaMedica(formulaMedicaM);
        misCitasClinicasA.setSintomas(sintomasM);
        citasClinicasFacade.edit(misCitasClinicasA);
        
    }
    
    public void atenderCitaPeluqueria() throws ParseException{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        Citas misCitasA = new Citas();
        int idCitaM = Integer.parseInt((String) params.get("idCitaB"));
        
        SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String fecha = ((String) params.get("fechaAsignadaB"));
        Date fechaAsignadaAP = new Date();
        fechaAsignadaAP=formato.parse(fecha);

        int idServicioT=Integer.parseInt((String) params.get("idServicioB"));
        Servicios idServicioM = this.serviciosFacade.find(idServicioT);
        
        int idMascotaT=Integer.parseInt((String) params.get("idMascotaB"));
        Mascotas idMascotaM = this.mascotasFacade.find(idMascotaT);
        
        int idVeterinarioT=Integer.parseInt((String) params.get("idVeterinarioB"));
        Personalveterinario idVeterinarioM = this.personalveterinarioFacade.find(idVeterinarioT);
        String vacunasM = ((String) params.get("vacunasB"));
        String pesoM = ((String) params.get("pesoB"));
        String antecedentesM = ((String) params.get("antecedentesB"));
        misCitasA.setIdCita(idCitaM);
        misCitasA.setEstado("Ejecutada");
        misCitasA.setFechaAsignada(fechaAsignadaAP);
        misCitasA.setIdServicio(idServicioM);
        misCitasA.setIdMascota(idMascotaM);
        misCitasA.setIdVeterinario(idVeterinarioM);
        misCitasA.setVacunas(vacunasM);
        misCitasA.setPeso(pesoM);
        misCitasA.setAntecedentes(antecedentesM);
        citasFacade.edit(misCitasA);
        
        Citaspeluqueria misCitasPeluqueriaB = new Citaspeluqueria();
        int idCitaAB = Integer.parseInt((String) params.get("idCitaAB"));
        String serviciosAplicadosB = ((String) params.get("serviciosAplicadosB"));
        misCitasPeluqueriaB.setIdCita(idCitaAB);
        misCitasPeluqueriaB.setServiciosAplicados(serviciosAplicadosB);
        citasPeluqueriaFacade.edit(misCitasPeluqueriaB);
    }
    
    public void modificarCita() throws ParseException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        
        SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String fecha = ((String) params.get("fechaCitaV"));
        Date fechaAsignadaM = formato.parse(fecha);
        Date fechaActual = new Date();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(fechaAsignadaM);
        cal2.setTime(fechaActual);
        
        long milis1 = cal1.getTimeInMillis();
        long milis2 = cal2.getTimeInMillis();
        long diff =  milis1 - milis2;
        long diffHours = diff / (60 * 60 * 1000);
        if(diffHours >= 24 || diffHours <=-24) {
        SimpleDateFormat formato2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String fecha2 = ((String) params.get("fecha"));
        fecha2 += ":00";
        Date fechaAsignadaM2 = new Date();
        fechaAsignadaM2=formato2.parse(fecha2);
        Citas misCitasA = new Citas();
        int idCitaM = Integer.parseInt((String) params.get("idCitaA"));

        int idServicioT=Integer.parseInt((String) params.get("idServicioA"));
        Servicios idServicioM = this.serviciosFacade.find(idServicioT);
        
        int idMascotaT=Integer.parseInt((String) params.get("idMascotaA"));
        Mascotas idMascotaM = this.mascotasFacade.find(idMascotaT);
        
        int idVeterinarioT=Integer.parseInt((String) params.get("idVeterinarioA"));
        Personalveterinario idVeterinarioM = this.personalveterinarioFacade.find(idVeterinarioT);
        String estadoM=((String) params.get("estadoA"));
        String vacunasM = ((String) params.get("vacunasA"));
        String pesoM = ((String) params.get("pesoA"));
        String antecedentesM = ((String) params.get("antecedentesA"));
        misCitasA.setIdCita(idCitaM);
        misCitasA.setEstado(estadoM);
        misCitasA.setFechaAsignada(fechaAsignadaM2);
        misCitasA.setIdServicio(idServicioM);
        misCitasA.setIdMascota(idMascotaM);
        misCitasA.setIdVeterinario(idVeterinarioM);
        misCitasA.setVacunas(vacunasM);
        misCitasA.setPeso(pesoM);
        misCitasA.setAntecedentes(antecedentesM);
        citasFacade.edit(misCitasA);
        this.estados = "2";
        }
        else{
            this.estados = "1";
        }
    }
    
    public void cancelarCita(Servicios idServicio) throws ParseException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        
        SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String fecha = ((String) params.get("fechaCitaC"));
        Date fechaAsignadaC = new Date();
        fechaAsignadaC=formato.parse(fecha);
        Date fechaActual = new Date();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(fechaAsignadaC);
        cal2.setTime(fechaActual);
        
        long milis1 = cal1.getTimeInMillis();
        long milis2 = cal2.getTimeInMillis();
        long diff =  milis1 - milis2;
        long diffHours = diff / (60 * 60 * 1000);
        if(diffHours >= 24 || diffHours <=-24) {
            serviciosFacade.remove(idServicio);
            this.estados = "4";
        }
        else{
            this.estados = "3";
        }
    }
    
    public List <Citas> listarMisCitasRecientes(){
        List <Citas> listaCitasRecientes;
        listaCitasRecientes=this.citasFacade.consultarCitasRecientes();
        return listaCitasRecientes;
    }
    
    public List <Citas> listarMisCitasEjecutadas(){
        List <Citas> listaCitasEjecutadas;
        listaCitasEjecutadas=this.citasFacade.consultarCitasEjecutadas();
        return listaCitasEjecutadas;
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

    public Integer getIdCitaClinica() {
        return idCitaClinica;
    }

    public void setIdCitaClinica(Integer idCitaClinica) {
        this.idCitaClinica = idCitaClinica;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Personalveterinario> getListaVeterinarios() {
        return personalveterinarioFacade.findAll();
    }

    public void setListaVeterinarios(List<Personalveterinario> listaVeterinarios) {
        this.listaVeterinarios = listaVeterinarios;
    }

    public List<Mascotas> getListaMascotas() {
        List<Mascotas> misMascotas = new ArrayList<>();
        misMascotas =  mascotasFacade.findAll();
        return misMascotas;
    }

    public void setListaMascotas(List<Mascotas> listaMascotas) {
        this.listaMascotas = listaMascotas;
    }

    public String getEstados() {
        return estados;
    }

    public void setEstados(String estados) {
        this.estados = estados;
    }

    public String getEstadosP() {
        return estadosP;
    }

    public void setEstadosP(String estadosP) {
        this.estadosP = estadosP;
    }

    public String getConfirmar() {
        return confirmar;
    }

    public void setConfirmar(String confirmar) {
        this.confirmar = confirmar;
    }

    public List<Usuarios> getListaCargaVeterinarios() {
        return listaCargaVeterinarios;
    }

    public void setListaCargaVeterinarios(List<Usuarios> listaCargaVeterinarios) {
        this.listaCargaVeterinarios = listaCargaVeterinarios;
    }

    public String getCampoOculto() {
        return campoOculto;
    }

    public void setCampoOculto(String campoOculto) {
        this.campoOculto = campoOculto;
    }

    public Date getFechaVerdadera() {
        return fechaVerdadera;
    }

    public void setFechaVerdadera(Date fechaVerdadera) {
        this.fechaVerdadera = fechaVerdadera;
    }
}
