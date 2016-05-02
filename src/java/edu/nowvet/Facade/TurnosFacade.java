/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Turnos;
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
public class TurnosFacade extends AbstractFacade<Turnos> {

    @PersistenceContext(unitName = "NowVetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TurnosFacade() {
        super(Turnos.class);
    }
    
    public List<Turnos> consultarTurnosCreados(){
        Query q = em.createQuery("Select t from Turnos t where t.fechaInicio = '2016-11-03 09:00:00'");
        return q.getResultList();
    }   
}
