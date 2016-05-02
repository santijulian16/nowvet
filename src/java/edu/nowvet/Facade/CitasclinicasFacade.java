/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nowvet.Facade;

import edu.nowvet.Entitys.Citasclinicas;
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
public class CitasclinicasFacade extends AbstractFacade<Citasclinicas> {

    @PersistenceContext(unitName = "NowVetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CitasclinicasFacade() {
        super(Citasclinicas.class);
    }
    
    public List<Citasclinicas> listarHistorial(int idMascota){
        Query q;
        String estado="Ejecutada";
        q=this.em.createQuery("select h from Citasclinicas h where h.citas.idMascota.codigoMascota=:id and h.citas.estado=:est");
        q.setParameter("id", idMascota);
        q.setParameter("est", estado);
        List<Citasclinicas> historial;
        historial=q.getResultList();
        return historial;
    }
    
}
