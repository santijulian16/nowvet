/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Citas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Movimiento Rastafari
 */
@Stateless
public class CitasFacade extends AbstractFacade<Citas> {

    @PersistenceContext(unitName = "NowVetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CitasFacade() {
        super(Citas.class);
    }
    
    public List<Citas> consultarCitasRecientes(){
        Query q = em.createQuery("Select c from Citas c where c.estado = 'Creada'");
        return q.getResultList();
    }
    
    public String consultarMotivo(int idCita){
        Query q = em.createQuery("Select cc.motivo from Citasclinicas cc where cc.idCita=:idCita");
        q.setParameter("idCita", idCita);
        String motivo = String.join("", q.getResultList());
        return motivo;
    }
    
    public String consultarSintomas(int idCita){
        Query q = em.createQuery("Select cc.sintomas from Citasclinicas cc where cc.idCita=:idCita");
        q.setParameter("idCita", idCita);
        String sintomas = String.join("", q.getResultList());
        return sintomas;
    }
    
    public String consultarServiciosAplicados(int idCita){
        Query q = em.createQuery("Select cp.serviciosAplicados from Citaspeluqueria cp where cp.idCita=:idCita");
        q.setParameter("idCita", idCita);
        String serviciosA = String.join("", q.getResultList());
        return serviciosA;
    }
    
    public List<Citas> consultarFechaAsignada(int idCita) throws ParseException{
        Query q = em.createQuery("Select c.fechaAsignada from Citas c where c.idCita=:idCita");
        q.setParameter("idCita", idCita);
        return q.getResultList();
    }
    
    public String consultarVacunas(int idCita){
        Query q = em.createQuery("Select c.vacunas from Citas c where c.idCita=:idCita");
        q.setParameter("idCita", idCita);
        String vacunas = String.join("", q.getResultList());
        return vacunas;
    }
    
    public String consultarPeso(int idCita){
        Query q = em.createQuery("Select c.peso from Citas c where c.idCita=:idCita");
        q.setParameter("idCita", idCita);
        String peso = String.join("", q.getResultList());
        return peso;
    }
    
    public String consultarAntecedentes(int idCita){
        Query q = em.createQuery("Select c.antecedentes from Citas c where c.idCita=:idCita");
        q.setParameter("idCita", idCita);
        String antecedentes = String.join("", q.getResultList());
        return antecedentes;
    }
    
    public List<Citas> consultarCitasEjecutadas(){
        Query q = em.createQuery("Select c from Citas c where c.estado='Ejecutada'");
        return q.getResultList();
    }
    
    public List<Citas> listarHistorialFecha(int idCita){
        Query q;
        q=this.em.createQuery("select c from Citas c where c.idCita=:id and c.estado='Ejecutada'");
        q.setParameter("id", idCita);
        List<Citas> historial;
        historial=q.getResultList();
        return historial;
    }
    
    public List<Citas> verificarDisponibilidad(Date fecha) {
        Query q;
        q = em.createQuery("select c from Citas c where c.fechaAsignada  =:fecha and c.estado='Creada'");
        q.setParameter("fecha", fecha);
        return q.getResultList();
    }

    public List crearReporteCitasEjecutadas(int cedula){
        Query q = em.createQuery("select c.fechaAsignada as fecha, c.peso as peso, c.idMascota.nombre as mascota, c.idVeterinario.usuarios.nombres as vetNombre, c.idVeterinario.usuarios.apellidos as vetApellido, c.idVeterinario.usuarios.correo as vetCoreo from Citas c where c.idMascota.codigoPropietario.usuarios.cedula = :ced");
        q.setParameter("ced", cedula);
        List citaTemp=q.getResultList();
        return citaTemp;
    }
    
    public List hisorialClinico(int codigoMascota){
        Query q=em.createQuery("select c.citasclinicas.diagnostico, c.citasclinicas.motivo, c.citasclinicas.alimento, c.citasclinicas.formulaMedica, c.citasclinicas.sintomas, c.fechaAsignada, c.vacunas, c.peso, c.antecedentes, c.idVeterinario.usuarios.cedula, c.idVeterinario.usuarios.nombres, c.idVeterinario.usuarios.apellidos, c.idVeterinario.usuarios.correo from Citas c where c.idMascota.codigoMascota= :idMasco");
        q.setParameter("idMasco", codigoMascota);
        List histo=q.getResultList();
        return histo;
    }
}
