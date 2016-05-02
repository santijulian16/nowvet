/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Citas;
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
        q = em.createQuery("select c from Citas c where c.fechaAsignada  =:fecha");
        q.setParameter("fecha", fecha);
        return q.getResultList();
    }

}
