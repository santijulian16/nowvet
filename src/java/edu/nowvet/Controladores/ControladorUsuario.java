/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Controladores;

import edu.nowvet.Entitys.Auditoria;
import edu.nowvet.Entitys.Citas;
import edu.nowvet.Entitys.Mailer;
import edu.nowvet.Entitys.Personalveterinario;
import edu.nowvet.Entitys.Propietarios;
import edu.nowvet.Entitys.Roles;
import edu.nowvet.Entitys.Usuarios;
import edu.nowvet.Facade.AuditoriaFacade;
import edu.nowvet.Facade.PersonalveterinarioFacade;
import edu.nowvet.Facade.PropietariosFacade;
import edu.nowvet.Facade.RolesFacade;
import edu.nowvet.Facade.UsuariosFacade;
import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Julian
 */
@Named(value = "controladorUsuario")
@SessionScoped
public class ControladorUsuario implements Serializable {

    private String correo;
    private String contrasena;
    private String estado;
    private Usuarios usuLogin;
    private Usuarios usuTemp;//Este objeto sirve para eliminar usuarios agenos al usuario logueado
    private String rols;
    private int rolTemp;
    private Propietarios prop;
    JasperPrint jasperPrint;
    private List lista1;
    Workbook archivoExcel;
    Sheet mihoja;
    private int veterinario;
    /**
     * Creates a new instance of ControladorUsuario
     */
    @Inject
    UsuariosFacade usuFacade;
    @Inject
    AuditoriaFacade auFacade;
    @Inject
    RolesFacade rf;
    @Inject
    PropietariosFacade prf;
    @Inject
    PersonalveterinarioFacade pvf;

    public ControladorUsuario() {
        this.estado = "";
        this.contrasena = "";
        this.correo = "";
        this.usuTemp = new Usuarios();
        this.prop=new Propietarios();
    }

    public String login() throws IOException {
        FacesContext faces = FacesContext.getCurrentInstance();
        ExternalContext externalContext = faces.getExternalContext();
        Map<String, String> params = (Map<String, String>) externalContext.getRequestParameterMap();
        this.correo = (String) params.get("correo");
        this.contrasena = DigestUtils.md5Hex((String) params.get("contrasena"));
        Usuarios misUsu = this.usuFacade.login(this.correo, this.contrasena);
        Auditoria aud = new Auditoria();
        Date fecha = new Date();
        aud.setFecha(fecha);
        String pag = "null";
        if (misUsu.getNombres().equals("Vacio")) {
            aud.setDescipcion("Intento iniciar sesion");
            this.estado = "3";
        } else {

            //Redirecion
            //Sesion
            HttpServletRequest miSesion = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                    .getRequest();
            miSesion.getSession().setAttribute("usuario", misUsu);
            this.estado = "0";
            pag = "eleccionRol.xhtml";
            aud.setDescipcion("Inicio de sesion correcto");
        }
        this.auFacade.create(aud);
        return pag;
    }

    public void cambiarClave() {
        FacesContext faces = FacesContext.getCurrentInstance();
        ExternalContext externalContext = faces.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        String claveAct = DigestUtils.md5Hex((String) params.get("contraActual"));
        if (claveAct.equals(this.usuLogin.getContrasena())) {
            this.usuLogin.setContrasena(DigestUtils.md5Hex((String) params.get("contraNueva")));
            this.usuFacade.edit(usuLogin);
            HttpServletRequest miSesion = (HttpServletRequest) FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequest();
            miSesion.getSession().setAttribute("usuario", this.usuLogin);
            this.estado = "4";
        } else {
            this.estado = "5";
        }
    }

    public void modificarDatos() throws ParseException {
        HttpServletRequest miSesion = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequest();
        FacesContext faces = FacesContext.getCurrentInstance();
        ExternalContext externalContext = faces.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        this.usuLogin.setNombres((String) params.get("nombremod"));
        this.usuLogin.setApellidos((String) params.get("apellidomod"));
        this.usuLogin.setDireccion((String) params.get("direccionmod"));
        this.usuLogin.setCorreo((String) params.get("correomod"));
        String rolSesion=(String) miSesion.getSession().getAttribute("rol");
        if (rolSesion.equals("Administrador-Veterinario")) {
            this.usuLogin.getPersonalveterinario().setCargo((String) params.get("cargo"));
            SimpleDateFormat formatoHE = new SimpleDateFormat("HH:mm");
            String horarioEntradaModificar = ((String) params.get("horarioEntrada"));
            Date horarioE = formatoHE.parse(horarioEntradaModificar);
            SimpleDateFormat formatoHS = new SimpleDateFormat("HH:mm");
            String horarioSalidaModificar = ((String) params.get("horarioSalida"));
            Date horarioS = formatoHS.parse(horarioSalidaModificar);
            this.usuLogin.getPersonalveterinario().setHorarioEntrada(horarioE);
            this.usuLogin.getPersonalveterinario().setHorarioSalida(horarioS);
        }
        this.usuFacade.edit(usuLogin);
        miSesion.getSession().setAttribute("usuario", this.usuLogin);
        this.estado = "6";
    }

    public void miraSession(String rol) throws IOException {

        HttpServletRequest miSesion = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.usuLogin = (Usuarios) miSesion.getSession().getAttribute("usuario");
        String rolSesion= (String) miSesion.getSession().getAttribute("rol");
        this.estado = "0";
        if (rol.equals("admin")) {
            if (!(usuLogin != null)) {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                FacesContext.getCurrentInstance().
                        responseComplete();
                response.sendRedirect("/NowVet");
            }else if(!rolSesion.equals("Administrador-Veterinario")){
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                FacesContext.getCurrentInstance().
                        responseComplete();
                response.sendRedirect("/NowVet");
            }
        } else if (rol.equals("cliente")) {
            if (!(usuLogin != null)) {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                FacesContext.getCurrentInstance().
                        responseComplete();
                response.sendRedirect("/NowVet");
            }else if(!rolSesion.equals("Cliente")){
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                FacesContext.getCurrentInstance().
                        responseComplete();
                response.sendRedirect("/NowVet");
            }
        }

    }
    
    public void miraSession() throws IOException {
        HttpServletRequest miSesion = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.usuLogin = (Usuarios) miSesion.getSession().getAttribute("usuario");
        this.estado = "0";
        if (!(usuLogin != null)) {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                FacesContext.getCurrentInstance().
                        responseComplete();
                response.sendRedirect("/NowVet");
            }
    }

    public String cerrarSesion() {
        this.contrasena = "";
        this.correo = "";
        this.estado = "0";
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/NowVet/faces/index.xhtml";
    }

    public void redireccionarRol() throws IOException {//este metodo redirecciona usuarios segun su rol

        if (this.rols.equals("Administrador-Veterinario")) {
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("/NowVet/faces/administrador/indexAdministrador.xhtml");
            HttpServletRequest miSesion = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                    .getRequest();
            miSesion.getSession().setAttribute("rol", "Administrador-Veterinario");
        }
        if (this.rols.equals("Cliente")) {
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect("/NowVet/faces/cliente/indexCliente.xhtml");HttpServletRequest miSesion = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                    .getRequest();
            miSesion.getSession().setAttribute("rol", "Cliente");
        }
    }

    public void eliminar() {
        FacesContext faces = FacesContext.getCurrentInstance();
        ExternalContext externalContext = faces.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        int id = Integer.parseInt((String) params.get("idUsu"));
        Usuarios usuElim = this.usuFacade.find(id);
        this.usuFacade.remove(usuElim);
    }

    public void mostrarDetallesUsu(int id) {
        this.usuTemp = this.usuFacade.find(id);
        FacesContext contex = FacesContext.getCurrentInstance();
        try {
            contex.getExternalContext().redirect("/NowVet/faces/administrador/perfiles/perfil.xhtml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<Auditoria> listarAud() {
        List<Auditoria> listaAud;
        listaAud = this.auFacade.findAll();
        return listaAud;
    }

    public List<Usuarios> listarUsu() {
        List<Usuarios> listaUsu;
        listaUsu = this.usuFacade.findAll();
        return listaUsu;
    }

    public String modificarUsu() {
        this.usuFacade.edit(usuTemp);
        this.estado = "7";
        return "perfil";
    }
    
    public List<Roles> listarRolesDisponiblies(){//Este metodo verificar que roles disponibles hay para un usuario
        List<Roles> roles=this.rf.findAll();
        for(Roles rol : this.usuTemp.getRolesCollection()) {
            roles.remove(rol);
        }
        return roles;
    }

    public void eliminarRol(Roles rol) {
        this.usuTemp.getRolesCollection().remove(rol);
        Roles rol2=this.rf.find(rol.getIdRol());
        rol2.getUsuariosCollection().remove(this.usuTemp);
        this.rf.edit(rol2);
        this.usuFacade.edit(this.usuTemp);
        this.estado = "8";
    }
    
    public void agregarRol() throws ParseException{
        FacesContext faces = FacesContext.getCurrentInstance();
        ExternalContext externalContext = faces.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        Roles role=this.rf.find(this.rolTemp);
        this.usuTemp.getRolesCollection().add(role);
        role.getUsuariosCollection().add(usuTemp);
        if (this.rolTemp==1) {
            Propietarios propietario=this.prf.find(this.usuTemp.getCedula());
            if (propietario==null) {
                propietario=new Propietarios();
                propietario.setBarrio((String) params.get("barrio"));
                propietario.setDniPropietario(this.usuTemp.getCedula());
                propietario.setUsuarios(this.usuTemp);
                this.prf.create(propietario);
                this.usuTemp.setPropietarios(propietario);
            }else{
                propietario.setBarrio((String) params.get("barrio"));
                this.prf.edit(propietario);
            }
        }else if(this.rolTemp==2){
            Personalveterinario personalVet= this.pvf.find(this.usuTemp.getCedula());
            if (personalVet==null) {
                personalVet=new Personalveterinario();
                personalVet.setCargo((String) params.get("cargo"));
                SimpleDateFormat formatoHE = new SimpleDateFormat("HH:mm");
                String horarioEntrada = ((String) params.get("horaEntrada"));
                Date horarioE = formatoHE.parse(horarioEntrada);
                personalVet.setHorarioEntrada(horarioE);
                SimpleDateFormat formatoHS = new SimpleDateFormat("HH:mm");
                String horarioSalida = ((String) params.get("horaSalida"));
                Date horarioS = formatoHS.parse(horarioSalida);
                personalVet.setHorarioSalida(horarioS);
                personalVet.setDniPersonal(this.usuTemp.getCedula());
                personalVet.setUsuarios(this.usuTemp);
                this.pvf.create(personalVet);
                this.usuTemp.setPersonalveterinario(personalVet);
            }else{
                personalVet.setCargo((String) params.get("cargo"));
                
                this.pvf.edit(personalVet);
            }
        }
        this.rf.edit(role);
        this.usuFacade.edit(usuTemp);
    }

    public String registrarUsuario() {
        boolean exiteCorreo=false;
        Usuarios usutemp2=this.usuFacade.find(this.usuTemp.getCedula());
            List <Usuarios> usuList = this.usuFacade.findAll();
            for(Usuarios usu : usuList){
                if (usu.getCorreo().equals(this.usuTemp.getCorreo())) {
                    exiteCorreo=true;
                    break;
                }
            }
        if (usutemp2==null && exiteCorreo==false) {
        Collection<Roles> rolescoll = new ArrayList<>();
        rolescoll.add(this.rf.find(1));
        this.usuTemp.setRolesCollection(rolescoll);
        this.usuTemp.setEstado("Activo");
        this.usuFacade.create(usuTemp);
        Roles rol=this.rf.find(1);
        rol.getUsuariosCollection().add(usuTemp);
        this.rf.edit(rol);
        this.prop.setDniPropietario(this.usuTemp.getCedula());
        this.prop.setUsuarios(this.usuTemp);
        this.prf.create(prop);
        //Mailer.send(this.usuTemp.getCorreo(), "Bienvenido a NowVet " + this.usuTemp.getNombres(), "<div style='display: block;'><img style='widht: 70px; height: 70px;' src='http://nowvet.co.nf/imagenes/logo.png' alt='Logo'></div><div style='font-family: arial;'><h1 style='color: #2aac7d;'>Bienvenido "+this.usuTemp.getNombres()+" "+this.usuTemp.getApellidos()+"</h1><p style='font-size: 1.5em; color: #797979;'>Ahoras es mas facil agendar una cita a tus mascotas desde  NowVet, sin necesidad de ir hasta nuestra sede.</p></div>");
        return "registroUsuarioMensaje.xhtml";
        }else{
            this.estado = "9";
            return "registroUsuarios";
        }
    }

    public void registrarUsuarioCargaExcel() {
        boolean exiteCorreo=false;
        Usuarios usutemp2=this.usuFacade.find(this.usuTemp.getCedula());
            List <Usuarios> usuList = this.usuFacade.findAll();
            for(Usuarios usu : usuList){
                if (usu.getCorreo().equals(this.usuTemp.getCorreo())) {
                    exiteCorreo=true;
                    break;
                }
            }
        if (usutemp2==null && exiteCorreo==false) {
        Collection<Roles> rolescoll = new ArrayList<>();
        rolescoll.add(this.rf.find(1));
        this.usuTemp.setRolesCollection(rolescoll);
        this.usuTemp.setEstado("Activo");
        this.usuFacade.create(usuTemp);
        Roles rol=this.rf.find(1);
        rol.getUsuariosCollection().add(usuTemp);
        this.rf.edit(rol);
        this.prop.setDniPropietario(this.usuTemp.getCedula());
        this.prop.setUsuarios(this.usuTemp);
        this.prf.create(prop);
        //Mailer.send(this.usuTemp.getCorreo(), "Bienvenido a NowVet " + this.usuTemp.getNombres(), "<div style='display: block;'><img style='widht: 70px; height: 70px;' src='http://nowvet.co.nf/imagenes/logo.png' alt='Logo'></div><div style='font-family: arial;'><h1 style='color: #2aac7d;'>Bienvenido "+this.usuTemp.getNombres()+" "+this.usuTemp.getApellidos()+"</h1><p style='font-size: 1.5em; color: #797979;'>Ahoras es mas facil agendar una cita a tus mascotas desde  NowVet, sin necesidad de ir hasta nuestra sede.</p></div>");
      
        }
    }
    
    public void recuperarClave(){
        FacesContext faces = FacesContext.getCurrentInstance();
        ExternalContext externalContext = faces.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        boolean exiteCorreo=false;
            List <Usuarios> usuList = this.usuFacade.findAll();
            Usuarios recuperado=new Usuarios();
           String correoIngresado=(String) params.get("campoCorreoElectronico");
            for(Usuarios usu : usuList){
                if (usu.getCorreo().equals(correoIngresado)) {
                    exiteCorreo=true;
                    recuperado=usu;
                    break;
                }
            }
            if (exiteCorreo==true) {
                 int nuevaClave=(int)(Math.random()*(10000000-99999999+1)+99999999);
                 recuperado.setContrasena(""+nuevaClave);
                 this.usuFacade.edit(recuperado);
        Mailer.send(recuperado.getCorreo(), "Recuperacion De Contraseña", "<div style='display: block;'><img style='widht: 70px; height: 70px;' src='http://nowvet.co.nf/imagenes/logo.png' alt='Logo'></div><div style='font-family: arial;'><h1 style='color: #2aac7d;'>Hola "+recuperado.getNombres()+" "+recuperado.getApellidos()+"</h1><p style='font-size: 1.5em; color: #797979;'>Recuperacion de contraseña correcta, por seguridad le recomendamos cambiar su contraseña lo mas pronto posible.<br /><br /><strong>Correo: </strong>"+recuperado.getCorreo()+"<br /> <strong>Nueva Contraseña: </strong>"+nuevaClave+"</p></div>");
                 this.estado="11";
        }else{
                this.estado="12";
            }
    }
    
    public void setearRegistro(){//Este metodos setea los usuario temp 
        //para que no cargue los datos de usuario registrado anteriormente
        this.usuTemp=null;
        this.prop=null;
        this.usuTemp=new Usuarios();
        this.prop=new Propietarios();
        this.estado="0";
} 
    
    public boolean validarCliente(){//este metodo valida si un usuario temporal es cliente
        boolean cliente=false;
        for (Roles rol : this.usuTemp.getRolesCollection()) {
            if (rol.getIdRol()==1) {
               cliente=true;
               break;
            }
        }
        return cliente;
    }
    
    public void enviarCorreo(){
        FacesContext faces = FacesContext.getCurrentInstance();
        ExternalContext externalContext = faces.getExternalContext();
        Map params = externalContext.getRequestParameterMap();
        String mail=(String) params.get("emaiol");
        String asunto=(String) params.get("asunto");
        String encabezado= (String) params.get("encabezado");
        String msj= (String) params.get("texto");
        Mailer.send(mail, asunto, "<div style='display: block;'><img style='widht: 70px; height: 70px;' src='http://nowvet.co.nf/imagenes/logo.png' alt='Logo'></div><div style='font-family: arial;'><h1 style='color: #2aac7d;'>"+encabezado+"</h1><p style='font-size: 1.5em; color: #797979;'>"+msj+"<br /></p></div>");
        this.estado="13";
    }
    
    public void initAud() throws JRException {
        this.lista1=this.auFacade.findAll();
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(this.lista1);
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = (String) servletContext.getRealPath("/Reportes"); // Sustituye "/" por el directorio ej: "/upload"
        realPath+="/auditoriaReport.jasper"; 
        jasperPrint = JasperFillManager.fillReport(realPath, new HashMap(), beanCollectionDataSource);
    }
    public void PDFaud() throws JRException, IOException {
        initAud();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportAuditoria.pdf");
      ServletOutputStream servletOutputStream;
        servletOutputStream = httpServletResponse.getOutputStream();
       JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

    }

    public void DOCXaud() throws JRException, IOException {
        initAud();
           HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportAuditoria.docx");
       ServletOutputStream servletOutputStream;
        servletOutputStream = httpServletResponse.getOutputStream();
       JRDocxExporter docxExporter=new JRDocxExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
    }

    public void XLSXaud() throws JRException, IOException {
       initAud();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
       httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportAuditoria.xlsx");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JRXlsxExporter docxExporter=new JRXlsxExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
    }

    public void ODTaud() throws JRException, IOException {
        initAud();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportAuditoria.odt");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JROdtExporter docxExporter=new JROdtExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
   }
    
       public void PPTaud() throws JRException, IOException{
       initAud();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportAudotoria.pptx");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JRPptxExporter docxExporter=new JRPptxExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
   }
    
    public void initusu() throws JRException {
        this.lista1=this.usuFacade.findAll();
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(this.lista1);
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = (String) servletContext.getRealPath("/Reportes"); // Sustituye "/" por el directorio ej: "/upload"
        realPath+="/usuariosReport.jasper"; 
        jasperPrint = JasperFillManager.fillReport(realPath, new HashMap(), beanCollectionDataSource);
    }
    public void PDFusu() throws JRException, IOException {
        initusu();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=reporteUsuarios.pdf");
      ServletOutputStream servletOutputStream;
        servletOutputStream = httpServletResponse.getOutputStream();
       JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

    }

    public void DOCXusu() throws JRException, IOException {
        initusu();
           HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=reporteUsuarios.docx");
       ServletOutputStream servletOutputStream;
        servletOutputStream = httpServletResponse.getOutputStream();
       JRDocxExporter docxExporter=new JRDocxExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
    }

    public void XLSXusu() throws JRException, IOException {
       initusu();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
       httpServletResponse.addHeader("Content-disposition", "attachment; filename=reporteUsuarios.xlsx");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JRXlsxExporter docxExporter=new JRXlsxExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
    }

    public void ODTusu() throws JRException, IOException {
        initusu();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=reporteUsuarios.odt");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JROdtExporter docxExporter=new JROdtExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
   }
    
       public void PPTusu() throws JRException, IOException{
       initusu();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=reporteUsuarios.pptx");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JRPptxExporter docxExporter=new JRPptxExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
   }
       
       
    
    public int cantidadHojas(String ruta) throws IOException, BiffException {
        archivoExcel = Workbook.getWorkbook(new File(ruta));
        mihoja = archivoExcel.getSheet(0);
        return archivoExcel.getNumberOfSheets();
    }

    public int cantidadDeCitas(int mes){
        Personalveterinario per=this.pvf.find(this.veterinario);
        int cantidad=0;
        Date fecha1=new Date();
        fecha1.setMonth(mes);
        fecha1.setDate(0);
        fecha1.setHours(0);
        fecha1.setMinutes(0);
        fecha1.setSeconds(0);
        Date fecha2=new Date();
        fecha2.setMonth(mes);
        fecha2.setDate(31);
        fecha2.setHours(0);
        fecha2.setMinutes(0);
        fecha2.setSeconds(0);
        for (Citas cita : per.getCitasCollection()){
            if (cita.getFechaAsignada().before(fecha2) && fecha1.before(cita.getFechaAsignada())) {
                cantidad++;
            }else if(cita.getFechaAsignada().equals(fecha2) || cita.getFechaAsignada().equals(fecha1)){
                cantidad++;
            }
        }
        return cantidad;
    }
    
    public List <Personalveterinario> listarPersonal(){
        return this.pvf.findAll();
    }
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRols() {
        return rols;
    }

    public void setRols(String rols) {
        this.rols = rols;
    }

    public Usuarios getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(Usuarios usuLogin) {
        this.usuLogin = usuLogin;
    }

    public Usuarios getUsuTemp() {
        return usuTemp;
    }

    public void setUsuTemp(Usuarios usuTemp) {
        this.usuTemp = usuTemp;
    }

    public Propietarios getProp() {
        return prop;
    }

    public void setProp(Propietarios prop) {
        this.prop = prop;
    }

    public int getRolTemp() {
        return rolTemp;
    }

    public void setRolTemp(int rolTemp) {
        this.rolTemp = rolTemp;
    }

    public Sheet getMihoja() {
        return mihoja;
    }

    public void setMihoja(Sheet mihoja) {
        this.mihoja = mihoja;
    }

    public int getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(int veterinario) {
        this.veterinario = veterinario;
    }
    
}
