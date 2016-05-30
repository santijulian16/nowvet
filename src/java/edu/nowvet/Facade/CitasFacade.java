/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Citas;
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
        Query q = em.createQuery("Select c from Citas c where c.estado='Creada'");
        return q.getResultList();
    }
    
    public List<Citas> consultarCitasEjecutadas(){
        Query q = em.createQuery("Select c from Citas c where c.estado='Ejecutada'");
        return q.getResultList();
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
